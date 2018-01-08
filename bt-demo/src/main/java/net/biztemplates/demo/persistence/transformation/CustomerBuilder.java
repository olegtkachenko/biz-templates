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

package net.biztemplates.demo.persistence.transformation;

import net.biztemplates.Builder;
import net.biztemplates.demo.common.Customer;

public class CustomerBuilder implements Builder<Customer> {

  private net.biztemplates.demo.persistence.entities.Customer entity;

  public CustomerBuilder() {
  }

  public CustomerBuilder(net.biztemplates.demo.persistence.entities.Customer entity) {
    assertArg(entity);
    this.entity = entity;
  }

  public net.biztemplates.demo.persistence.entities.Customer getEntity() {
    return entity;
  }

  public void setEntity(net.biztemplates.demo.persistence.entities.Customer entity) {
    assertArg(entity);
    this.entity = entity;
  }

  @Override
  public Customer build(Customer object) {
    object.setInn(this.entity.getInn());
    object.setFirstName(this.entity.getFirstName());
    object.setLastName(this.entity.getLastName());
    object.setMiddleName(this.entity.getMiddleName());
    return object;
  }

  private void assertArg(net.biztemplates.demo.persistence.entities.Customer entity) {
    if (entity == null) {
      throw new IllegalArgumentException("Customer entity value is not be null.");
    }
  }
}
