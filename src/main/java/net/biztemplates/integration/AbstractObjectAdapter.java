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

import net.biztemplates.Indexed;
import net.biztemplates.ObjectAdapter;

public abstract class AbstractObjectAdapter<T, I> implements ObjectAdapter, Indexed<I> {

  private ObjectResourceQualifier qualifier;
  private I index;
  private ObjectResource<I, T> resource;

  public AbstractObjectAdapter() {
  }

  public AbstractObjectAdapter(ObjectResourceQualifier qualifier, I index) {
    if (qualifier == null) {
      throw new IllegalArgumentException("Argument 'qualifier' is not be null.");
    }
    if (index == null) {
      throw new IllegalArgumentException("Argument 'index' is not be null.");
    }
    this.index = index;
    this.qualifier = qualifier;
  }

  public static <T, I> AbstractObjectAdapter<T, I> createInstance(
      Class<? extends AbstractObjectAdapter<T, I>> adapter, ObjectResourceQualifier qualifier,
      I index) {
    AbstractObjectAdapter<T, I> objectAdapter;
    try {
      objectAdapter = adapter.newInstance();
    } catch (InstantiationException e) {
      throw new IllegalArgumentException("Adapter instance creation exception.", e);
    } catch (IllegalAccessException e) {
      throw new IllegalArgumentException("Adapter instance creation exception.", e);
    }
    objectAdapter.qualifier = qualifier.clone();
    objectAdapter.index = index;
    objectAdapter.assertState();
    return objectAdapter;
  }

  @Override
  public I getId() {
    return this.index;
  }

  public ObjectResourceQualifier qualifier() {
    return qualifier.clone();
  }

  @Override
  public T object() throws IllegalStateException, IndexOutOfBoundsException {
    return objectFromResources();
  }

  @Override
  public boolean isValid() {
    return this.qualifier != null && this.index != null;
  }

  protected ObjectResource<I, T> getResource() {
    return this.resource;
  }

  void setResource(ObjectResource<I, T> resource) {
    this.resource = resource;
  }

  public boolean isConnected() {
    return this.resource != null;
  }

  public void connect() {
    assertState();
    for (ObjectResource<?, ?> resource : ObjectResources.findResources(this.qualifier)) {
      if (resource.containsObject(this.index)) {
        this.resource = (ObjectResource<I, T>) resource;
        break;
      }
    }
  }

  private T objectFromResources() throws IllegalStateException, IndexOutOfBoundsException {
    assertState();
    if (!isConnected()) {
      connect();
    }
    return resource.object(this.index);
  }

  protected void assertState() throws IllegalStateException {
    if (!isValid()) {
      throw new IllegalStateException("Object is not valid.");
    }
  }
}
