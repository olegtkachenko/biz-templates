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

package net.biztemplates.demo.common;

import net.biztemplates.Strategy;
import net.biztemplates.demo.common.services.CustomerStateManager;

public class DefaultDeleteCustomerStrategy implements Strategy<CustomerRepository, Customer> {

  private CustomerRepository customerRepository;

  public DefaultDeleteCustomerStrategy() {
  }

  public DefaultDeleteCustomerStrategy(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public void setHolder(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public void apply(Customer customer) {
    assertState();
    CustomerStateManager stateManager = customerRepository.stateManager();
    if (customer.getInn() == null) {
      return;
    } else if (stateManager.isAttached(customer)) {
      stateManager.detach(customer);
    } else {
      throw new IndexOutOfBoundsException("Customer " + customer + " is not attached.");
    }
  }

  private void assertState() {
    if (this.customerRepository == null) {
      throw new IllegalStateException("Customer repository is not set.");
    }
  }
}
