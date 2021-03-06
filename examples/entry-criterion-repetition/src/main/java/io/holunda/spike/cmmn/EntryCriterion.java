package io.holunda.spike.cmmn;

import org.camunda.bpm.engine.delegate.DelegateCaseExecution;
import org.springframework.stereotype.Component;

import java.util.function.BiPredicate;

import static org.camunda.bpm.extension.cmmnhelper.core.RepetitionRule.WHEN_NOT_ENABLED_OR_ACTIVE;


@Component
public class EntryCriterion implements BiPredicate<DelegateCaseExecution, String> {

  @Override
  public boolean test(final DelegateCaseExecution execution, final String activityId) {
    return WHEN_NOT_ENABLED_OR_ACTIVE.test(execution,activityId);
  }
}
