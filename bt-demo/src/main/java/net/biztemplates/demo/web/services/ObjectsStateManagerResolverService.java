/*
 *     Copyright (c) 2017, The EDUARD BURENKOV (http://edevapps.com)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package net.biztemplates.demo.web.services;

import net.biztemplates.ObjectStateManager;
import net.biztemplates.ObjectStateManagerResolverComponent;
import net.biztemplates.demo.common.services.CustomerStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(ObjectStateManagerResolverComponent.COMPONENT_ID)
public class ObjectsStateManagerResolverService implements ObjectStateManagerResolverComponent {

  @Autowired
  private CustomerStateManager customerRepositiry;

  @Override
  public ObjectStateManager<?> resolve(Class<? extends ObjectStateManager<?>> args)
      throws IllegalArgumentException {
    if (CustomerStateManager.class.equals(args)) {
      return customerRepositiry;
    }
    throw new IllegalArgumentException("State manager " + args + "is not resolve.");
  }

  @Override
  public boolean isValidArgument(Object args) {
    if (!Class.class.isAssignableFrom(args.getClass())) {
      return false;
    }
    try {
      Class<? extends ObjectStateManager<?>>
          value =
          (Class<? extends ObjectStateManager<?>>) args;
    } catch (ClassCastException ignored) {
      return false;
    }
    return true;
  }

  @Override
  public Class<? extends ObjectStateManager<?>>[] allArguments() {
    return new Class[]{CustomerStateManager.class};
  }
}
