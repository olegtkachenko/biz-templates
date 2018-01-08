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

package net.biztemplates.demo.persistence.managers;

import com.edevapps.biztemplates.persistence.EntityDataManager;
import javax.persistence.NoResultException;
import net.biztemplates.demo.common.services.CustomerStateManager;
import net.biztemplates.demo.persistence.entities.Customer;
import net.biztemplates.demo.persistence.queries.AllCustomersQuery;
import net.biztemplates.demo.persistence.queries.CustomerByInnQuery;
import net.biztemplates.demo.persistence.transformation.CustomerBuilder;
import net.biztemplates.demo.persistence.transformation.CustomerEntityBuilder;
import net.biztemplates.demo.persistence.transformation.CustomerMapper;

public abstract class CustomerManager extends EntityDataManager<Customer>
    implements
    CustomerStateManager {

  public CustomerManager() {
    super(Customer.class);
  }

  @Override
  public boolean contains(Customer customer) {
    return super.find(customer.getId()) != null;
  }

  @Override
  public net.biztemplates.demo.common.Customer attach(
      net.biztemplates.demo.common.Customer customer) {
    super.create(new CustomerEntityBuilder(customer).build(new Customer()));
    return customer;
  }

  @Override
  public void detach(net.biztemplates.demo.common.Customer customer) {
    Customer entity = findCustomerByInn(customer.getInn());
    assertEntity(entity, customer);
    super.delete(entity);
  }

  @Override
  public boolean isAttached(net.biztemplates.demo.common.Customer customer) {
    return findCustomerByInn(customer.getInn()) != null;
  }

  @Override
  public void updateCustomer(net.biztemplates.demo.common.Customer customer) {
    Customer entity = findCustomerByInn(customer.getInn());
    assertEntity(entity, customer);
    new CustomerEntityBuilder(customer).build(entity);
    super.update(entity);
  }

  @Override
  public void loadCustomer(net.biztemplates.demo.common.Customer customer) {
    Customer entity = findCustomerByInn(customer.getInn());
    assertEntity(entity, customer);
    new CustomerBuilder(entity).build(customer);
  }

  @Override
  public net.biztemplates.demo.common.Customer[] loadAllCustomers() {
    return new CustomerMapper().map(new AllCustomersQuery().search(getEntityManager()));
  }

  public Customer findCustomerByInn(String inn) {
    try {
      return new CustomerByInnQuery(inn).single(getEntityManager());
    } catch (NoResultException ignore) {
    }
    return null;
  }

  private void assertEntity(Customer entity,
      net.biztemplates.demo.common.Customer customer) {
    if (entity == null) {
      throw new IllegalArgumentException("Customer " + customer + " is not exist.");
    }
  }
}
