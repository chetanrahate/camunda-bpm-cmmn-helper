package org.camunda.bpm.extension.cmmnhelper.example.state;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.As;
import com.tngtech.jgiven.annotation.Quoted;
import com.tngtech.jgiven.annotation.ScenarioState;
import com.tngtech.jgiven.integration.spring.EnableJGiven;
import com.tngtech.jgiven.integration.spring.JGivenStage;
import com.tngtech.jgiven.integration.spring.SimpleSpringScenarioTest;
import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.extension.cmmnhelper.example.state.DummyCase.ACTIVITIES;
import org.camunda.bpm.extension.cmmnhelper.test.CmmnStateAssertHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState.AVAILABLE;
import static org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState.DISABLED;
import static org.camunda.bpm.engine.impl.cmmn.execution.CaseExecutionState.ENABLED;
import static org.camunda.bpm.extension.cmmnhelper.example.state.DummyCase.ACTIVITIES.TASK_1;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableJGiven
public class StateControlApplicationTest extends SimpleSpringScenarioTest<StateControlApplicationTest.StateControlApplicationStage> {


  @JGivenStage
  @RequiredArgsConstructor
  public static class StateControlApplicationStage extends Stage<StateControlApplicationStage> {

    private final DummyCase dummyCase;
    private final DummyEntryCriterion criterion;
    private final CaseService caseService;
    private final HistoryService historyService;


    @ScenarioState
    protected CaseInstance caseInstance;

    public StateControlApplicationStage a_case_is_started() {
      caseInstance = dummyCase.start("12345");
      return self();
    }

    public StateControlApplicationStage task_$_should_be_enabled(@Quoted String task) {
      criterion.enable(task);

      return self();
    }

    public StateControlApplicationStage task_$_should_be_disabled(@Quoted String task) {
      criterion.disable(task);

      return self();
    }

    public StateControlApplicationStage variable_$_is_set_to_$(String key, String value) {
      caseService.setVariable(caseInstance.getCaseInstanceId(), key, value);
      return self();
    }

    public StateControlApplicationStage state_is_reevaluated() {
      caseService.setVariable(caseInstance.getCaseInstanceId(), DummyCase.VARIABLES.BUSINESS_KEY, "12345");

      return self();
    }


    public StateControlApplicationStage task_$_has_state_$(@Quoted String task, @Quoted CaseExecutionState state) {
      assertThat(new CmmnStateAssertHelper(historyService,
        TASK_1,
        caseInstance.getCaseInstanceId()).get()).containsOnlyOnce(state);

      return self();
    }
  }

  @Test
  public void bothTasksAvailableAfterStart() {
    given().a_case_is_started();

    when().variable_$_is_set_to_$("foo", "bar");

    then().task_$_has_state_$(ACTIVITIES.TASK_1, AVAILABLE)
      .and().task_$_has_state_$(ACTIVITIES.TASK_2, AVAILABLE);
  }

  @Test
  @As("when the criterion is true, the task is enabled when the case is started")
  public void bothTasksEnabledAfterStart() {
    given()
      .task_$_should_be_enabled(ACTIVITIES.TASK_1)
      .and()
      .task_$_should_be_enabled(ACTIVITIES.TASK_2);

    when()
      .a_case_is_started();
    then()
      .task_$_has_state_$(ACTIVITIES.TASK_1, ENABLED)
      .and()
      .task_$_has_state_$(ACTIVITIES.TASK_2, ENABLED);
  }

  @Test
  public void task2_gets_disabled() {
    given()
      .task_$_should_be_enabled(ACTIVITIES.TASK_1)
      .and()
      .task_$_should_be_enabled(ACTIVITIES.TASK_2);

    when()
      .a_case_is_started()
      .and()
      .task_$_should_be_disabled(ACTIVITIES.TASK_2)
      .and()
      .state_is_reevaluated();

    then()
      .task_$_has_state_$(ACTIVITIES.TASK_2, DISABLED);

  }
}
