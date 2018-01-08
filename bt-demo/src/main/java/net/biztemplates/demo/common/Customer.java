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

public class Customer {

  private String inn;
  private String firstName;
  private String lastName;
  private String middleName;

  public Customer() {
  }

  public Customer(String inn) {
    this.inn = inn;
  }

  public Customer(String firstName, String lastName, String middleName) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
  }

  public Customer(String inn, String firstName, String lastName, String middleName) {
    this.inn = inn;
    this.firstName = firstName;
    this.lastName = lastName;
    this.middleName = middleName;
  }

  //Business method
  public void bizMethod() {
    System.out.println("Do work.");
  }

  public static Customer[] allCustomers() {
    return new CustomerRepository().loadAll();
  }

  public String getInn() {
    return inn;
  }

  public void setInn(String inn) {
    this.inn = inn;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public Customer save() {
    new CustomerRepository().save(this);
    return this;
  }

  public Customer load() {
    new CustomerRepository().load(this);
    return this;
  }

  public Customer delete() {
    new CustomerRepository().delete(this);
    return this;
  }

  @Override
  public String toString() {
    return "Customer{" +
        "inn=" + inn +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", middleName='" + middleName + '\'' +
        '}';
  }
}
