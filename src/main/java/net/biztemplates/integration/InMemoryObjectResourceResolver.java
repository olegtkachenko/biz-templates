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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryObjectResourceResolver implements ObjectResourceResolver,
    Set<ObjectResource<?, ?>> {

  private final Set<ObjectResource<?, ?>> resources = ConcurrentHashMap.newKeySet(0);
  private String name;

  public InMemoryObjectResourceResolver() {
    this.name = this.getClass().getName();
  }

  public InMemoryObjectResourceResolver(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Name is not be null or empty.");
    }

    this.name = name;
  }

  @Override
  public String getId() {
    return this.name;
  }

  @Override
  public ObjectResource<?, ?> resolve(ObjectResourceQualifier args)
      throws IllegalArgumentException {
    for (Object item : this.resources.toArray()) {
      ObjectResource<?, ?> resource = (ObjectResource<?, ?>) item;
      if (args.getObjectType().isAssignableFrom(resource.qualifier().getObjectType())
          && args.getIndexType().equals(resource.qualifier().getIndexType())) {
        return resource;
      }
    }
    throw new IllegalArgumentException(
        "Object findFirstResource by findFirstQualifier '" + args + "'  is not resolved.");
  }

  @Override
  public int size() {
    return this.resources.size();
  }

  @Override
  public boolean isEmpty() {
    return this.resources.isEmpty();
  }

  @Override
  public boolean isValidArgument(Object o) {
    //noinspection SuspiciousMethodCalls
    return this.resources.contains(o);
  }

  @Override
  public boolean contains(Object o) {
    return this.resources.contains(o);
  }

  @Override
  public ObjectResource<?, ?>[] findResources(Class<?> object, Class<?> index) {
    List<ObjectResource<?, ?>> l = new ArrayList<>();
    for (Object item : resources.toArray()) {
      ObjectResource<?, ?> r = (ObjectResource<?, ?>) item;
      ObjectResourceQualifier q = r.qualifier();
      if ((q.getObjectType().isAssignableFrom(object) || object.isAssignableFrom(q.getObjectType()))
          && index.equals(q.getIndexType())) {
        l.add(r);
      }
    }
    return l.toArray(new ObjectResource<?, ?>[l.size()]);
  }

  @Override
  public ObjectResourceQualifier[] allArguments() {
    return Arrays.stream(this.resources.toArray())
        .map(item -> ((ObjectResource<?, ?>) item).qualifier())
        .toArray(ObjectResourceQualifier[]::new);
  }

  @Override
  public Iterator<ObjectResource<?, ?>> iterator() {
    return this.resources.iterator();
  }

  @Override
  public Object[] toArray() {
    return this.resources.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    //noinspection SuspiciousToArrayCall
    return this.resources.toArray(a);
  }

  @Override
  public boolean add(ObjectResource<?, ?> resource) {
    return this.resources.add(resource);
  }

  @Override
  public boolean remove(Object o) {
    return this.resources.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return this.resources.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends ObjectResource<?, ?>> c) {
    return this.resources.addAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return this.resources.retainAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return this.resources.removeAll(c);
  }

  @Override
  public void clear() {
    this.resources.clear();
  }

}
