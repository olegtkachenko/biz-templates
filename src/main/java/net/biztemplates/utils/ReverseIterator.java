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

package net.biztemplates.utils;

import java.util.Iterator;
import java.util.List;

public class ReverseIterator<T> implements Iterator<T>, Iterable<T> {

  private final List<T> list;
  private int position;

  public ReverseIterator(List<T> list) {
    this.list = list;
    this.position = list.size() - 1;
  }

  @Override
  public Iterator<T> iterator() {
    return this;
  }

  @Override
  public synchronized boolean hasNext() {
    return this.position > 0;
  }

  @Override
  public synchronized T next() {
    return this.list.get(this.position--);
  }

}
