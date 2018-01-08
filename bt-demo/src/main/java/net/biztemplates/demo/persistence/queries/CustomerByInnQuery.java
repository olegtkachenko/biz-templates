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

package net.biztemplates.demo.persistence.queries;

import com.edevapps.biztemplates.persistence.EntityQuery;
import net.biztemplates.demo.persistence.entities.Customer;

public class CustomerByInnQuery extends EntityQuery<Customer> {

  public CustomerByInnQuery() {
    super(Customer.class);
  }

  public CustomerByInnQuery(String inn) {
    super(Customer.class);
    setInn(inn);
  }

  public void setInn(String inn) {
    super.setParameter(Customer.P_INN, inn);
  }

  @Override
  protected boolean isNamed() {
    return true;
  }

  @Override
  protected String getQueryName() throws UnsupportedOperationException {
    return Customer.Q_BY_INN;
  }

  @Override
  protected Customer[] createEntityArray(int size) {
    return new Customer[size];
  }

  @Override
  public String[] parametersName() {
    return new String[]{Customer.P_INN};
  }
}
