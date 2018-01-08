//
package com.edevapps.tests.environment;

import net.biztemplates.Visit;
import net.biztemplates.Visitor;

public class TestClass extends AbstractClass
    implements TestInteface, TestComponent, Visitor {

  private String stringProperty;

  public TestClass(String stringProperty) {//NOPMD
    this.stringProperty = stringProperty;
  }

  @Override
  public String getStringProperty() {
    return stringProperty;
  }

  public void setStringProperty(String value) {
    this.stringProperty = value;
  }

  @Override
  public String test() {
    return "Hello!";
  }

  @Override
  public Visit<?> beginVisit(Class<?> object) throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean isVisitable(Class<?> object) {
    return false;
  }

}
