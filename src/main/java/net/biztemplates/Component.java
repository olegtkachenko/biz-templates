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


import java.lang.reflect.Field;

public interface Component extends Indexed<String> {

  String COMPONENT_ID = null;

  static String metaId(Class<? extends Component> type) {
    if (type.isAnnotationPresent(ComponentId.class)) {
      return type.getAnnotation(ComponentId.class).value();
    }

    String id = null;
    try {
      Field idField = type.getField("COMPONENT_ID");
      if (idField.getDeclaringClass().equals(String.class)) {
        id = (String) idField.get(type);
      }
    } catch (Exception ignored) { }

    if (id != null) {
      return id;
    }

    for (Class<?> item : type.getInterfaces()) {
      if (!Component.class.isAssignableFrom(item)) {
        continue;
      }

      if (item.isAnnotationPresent(ComponentId.class)) {
        return item.getAnnotation(ComponentId.class).value();
      }
    }
    return type.getName();
  }

  static String metaId(Component comp) {
    return metaId(comp.getClass());
  }

  @Override
  default String getId() {
    return metaId(this);
  }
}