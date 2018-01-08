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

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class FactoryResolverImpl implements FactoryResolver, Composite<Class<?>, Factory<?>> {

  private final Map<Class<?>, Factory<?>> items = new ConcurrentHashMap<>(0);

  protected Map<Class<?>, Factory<?>> getItems() {
    return items;
  }

  @Override
  public Factory<?> resolve(Class<?> args) throws IllegalArgumentException {
    try {
      return this.items.get(args);
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public Factory<?> add(Factory<?> value) {
    if (isValidArgument(value.getClass())) {
      throw new IllegalArgumentException("Query with such findQualifier already exist.");
    }

    this.items.put(value.getClass(), value);
    return value;
  }

  @Override
  public boolean remove(Class<?> id) {
    boolean rez = false;
    synchronized (this.items) {
      if (this.items.containsKey(id)) {
        this.items.remove(id);
        rez = true;
      }
    }
    return rez;
  }

  @Override
  public boolean isValidArgument(Object id) {
    //noinspection SuspiciousMethodCalls
    return this.items.containsKey(id);
  }

  @Override
  public boolean containsIndex(Object id) {
    //noinspection SuspiciousMethodCalls
    return this.items.containsKey(id);
  }

  @Override
  public Factory<?> get(Class<?> id) {
    return this.items.get(id);
  }

  @Override
  public int quantity() {
    return this.items.size();
  }

  @Override
  public Iterator<Factory<?>> iterator() {
    return this.items.values().iterator();
  }

  @Override
  public Class<?>[] allArguments() {
    return items.keySet().toArray(new Class[items.size()]);
  }
}
