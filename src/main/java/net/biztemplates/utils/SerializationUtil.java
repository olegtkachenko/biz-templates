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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class SerializationUtil {

  @SuppressWarnings("unchecked")
  public static <T extends Serializable> T duplicate(T object) {
    T duplicate;
    ByteArrayOutputStream arrayOutputStream;
    ObjectOutputStream objectOutputStream;
    ByteArrayInputStream arrayInputStream;
    ObjectInputStream objectInputStream;

    try {
      arrayOutputStream = new ByteArrayOutputStream();
      objectOutputStream = new ObjectOutputStream(arrayOutputStream);
      objectOutputStream.writeObject(object);
      objectOutputStream.flush();
      arrayOutputStream.flush();
      objectOutputStream.close();
      arrayOutputStream.close();
      arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
      objectInputStream = new ObjectInputStream(arrayInputStream);
      duplicate = (T) objectInputStream.readObject();
    } catch (Exception ex) {
      throw new SerializationOperationException("Object duplication is failed.", ex);
    }

    return duplicate;
  }
}
