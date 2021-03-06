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

import java.util.Map;

public interface Queryable<T> {

  Object getResource();

  @SuppressWarnings("unchecked")
  default <R> T[] select(Query<T, R> query) {
    if (!query.isValidResource(getResource())) {
      throw new IllegalArgumentException("Query findFirstResource is not valid.");
    }

    return query.search((R) getResource());
  }

  @SuppressWarnings("unchecked")
  default <R> T[] select(Query<T, R> query, Map<String, Object> params) {
    if (!query.isValidResource(getResource())) {
      throw new IllegalArgumentException("Query findFirstResource is not valid.");
    }

    query.setParameters(params);
    return query.search((R) getResource());
  }
}
