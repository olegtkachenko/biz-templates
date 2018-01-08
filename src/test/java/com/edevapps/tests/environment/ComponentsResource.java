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

package com.edevapps.tests.environment;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.biztemplates.Component;
import net.biztemplates.Composite;
import net.biztemplates.integration.ObjectResource;
import net.biztemplates.integration.ObjectResourceQualifier;

public class ComponentsResource implements ObjectResource<String, Component>,
    Composite<String, Component> {

  private String name;
  private Map<String, Component> items = new ConcurrentHashMap<>();

  public ComponentsResource(String name) {
    this.name = name;
  }

  public Map<String, Component> getItems() {
    return items;
  }

  @Override
  public Component add(Component value) {
    return items.put(value.getId(), value);
  }

  @Override
  public boolean remove(String id) {
    if (!this.items.containsKey(id)) {
      return false;
    }
    items.remove(id);
    return true;
  }

  @Override
  public boolean containsIndex(Object id) {
    return this.items.containsKey(id);
  }

  @Override
  public Component get(String id) {
    return items.get(id);
  }

  @Override
  public int quantity() {
    return items.size();
  }

  @Override
  public Iterator<Component> iterator() {
    return items.values().iterator();
  }

  @Override
  public ObjectResourceQualifier qualifier() {
    return new ObjectResourceQualifier(this.name, Component.class, String.class);
  }

  @Override
  public boolean isValidId(Object id) {
    return id instanceof String;
  }

  @Override
  public boolean containsObject(Object id) {
    return items.containsKey(id);
  }

  @Override
  public Component object(String id) throws IndexOutOfBoundsException {
    return items.get(id);
  }
}
