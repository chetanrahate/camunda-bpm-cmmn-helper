package org.camunda.bpm.extension.cmmnhelper.spring;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.delegate.CaseVariableListener;
import org.camunda.bpm.engine.impl.cfg.AbstractProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cmmn.model.CmmnActivity;
import org.camunda.bpm.engine.impl.cmmn.transformer.AbstractCmmnTransformListener;
import org.camunda.bpm.extension.cmmnhelper.core.VariableInstanceEvent;
import org.camunda.bpm.model.cmmn.impl.instance.CasePlanModel;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class PublishVariableEventPlugin extends AbstractProcessEnginePlugin {

  private final ApplicationEventPublisher publisher;

  private static final Set<String> EVENT_TYPES = new HashSet<>(Arrays.asList(
    CaseVariableListener.CREATE,
    CaseVariableListener.UPDATE,
    CaseVariableListener.DELETE)
  );

  @Override
  public void preInit(final ProcessEngineConfigurationImpl configuration) {
    configuration.getCustomPostCmmnTransformListeners()
      .add(new AbstractCmmnTransformListener() {
             @Override
             public void transformCasePlanModel(final CasePlanModel casePlanModel, final CmmnActivity activity) {
               EVENT_TYPES.forEach(event -> {
                 activity.addVariableListener(event, (CaseVariableListener) variableInstance -> {
                   publisher.publishEvent(VariableInstanceEvent.of(variableInstance));
                 });
               });
             }
           }
      );
  }

}
