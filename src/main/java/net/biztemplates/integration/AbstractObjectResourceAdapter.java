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

package net.biztemplates.integration;

public abstract class AbstractObjectResourceAdapter<I, T> implements ObjectResourceResolver,
    ObjectResource<I, T> {

  protected final boolean register() {
    return ObjectResources.registerResolver(this);
  }

  protected final boolean remove() {
    return ObjectResources.removeResolver(this.getId());
  }

  @Override
  public boolean isValidArgument(Object args) {
    return args instanceof ObjectResourceQualifier && qualifier().equals(args);
  }

  @Override
  public ObjectResourceQualifier[] allArguments() {
    return new ObjectResourceQualifier[]{qualifier()};
  }

}
