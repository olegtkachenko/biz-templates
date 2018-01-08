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


public interface Visitor {

  default <T> void visit(T object) throws IllegalArgumentException {
    if (!isVisitable(object.getClass())) {
      throw new IllegalArgumentException("This object '" + object + "' is not visitable.");
    }

    //noinspection unchecked
    ((Visit<T>) beginVisit(object.getClass())).meeting(object);
  }

  Visit<?> beginVisit(Class<?> object) throws IllegalArgumentException;

  boolean isVisitable(Class<?> object);
}
