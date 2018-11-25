package org.camunda.bpm.extension.cmmnhelper.core.plugin;

import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class CaseVariableListenerPluginTest {

  private final ProcessEngineConfigurationImpl configuration = new StandaloneInMemProcessEngineConfiguration();
  private CaseVariableListenerPlugin plugin;

  @Test
  public void customListenersEmpty() {
    assertThat(configuration.getCustomPostCmmnTransformListeners()).isNull();
  }

  @Test
  public void add_listeners() {
    new CaseVariableListenerPlugin().preInit(configuration);
    assertThat(configuration.getCustomPostCmmnTransformListeners()).isNotEmpty();
  }
}
