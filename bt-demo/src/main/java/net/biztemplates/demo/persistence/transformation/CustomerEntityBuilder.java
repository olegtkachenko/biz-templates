/*
 *     Copyright (c) 2017, The EDUARD BURENKOV (http://edevapps.com)
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package net.biztemplates.demo.persistence.transformation;

import net.biztemplates.Builder;
import net.biztemplates.demo.persistence.entities.Customer;

public class CustomerEntityBuilder implements Builder<Customer> {

  private net.biztemplates.demo.common.Customer customer;

  public CustomerEntityBuilder() {
  }

  public CustomerEntityBuilder(net.biztemplates.demo.common.Customer customer) {
    assertArg(customer);
    this.customer = customer;
  }

  public void setCustomer(net.biztemplates.demo.common.Customer customer) {
    assertArg(customer);
    this.customer = customer;
  }

  @Override
  public Customer build(Customer object) {
    object.setInn(this.customer.getInn());
    object.setFirstName(this.customer.getFirstName());
    object.setLastName(this.customer.getLastName());
    object.setMiddleName(this.customer.getMiddleName());
    return object;
  }

  private void assertArg(net.biztemplates.demo.common.Customer customer) {
    if (customer == null) {
      throw new IllegalArgumentException("Customer value is not be null.");
    }
  }
}
