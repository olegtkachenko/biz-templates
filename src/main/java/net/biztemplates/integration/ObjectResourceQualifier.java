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

package net.biztemplates.integration;

import java.io.Serializable;
import net.biztemplates.utils.SerializationUtil;

public final class ObjectResourceQualifier implements Serializable, Cloneable {

  private String name;
  private Class<?> indexType;
  private Class<?> objectType;

  public ObjectResourceQualifier(String name, Class<?> objectType, Class<?> indexType) {
    assertArgs(name, objectType, indexType);
    this.name = name;
    this.indexType = indexType;
    this.objectType = objectType;
  }

  public String getName() {
    return name;
  }

  public Class<?> getIndexType() {
    return indexType;
  }

  public Class<?> getObjectType() {
    return objectType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;//NOPMD
    }
    if (o == null || getClass() != o.getClass()) {
      return false;//NOPMD
    }

    ObjectResourceQualifier that = (ObjectResourceQualifier) o;

    return this.name.equals(that.getName())
        && this.indexType.equals(that.getIndexType())
        && (this.objectType.isAssignableFrom(that.getObjectType())
        || that.getObjectType().isAssignableFrom(this.objectType));
  }

  @Override
  public int hashCode() {
    int result = this.name.hashCode();
    result = 31 * result + this.indexType.hashCode();
    result = 31 * result + this.objectType.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ObjectResourceQualifier{" +
        "name='" + this.name + "'" +
        ", indexType=" + this.indexType +
        ", objectType=" + this.objectType +
        '}';
  }

  @Override
  public final ObjectResourceQualifier clone() {
    return SerializationUtil.duplicate(this);

  }

  private void assertArgs(String name, Class<?> objectType, Class<?> indexType) {
    if (name == null) {
      throw new IllegalArgumentException("ObjectResourceQualifier argument 'name' is not be null.");
    }
    if (indexType == null) {
      throw new IllegalArgumentException(
          "ObjectResourceQualifier argument 'indexType' is not be null.");
    }
    if (objectType == null) {
      throw new IllegalArgumentException(
          "ObjectResourceQualifier argument 'objectType' is not be null.");
    }
  }
}
