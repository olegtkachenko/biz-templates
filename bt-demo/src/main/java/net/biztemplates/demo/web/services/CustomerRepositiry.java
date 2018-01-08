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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.biztemplates.demo.common.Customer;
import net.biztemplates.demo.common.services.CustomerStateManager;
import net.biztemplates.demo.persistence.managers.CustomerManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomerRepositiry extends CustomerManager implements CustomerStateManager {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  @Transactional
  public Customer attach(Customer customer) {
    return super.attach(customer);
  }

  @Override
  @Transactional
  public void detach(Customer customer) {
    super.detach(customer);
  }

  @Override
  @Transactional
  public void updateCustomer(Customer customer) {
    super.updateCustomer(customer);
  }
}
