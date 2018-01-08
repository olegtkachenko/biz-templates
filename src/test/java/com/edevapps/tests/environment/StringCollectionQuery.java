//
package com.edevapps.tests.environment;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import net.biztemplates.Query;

public class StringCollectionQuery implements Query<String, Collection<String>> {

  public static final String QUERY_NAME = "stringListQuery";
  public static final String P_SERARCH_STRING = "searchString";

  private String searchString;

  public StringCollectionQuery() {
  }

  public StringCollectionQuery(String searchString) {
    this.searchString = searchString;
  }

  @Override
  public String getId() {
    return QUERY_NAME;
  }

  @Override
  public Class<?> objectType() {
    return String.class;
  }

  @Override
  public boolean isValidResource(Object source) {
    if (source == null) {
      throw new IllegalArgumentException("Value is not be null.");
    }

    boolean rez;
    try {
      Collection<String> c = (Collection<String>) source;
      rez = true;
    } catch (ClassCastException e) {
      rez = false;
    }
    return rez;
  }

  public String getSearchString() {
    return searchString;
  }

  public void setSearchString(String searchString) {
    this.searchString = searchString;
  }

  @Override
  public String[] search(Collection<String> resource) {
    ArrayList<String> l = new ArrayList<>();
    for (String item : resource) {
      if (item.indexOf(searchString) > 0) {
        l.add(item);
      }
    }
    return l.toArray(new String[l.size()]);
  }

  @Override
  public String[] parametersName() {
    return new String[]{P_SERARCH_STRING};
  }

  @Override
  public StringCollectionQuery setParameters(Map<String, Object> params) {
    if (params.containsKey(P_SERARCH_STRING)) {
      searchString = (String) params.get(P_SERARCH_STRING);
    }
    return this;
  }

  @Override
  public StringCollectionQuery setParameter(String name, Object value) {
    if (P_SERARCH_STRING.equals(name) && value instanceof String) {
      searchString = (String) value;
    }
    return this;
  }
}
