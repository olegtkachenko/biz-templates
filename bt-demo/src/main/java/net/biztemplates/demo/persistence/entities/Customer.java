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

package net.biztemplates.demo.persistence.entities;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@NamedQueries({
    @NamedQuery(name = Customer.Q_ALL,
        query = "SELECT c FROM Customer c ORDER BY c.firstName, c.lastName, c.middleName"),
    @NamedQuery(name = Customer.Q_BY_INN,
        query = "SELECT c FROM Customer c WHERE c.inn = :" + Customer.P_INN)
})
public class Customer implements Serializable {

  public static final String Q_ALL = "Customer.all";
  public static final String Q_BY_INN = "Customer.byInn";
  public static final String P_INN = "inn";

  @Id
  @SequenceGenerator(name = "customer_id_seq_gen",
      sequenceName = "customer_id_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE,
      generator = "customer_id_seq_gen")
  private Long id;
  @Column(name = "inn", unique = true, nullable = false, length = 9)
  private String inn;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "middle_name")
  private String middleName;

  public Customer() {
  }

  public Customer(Long id, String inn, String firstName, String lastName, String middleName) {
    this.id = id;
    this.inn = inn;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public String toString() {
    return "Customer{" +
        "id=" + id +
        ", inn='" + inn + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", middleName='" + middleName + '\'' +
        '}';
  }
}
