package org.camunda.bpm.extension.cmmnhelper.core.plugin;

import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cmmn.model.CmmnActivity;
import org.camunda.bpm.engine.impl.cmmn.transformer.AbstractCmmnTransformListener;
import org.camunda.bpm.model.cmmn.instance.CasePlanModel;

import java.util.ArrayList;

public class CaseVariableListenerPlugin extends AbstractProcessEnginePlugin {

  @Override
  public void preInit(ProcessEngineConfigurationImpl configuration) {
    if (configuration.getCustomPostCmmnTransformListeners() == null) {
      configuration.setCustomPostCmmnTransformListeners(new ArrayList<>());
    }

    configuration.getCustomPostCmmnTransformListeners().add(new AbstractCmmnTransformListener(){
      @Override
      public void transformCasePlanModel(final CasePlanModel casePlanModel, final CmmnActivity activity) {

      }
    });

  }





}
