package org.camunda.bpm.extension.cmmnhelper.example.state;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AxonStateExampleApplicationTest {

  @Autowired
  private CaseService caseService;

  @Test
  public void name() {
    CaseInstance instance = caseService.createCaseInstanceByKey("dummy_case");

  }
}
