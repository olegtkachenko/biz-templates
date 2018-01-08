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
import net.biztemplates.integration.ObjectStateManagerAdapter;

public class CustomerRepository extends ObjectStateManagerAdapter<CustomerStateManager, Customer> {

  private Strategy<CustomerRepository, Customer> deleteCustomerStrategy;

  public CustomerRepository() {
    super(CustomerStateManager.class);
    this.deleteCustomerStrategy = new DefaultDeleteCustomerStrategy(this);
  }

  public CustomerRepository(Strategy<CustomerRepository, Customer> deleteCustomerStrategy) {
    super(CustomerStateManager.class);
    this.deleteCustomerStrategy = deleteCustomerStrategy;
    this.deleteCustomerStrategy.setHolder(this);
  }

  public void setDeleteCustomerStrategy(
      Strategy<CustomerRepository, Customer> deleteCustomerStrategy) {
    this.deleteCustomerStrategy = deleteCustomerStrategy;
  }

  public Customer[] loadAll() {
    return super.stateManager().loadAllCustomers();
  }

  public void save(Customer customer) {
    CustomerStateManager stateManager = super.stateManager();
    if (stateManager.isAttached(customer)) {
      stateManager.updateCustomer(customer);
    } else {
      stateManager.attach(customer);
    }
  }

  public void load(Customer customer) {
    if (customer.getInn() == null) {
      throw new IllegalArgumentException("Customer getId property is not be null.");
    }
    super.stateManager().loadCustomer(customer);
  }

  public void delete(Customer customer) {
    deleteCustomerStrategy.apply(customer);
  }

  protected void assertState() {
    if (this.deleteCustomerStrategy == null) {
      throw new IllegalStateException("Delete customer strategy is not set.");
    }
  }
}
