package org.camunda.bpm.extension.cmmnhelper.example.state;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cmmn.operation.AtomicOperationCaseExecutionEnable;
import org.camunda.bpm.engine.impl.core.variable.scope.CmmnVariableInvocationListener;
import org.camunda.bpm.extension.cmmnhelper.core.VariableInstanceEvent;
import org.camunda.bpm.extension.cmmnhelper.spring.PublishVariableEventPlugin;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class AxonStateExampleApplication {

  public static void main(String[] args) {
    SpringApplication.run(AxonStateExampleApplication.class, args);
  }

  @Bean
  public ProcessEnginePlugin onVariableChange(ApplicationEventPublisher publisher) {
    return new PublishVariableEventPlugin(publisher);
  }

  @EventListener
  public void on(VariableInstanceEvent.OnVariableCreate onCreate) {
    log.info("called on create: {}", onCreate);
  }
}
