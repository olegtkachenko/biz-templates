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

import net.biztemplates.Configuration;
import net.biztemplates.ObjectStateManager;
import net.biztemplates.ObjectStateManagerResolverComponent;

public class ObjectStateManagerAdapter<M extends ObjectStateManager<T>, T>
    extends AbstractObjectAdapter<ObjectStateManagerResolverComponent, String> implements
    ObjectStateManager<T> {

  public static final String PROP_OBJECT_STATE_MANAGER_RESOURSE = "biztemplates.integration.object_states_resource";

  private Class<M> managerType;

  public ObjectStateManagerAdapter(Class<M> managerType) {
    super(new ObjectResourceQualifier(
        Configuration.property(PROP_OBJECT_STATE_MANAGER_RESOURSE),
        Object.class,
        String.class), ObjectStateManagerResolverComponent.COMPONENT_ID);
    assertArgs(managerType);
    this.managerType = managerType;
  }

  public M stateManager() {
    return this.object().resolveOf(managerType);
  }

  @Override
  public T attach(T value) {
    return stateManager().attach(value);
  }

  @Override
  public void detach(T value) {
    stateManager().attach(value);
  }

  @Override
  public boolean isAttached(T value) {
    return stateManager().isAttached(value);
  }

  @Override
  public Class<? extends T> getObjectType() {
    return stateManager().getObjectType();
  }

  private void assertArgs(Class<M> managerType) {
    if (managerType == null) {
      throw new IllegalArgumentException("Manager type argument is not be null.");
    }
  }
}
