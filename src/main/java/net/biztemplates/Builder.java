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

public interface Builder<T> {

  T build(T object);

  default T build(Class<T> cls) throws BuildException {
    try {
      return build(cls.newInstance());
    } catch (Exception e) {
      throw new BuildException("Object building is failed.", e);
    }
  }

  default T buildWith(Factory<T> factory) {
    return build(factory.createInstance());
  }

  default T buildWith(Class<? extends Factory<T>> factory) throws BuildException {
    try {
      return buildWith(factory.newInstance());
    } catch (Exception e) {
      throw new BuildException("Object building is failed.", e);
    }
  }
}
