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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import net.biztemplates.Component;

public final class ObjectResources {

  private static final Map<String, ObjectResourceResolver> resolvers = new ConcurrentHashMap<>(0);

  private ObjectResources() {
  }

  public static boolean registerResolver(ObjectResourceResolver resolver) {
    boolean rez = false;
    synchronized (resolvers) {
      if (!resolvers.containsKey(resolver.getId())) {
        resolvers.put(resolver.getId(), resolver);
        rez = true;
      }
    }
    return rez;
  }

  public static boolean removeResolver(String name) {
    boolean rez = false;
    synchronized (resolvers) {
      if (resolvers.containsKey(name)) {
        resolvers.remove(name);
        rez = true;
      }
    }
    return rez;
  }

  public static void clearResolvers() {
    resolvers.clear();
  }

  public static ObjectResourceResolver resolver(String name) {
    return resolvers.get(name);
  }

  public static int countResolvers() {
    return resolvers.size();
  }

  public static <I, T> ObjectResource<I, T> findFirstResource(ObjectResourceQualifier qualifier) {
    //noinspection unchecked
    return (ObjectResource<I, T>) resolvers.values().stream()
        .filter(item -> item.containsArgument(qualifier))
        .findFirst().<ObjectResource<?, ?>>map(item -> item.resolve(qualifier)).orElse(null);
  }

  public static <R extends ObjectResource<I, T>, T, I> R findFirstResourceOf(
      ObjectResourceQualifier qualifier) {
    return (R) findFirstResource(qualifier);
  }

  public static <I, T> ObjectResource<I, T> firstResource(ObjectResourceQualifier qualifier)
      throws IndexOutOfBoundsException {
    for (ObjectResourceResolver resolver : resolvers.values()) {
      if (resolver.containsArgument(qualifier)) {
        return resolver.resolveOf(qualifier);
      }
    }
    throw new IndexOutOfBoundsException("Resource by qualifier '" + qualifier + "' is not find.");
  }

  public static <R extends ObjectResource<I, T>, T, I> R firstResourceOf(
      ObjectResourceQualifier qualifier)
      throws IndexOutOfBoundsException {
    return (R) firstResource(qualifier);
  }

  public static <I, T> ObjectResource<I, T> findFirstResource(Class<?> object, Class<?> index) {
    //noinspection unchecked
    return (ObjectResource<I, T>) resolvers.values().stream()
        .map(resolver -> resolver.findResource(object, index)).filter(Objects::nonNull).findFirst()
        .orElse(null);
  }

  public static <R extends ObjectResource<I, T>, T, I> R findFirstResourceOf(Class<?> object,
      Class<?> index) {
    return (R) findFirstResource(object, index);
  }

  public static ObjectResource<?, ?>[] findResources(ObjectResourceQualifier qualifier) {
    return resolvers.values().stream().filter(resolver -> resolver.containsArgument(qualifier))
        .map(resolver -> resolver.resolve(qualifier)).toArray(ObjectResource<?, ?>[]::new);
  }

  public static ObjectResource<?, ?>[] findResources(Class<?> object, Class<?> index) {
    List<ObjectResource<?, ?>> list = new ArrayList<>();
    resolvers.values().stream().map(item -> Arrays.asList(item.findResources(object, index)))
        .forEach(list::addAll);
    return list.toArray(new ObjectResource<?, ?>[list.size()]);
  }

  public static ObjectResourceQualifier findFirstQualifier(Class<?> object, Class<?> index) {
    return resolvers.values().stream().map(item -> item.findQualifier(object, index))
        .filter(Objects::nonNull)
        .findFirst().orElse(null);
  }

  public static ObjectResourceQualifier[] findQualifiers(Class<?> object, Class<?> index) {
    return resolvers.values().stream()
        .flatMap(resolver -> Arrays.stream(resolver.findQualifiers(object, index)))
        .toArray(ObjectResourceQualifier[]::new);
  }

  public static boolean containsResource(ObjectResourceQualifier qualifier) {
    return resolvers.values().stream().anyMatch(item -> item.containsArgument(qualifier));
  }

  public static <T, I> T object(Class<?> object, I index) throws IndexOutOfBoundsException {//NOPMD
    for (ObjectResource<?, ?> item : findResources(object, index.getClass())) {
      if (item.isValidId(index)) {
        Object obj = item.findObject(index);
        if (obj != null) {
          //noinspection unchecked
          return (T) obj;
        }
      }
    }
    throw new IndexOutOfBoundsException(
        "Resource by type '" + object + "' and index '" + index + " is not resolved.");
  }

  public static <C extends Component> C component(Class<C> type)
      throws IndexOutOfBoundsException {
    return object(type, Component.metaId(type));
  }

  public static <A extends AbstractObjectAdapter<T, I>, T, I> A adapter(
      Class<A> adapter, Class<?> object, I index)
      throws IndexOutOfBoundsException, IllegalArgumentException {
    A objectAdapter = null;
    for (ObjectResource<?, ?> item : findResources(object, index.getClass())) {
      if (item.containsObject(index)) {
        objectAdapter = (A) AbstractObjectAdapter.createInstance(adapter, item.qualifier(), index);
        objectAdapter.setResource((ObjectResource<I, T>) item);
        break;
      }
    }
    if (objectAdapter == null) {
      throw new IndexOutOfBoundsException(
          "Adapter by type '" + object + "' and index '" + index + " is not resolved.");
    }
    return objectAdapter;
  }

  public static <A extends AbstractObjectAdapter<C, String>, C extends Component> A componentAdapter(
      Class<A> adapter, Class<C> object)
      throws IndexOutOfBoundsException, IllegalArgumentException {
    //noinspection ConstantConditions
    String index = Component.metaId(object);
    A componentAdapter = null;
    for (ObjectResource<?, ?> item : findResources(object, String.class)) {
      if (item.containsObject(index)) {
        componentAdapter = (A) AbstractObjectAdapter
            .createInstance(adapter, item.qualifier(), index);
        componentAdapter.setResource((ObjectResource<String, C>) item);
      }
    }
    if (componentAdapter == null) {
      throw new IndexOutOfBoundsException("Adapter by type '" + object + "' is not resolved.");
    }
    return componentAdapter;
  }

  public static ObjectResourceQualifier[] qualifiers() {
    ArrayList<ObjectResourceQualifier> list = new ArrayList<>();
    resolvers.values().stream().map(item -> Arrays.asList(item.allArguments()))
        .forEach(list::addAll);
    return list.toArray(new ObjectResourceQualifier[list.size()]);
  }

  public static ObjectResourceResolver[] resolvers() {
    synchronized (resolvers) {
      return resolvers.values().toArray(new ObjectResourceResolver[resolvers.size()]);
    }
  }
}