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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

public final class PropertiesUtil {

  public static HashMap<String, String> fromClassPath(Class<?> domain, String filename) {
    HashMap<String, String> map = new HashMap<>();
    Properties properties = new Properties();
    try {
      properties.load(domain.getClassLoader().getResourceAsStream(filename));
      for (Entry<Object, Object> item : properties.entrySet()) {
        map.put(item.getKey().toString(), item.getValue().toString());
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Not load properties file " + filename, e);
    }
    return map;
  }
}
