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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public final class ReflectionUtil {

  public static final int MIN_PREFIX_LENGHT = 1;

  public static Method[] methodsByPrefix(Class<?> type, String prefix) {
    ArrayList<Method> l = new ArrayList<>(0);
    for (Method methodItm : type.getDeclaredMethods()) {
      if (methodItm.getName().substring(0, prefix.length()).equals(prefix)) {
        l.add(methodItm);
      }
    }
    return l.toArray(new Method[l.size()]);
  }

  public static Class<?>[] parents(Class<?> type) {
    ArrayList<Class<?>> l = new ArrayList<>(0);
    Class<?> sc = type;
    do {
      sc = sc.getSuperclass();
      if (sc != null) {
        l.add(sc);
      }
    } while (sc != null);
    return l.toArray(new Class<?>[l.size()]);
  }

  public static Class<?>[] interfaces(Class<?> type) {
    HashSet<Class<?>> l = new HashSet<>(0);
    Collections.addAll(l, type.getInterfaces());

    if (type.getSuperclass() != null) {
      for (Class<?> i2 : parents(type)) {
        for (Class<?> i3 : i2.getInterfaces()) {
          if (!l.contains(i3)) {
            l.add(i3);
          }
        }
      }
    }
    return l.toArray(new Class<?>[l.size()]);
  }

  public static boolean isImplement(Class<?> type, Class<?> ifce) {
    if (!ifce.isInterface()) {
      throw new IllegalArgumentException("Interface class argument is not valid.");
    }
    for (Class<?> item : interfaces(type)) {
      if (item.equals(ifce)) {
        return true;//NOPMD
      }
    }
    return false;
  }

  public static String addPrefix(String prefix, String name) {
    return prefix + name.substring(0, 1).toUpperCase() +
        name.substring(1, name.length());
  }

  public static String buildGetter(String name) {
    return addPrefix("get", name);
  }

  public static String buildSetter(String name) {
    return addPrefix("set", name);
  }

  public static String removePrefix(int prefixLength, String name) {
    if (prefixLength < MIN_PREFIX_LENGHT) {
      throw new IllegalArgumentException("Prefix length is not be less than of 1.");
    }

    StringBuilder sb = new StringBuilder();
    String s = name.substring(prefixLength, name.length());
    sb.append(s.substring(0, 1).toLowerCase());
    sb.append(s.substring(1, s.length()));

    return sb.toString();
  }

  public static boolean isAnnotated(Class<?> type, Class<?> extIface,
      Class<? extends Annotation> annotation) {
    if (extIface != null && !extIface.isInterface()) {
      throw new IllegalArgumentException("'extIface' value must be interface.");
    }

    if (type.isAnnotationPresent(annotation)) {
      return true;//NOPMD
    }

    for (Class<?> item : type.getInterfaces()) {
      if (extIface != null && !extIface.isAssignableFrom(item)) {
        continue;
      }

      if (type.isAnnotationPresent(annotation)) {
        return true;//NOPMD
      }
    }
    return false;
  }

  public static boolean isAnnotated(Class<?> type, Class<? extends Annotation> annotation) {
    return isAnnotated(type, null, annotation);
  }

  public static <T extends Annotation> T getAnnotation(Class<?> type, Class<?> extIface,
      Class<T> annotation) {
    if (extIface != null && !extIface.isInterface()) {
      throw new IllegalArgumentException("'extIface' value must be interface.");
    }

    if (type.isAnnotationPresent(annotation)) {
      return type.getAnnotation(annotation);//NOPMD
    }

    for (Class<?> item : type.getInterfaces()) {
      if (extIface != null && !extIface.isAssignableFrom(item)) {
        continue;
      }

      if (type.isAnnotationPresent(annotation)) {
        return type.getAnnotation(annotation);//NOPMD
      }
    }
    return null;
  }

  public static <T extends Annotation> T getAnnotation(Class<?> type, Class<T> annotation) {
    return getAnnotation(type, null, annotation);
  }

  public static Method getSetterMethod(Object object, String propertyName, Class<?> propertyClass)
      throws IllegalArgumentException {
    try {
      return object.getClass().getMethod(
          ReflectionUtil.buildSetter(propertyName), propertyClass);
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static Method getGetterMethod(Object object, String propertyName)
      throws IllegalArgumentException {
    try {
      return object.getClass().getMethod(
          ReflectionUtil.buildGetter(propertyName));
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
