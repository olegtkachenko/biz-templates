//
package com.edevapps.tests.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.biztemplates.Query;


public class ObjectCollectionQuery implements Query<Object, Collection<Object>> {

  public static final String QUERY_NAME = "objectListQuery";
  public static final String P_SERARCH_OBJECT = "searchObject";

  private Class<?> searchObject;

  public ObjectCollectionQuery() {
  }

  public ObjectCollectionQuery(Class<?> searchObject) {
    this.searchObject = searchObject;
  }

  public Class<?> getSearchObject() {
    return searchObject;
  }

  public void setSearchObject(Class<?> searchObject) {
    this.searchObject = searchObject;
  }

  @Override
  public boolean isValidResource(Object source) {
    boolean rez;
    try {
      Collection<Object> l = (Collection<Object>) source;
      rez = true;
    } catch (ClassCastException ex) {
      rez = false;
    }
    return rez;
  }

  @Override
  public Object[] search(Collection<Object> resource) {
    ArrayList<Object> l = new ArrayList<>();
    for (Object item : resource) {
      if (item.getClass().equals(searchObject)) {
        l.add(item);
      }
    }
    return l.toArray(new Object[l.size()]);
  }

  @Override
  public String getId() {
    return QUERY_NAME;
  }

  @Override
  public Class<?> objectType() {
    return Object.class;
  }

  @Override
  public String[] parametersName() {
    return new String[]{P_SERARCH_OBJECT};
  }

  @Override
  public ObjectCollectionQuery setParameters(Map<String, Object> params) {
    if (params.containsKey(P_SERARCH_OBJECT)) {
      searchObject = (Class<?>) params.get(P_SERARCH_OBJECT);
    }
    return this;
  }

  @Override
  public ObjectCollectionQuery setParameter(String name, Object value) {
    if (P_SERARCH_OBJECT.equals(name) && value instanceof Class<?>) {
      searchObject = (Class<?>) value;
    }
    return this;
  }
}
