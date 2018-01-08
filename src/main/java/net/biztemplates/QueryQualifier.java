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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.biztemplates.utils.SerializationUtil;

public final class QueryQualifier implements Serializable, Cloneable {

  final private String name;
  final private Class<?> object;
  private final Set<String> parameters = new HashSet<>(0);

  public QueryQualifier(Query<?, ?> query) {
    assertParams(query.getId(), query.objectType(), query.parametersName());

    this.name = query.getId();
    this.object = query.objectType();
    setParameters(Arrays.asList(query.parametersName()));
  }

  public QueryQualifier(String name, Class<?> objectType, String... parameters) {
    assertParams(name, objectType, parameters);

    this.name = name;
    this.object = objectType;
    setParameters(Arrays.asList(parameters));
  }

  public QueryQualifier(String name, Class<?> objectType, Collection<String> parameters) {
    String[] names = new String[parameters.size()];
    parameters.toArray(names);
    assertParams(name, objectType, names);

    this.name = name;
    this.object = objectType;
    setParameters(parameters);
  }

  private void setParameters(Collection<String> names) {
    this.parameters.clear();
    this.parameters.addAll(names);
  }

  public String name() {
    return this.name;
  }

  public Class<?> object() {
    return this.object;
  }

  public String[] parameters() {
    return this.parameters.toArray(new String[this.parameters.size()]);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;//NOPMD
    }
    if (o == null || getClass() != o.getClass()) {
      return false;//NOPMD
    }

    QueryQualifier that = (QueryQualifier) o;

    return this.name.equals(that.name)
        && this.object.equals(that.object)
        && this.parameters.containsAll(that.parameters);
  }

  @Override
  public int hashCode() {
    int result = this.name.hashCode();
    result = 31 * result + this.object.hashCode();
    for (String item : this.parameters) {
      result = 31 * result + (item != null ? item.hashCode() : 0);
    }
    return result;
  }

  private void assertParams(String name, Class<?> object, String[] parameters) {
    if (name == null) {
      throw new IllegalArgumentException("Name value is not be null.");
    }
    if (object == null) {
      throw new IllegalArgumentException("ObjectClass getPropertyName value is not be null.");
    }
    if (parameters == null) {
      throw new IllegalArgumentException("Parameters value is not be null.");
    }
  }

  @Override
  public final QueryQualifier clone() throws CloneNotSupportedException {
    return SerializationUtil.duplicate(this);
  }
}
