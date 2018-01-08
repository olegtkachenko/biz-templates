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

package net.biztemplates.demo.web.configs;

import com.edevapps.biztemplates.integration.spring.SpringComponentsResourceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("net.biztemplates.demo")
public class RootConfig {

  @Bean
  public SpringComponentsResourceAdapter springComponentsResourceAdapter() {
    return new SpringComponentsResourceAdapter();
  }
}
