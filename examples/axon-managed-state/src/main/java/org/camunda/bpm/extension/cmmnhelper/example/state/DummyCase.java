package org.camunda.bpm.extension.cmmnhelper.example.state;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class DummyCase {
  public static final String KEY = "dummy_case";

  public enum ACTIVITIES {
    ;

    public static final String TASK_1 = "task1";
    public static final String TASK_2 = "task2";
  }

  public enum VARIABLES {
    ;

    public static final String BUSINESS_KEY = "businessKey";
  }

  private final CaseService caseService;

  public CaseInstance start(final String businessKey) {
    return caseService.createCaseInstanceByKey(KEY, businessKey, Variables.putValue(VARIABLES.BUSINESS_KEY, businessKey));
  }
}
