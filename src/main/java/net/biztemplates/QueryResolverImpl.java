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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QueryResolverImpl implements QueryResolver,
    Composite<QueryQualifier, Query<?, ?>> {

  private final Map<QueryQualifier, Query<?, ?>> queries = new ConcurrentHashMap<>(0);

  protected Map<QueryQualifier, Query<?, ?>> getQueries() {
    return queries;
  }

  @Override
  public Query<?, ?> resolve(QueryQualifier args) {
    return get(args);
  }

  @Override
  public Query<?, ?> add(Query<?, ?> value) {
    if (isValidArgument(value.qualifier())) {
      throw new IllegalArgumentException("Query with such findQualifier already exist.");
    }

    this.queries.put(value.qualifier(), value);
    return value;
  }

  @Override
  public boolean remove(QueryQualifier id) {
    boolean rez = false;
    synchronized (this.queries) {
      if (this.queries.containsKey(id)) {
        this.queries.remove(id);
        rez = true;
      }
    }
    return rez;
  }

  @Override
  public boolean isValidArgument(Object id) {
    //noinspection SuspiciousMethodCalls
    return this.queries.containsKey(id);
  }

  @Override
  public boolean containsIndex(Object id) {
    //noinspection SuspiciousMethodCalls
    return this.queries.containsKey(id);
  }

  @Override
  public Query<?, ?> get(QueryQualifier id) {
    return this.queries.get(id);
  }

  @Override
  public int quantity() {
    return this.queries.size();
  }

  @Override
  public Iterator<Query<?, ?>> iterator() {
    return this.queries.values().iterator();
  }

  @Override
  public QueryQualifier[] allArguments() {
    return queries.keySet().toArray(new QueryQualifier[queries.size()]);
  }
}
