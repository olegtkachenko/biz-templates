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

package net.biztemplates.demo.web.controllers;

import java.util.Arrays;
import net.biztemplates.demo.common.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/customer")
public class CustomerController {

  @RequestMapping(value = "/all",
      method = RequestMethod.GET)
  public String allCustomers(Model model) {
    model.addAttribute("customers", Arrays.asList(Customer.allCustomers()));
    return "customers";
  }

  @RequestMapping(value = "/new",
      method = RequestMethod.GET)
  public String newCustomers(Model model) {
    model.addAttribute("customer", new Customer());
    return "customer";
  }

  @RequestMapping(value = "/save",
      method = RequestMethod.POST)
  public String saveCustomer(
      @ModelAttribute
          Customer customer) {
    customer.save();
    return "redirect:all";
  }

  @RequestMapping(value = "/edit/{inn}",
      method = RequestMethod.GET)
  public String editCustomer(Model model,
      @PathVariable
          String inn) {
    model.addAttribute("customer", new Customer(inn).load());
    return "customer";
  }

  @RequestMapping(value = "/delete/{inn}",
      method = RequestMethod.GET)
  public String deleteCustomer(
      @PathVariable
          String inn) {
    new Customer(inn).delete();
    return "redirect:/customer/all";
  }
}
