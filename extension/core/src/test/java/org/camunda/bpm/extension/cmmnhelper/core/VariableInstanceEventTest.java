package org.camunda.bpm.extension.cmmnhelper.core;

import org.camunda.bpm.engine.delegate.CaseVariableListener;
import org.camunda.bpm.engine.delegate.DelegateCaseVariableInstance;
import org.camunda.bpm.extension.mockito.delegate.DelegateCaseVariableInstanceFake;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class VariableInstanceEventTest {

  @Test
  public void implements_toString() {

    DelegateCaseVariableInstance variable = new DelegateCaseVariableInstanceFake()
      .withId(UUID.randomUUID().toString())
      .withName("foo")
      .withEventName(CaseVariableListener.CREATE);

    assertThat(VariableInstanceEvent.of(variable).toString()).isEqualTo("VariableInstanceEvent[]");
  }
}
