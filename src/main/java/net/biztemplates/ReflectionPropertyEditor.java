/*
 *    Copyright (c) 2017, The EDUARD BURENKOV (http://edevapps.com)
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package net.biztemplates;

import java.lang.reflect.Method;
import net.biztemplates.utils.ReflectionUtil;

public class ReflectionPropertyEditor implements PropertyEditor {

  private Object object;
  private Class<?> propertyClass;
  private String propertyName;

  public ReflectionPropertyEditor(Object object, Class<?> propertyClass, String propertyName) {
    setPropertyClass(propertyClass);
    setPropertyName(propertyName);
    setObject(object);
  }

  public Object getObject() {
    return this.object;
  }

  public void setObject(Object object) {
    if (object == null) {
      throw new IllegalArgumentException("Object value is not be null.");
    }

    this.object = object;
  }

  public Class<?> getPropertyClass() {
    return propertyClass;
  }

  public void setPropertyClass(Class<?> propertyClass) {
    if (propertyClass == null) {
      throw new IllegalArgumentException("Property class is not be null.");
    }

    this.propertyClass = propertyClass;
  }

  @Override
  public String getPropertyName() {
    return this.propertyName;
  }

  public void setPropertyName(String propertyName) {
    if (propertyName == null) {
      throw new IllegalArgumentException("Property getPropertyName is not be null.");
    }

    this.propertyName = propertyName;
  }

  @Override
  public Object getValue() {
    try {
      return getGetterMethod().invoke(this.object);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public void setValue(Object value) {
    try {
      getSetterMethod().invoke(this.object, value);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  public boolean isValid(Object value) {
    return propertyClass.isAssignableFrom(value.getClass());
  }

  private Method getSetterMethod() {
    return ReflectionUtil.getSetterMethod(this.object, this.propertyName, this.propertyClass);
  }

  private Method getGetterMethod() {
    return ReflectionUtil.getGetterMethod(this.object, this.propertyName);
  }
}
