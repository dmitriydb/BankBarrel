package ru.shanalotte.bankbarrel.appserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class AbstractTestCase {

  @Autowired
  protected TestUtils testUtils;


}
