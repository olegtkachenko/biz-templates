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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {

  public static String replace(String source, String pattern, String value) {
    StringBuffer buffer = new StringBuffer();
    Pattern regexp = Pattern.compile(pattern);
    Matcher matcher = regexp.matcher(source);
    while (matcher.find()) {
      matcher.appendReplacement(buffer, value);
    }
    matcher.appendTail(buffer);

    return buffer.toString();
  }

}
