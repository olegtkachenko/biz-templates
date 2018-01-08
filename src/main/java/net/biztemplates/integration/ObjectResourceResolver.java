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
import net.biztemplates.Component;
import net.biztemplates.Resolver;

public interface ObjectResourceResolver extends
    Resolver<ObjectResource<?, ?>, ObjectResourceQualifier>, Component {

  @SuppressWarnings("unchecked")
  default <I, T> ObjectResource<I, T> resolveOf(ObjectResourceQualifier qualifier)
      throws IllegalArgumentException {
    return (ObjectResource<I, T>) resolve(qualifier);
  }

  @Override
  default boolean isValidArgument(Object args) {
    return args instanceof ObjectResourceQualifier;
  }

  default ObjectResourceQualifier[] findQualifiers(Class<?> object, Class<?> index) {
    ArrayList<ObjectResourceQualifier> list = new ArrayList<>(0);
    for (ObjectResourceQualifier item : allArguments()) {
      if ((item.getObjectType().equals(object) || item.getObjectType().isAssignableFrom(object)
          || object
          .isAssignableFrom(item.getObjectType())) &&
          (item.getIndexType().equals(index) || item.getIndexType().isAssignableFrom(index) || index
              .isAssignableFrom(index))) {
        list.add(item);
      }
    }
    return list.toArray(new ObjectResourceQualifier[list.size()]);
  }

  default ObjectResourceQualifier findQualifier(Class<?> object, Class<?> index)
      throws IllegalArgumentException {
    //noinspection LoopStatementThatDoesntLoop
    return Arrays.stream(findQualifiers(object, index)).findFirst().orElse(null);
  }

  default ObjectResource<?, ?>[] findResources(Class<?> object, Class<?> index) {
    return Arrays.stream(findQualifiers(object, index)).map(this::resolve)
        .toArray(ObjectResource<?, ?>[]::new);
  }

  default ObjectResource<?, ?> findResource(Class<?> object, Class<?> index) {
    //noinspection LoopStatementThatDoesntLoop
    return Arrays.stream(findQualifiers(object, index)).findFirst().<ObjectResource<?, ?>>map(
        this::resolve)
        .orElse(null);
  }

}
