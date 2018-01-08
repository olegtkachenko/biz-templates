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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.biztemplates.utils.PropertiesUtil;
import net.biztemplates.utils.UrlUtil;

public final class Configuration {

  public static final String DEFAULT_CONFIGURATION_FILE = "META-INF/domain.configuratoion.properties";

  private static final Map<String, Map<String, String>> configuratoions = new ConcurrentHashMap(
      0);

  private Configuration() {
  }

  public static void load(Class<?> source) {
    load(DEFAULT_CONFIGURATION_FILE, source);
  }

  public static void load(String path, Class<?> source) {
    configuratoions.put(UrlUtil.fileName(path), PropertiesUtil.fromClassPath(source, path));
  }

  public static boolean isLoad() {
    return !configuratoions.isEmpty();
  }

  public static Map<String, String> properties(String configuration) {
    synchronized (configuratoions) {
      if (configuratoions.containsKey(configuration)) {
        return new HashMap(configuratoions.get(configuration));
      }
    }
    throw new IllegalArgumentException(
        "A configuration with name '" + configuration + "' is not load.");
  }

  public static String property(String key) {
    synchronized (configuratoions) {
      for (Map<String, String> сonfiguration : configuratoions.values()) {
        if (сonfiguration.containsKey(key)) {
          return сonfiguration.get(key);
        }
      }
    }
    throw new IllegalArgumentException(
        "A property with key '" + key + "' is not present in configurations.");
  }

  public static boolean containsConfiguration(String name) {
    return configuratoions.containsKey(name);
  }

  public static boolean containsProperty(String key) {
    synchronized (configuratoions) {
      for (Map<String, String> сonfiguration : configuratoions.values()) {
        if (сonfiguration.containsKey(key)) {
          return true;
        }
      }
      return false;
    }
  }
}
