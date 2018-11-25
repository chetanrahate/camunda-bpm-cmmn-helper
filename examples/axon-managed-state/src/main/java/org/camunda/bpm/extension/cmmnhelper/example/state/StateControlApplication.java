package org.camunda.bpm.extension.cmmnhelper.example.state;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableProcessApplication
@Slf4j
public class StateControlApplication {

  public static void main(final String[] args) {
    SpringApplication.run(StateControlApplication.class, args);
  }

  @Bean
  public ProcessEnginePlugin onVariableTouch(final ApplicationEventPublisher publisher) {
    return null; //new PublishVariableEventPlugin(publisher);
  }

}
