//
package com.edevapps.tests.environment;

import net.biztemplates.integration.AbstractObjectAdapter;
import net.biztemplates.integration.ObjectResourceQualifier;

public class TestComponentAdapter extends AbstractObjectAdapter<TestComponent, String> implements
    TestComponent {

  public TestComponentAdapter() {
  }

  public TestComponentAdapter(ObjectResourceQualifier qualifier,
      String index) {
    super(qualifier, index);
  }

  @Override
  public String test() {
    return object().test();
  }
}
