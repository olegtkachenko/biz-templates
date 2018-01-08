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
import java.util.LinkedList;
import java.util.List;

public class CompositeBuilderImpl<T> implements CompositeBuilder<T> {

  private final List<Builder<T>> items = new LinkedList<>();

  @Override
  public Builder<T> add(Builder<T> value) {
    this.items.add(value);
    return value;
  }

  protected List<Builder<T>> getItems() {
    return items;
  }

  @Override
  public boolean containsIndex(Object value) {
    //noinspection SuspiciousMethodCalls
    return items.contains(value);
  }

  public Integer indexOf(Builder<T> value) {
    return items.indexOf(value);
  }

  @Override
  public Builder<T> get(Integer integer) {
    return items.get(integer);
  }

  @Override
  public boolean remove(Integer value) {
    //noinspection SuspiciousMethodCalls
    return items.remove(value);
  }

  @Override
  public int quantity() {
    return items.size();
  }

  @Override
  public Iterator<Builder<T>> iterator() {
    return items.iterator();
  }
}
