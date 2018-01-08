//
package com.edevapps.tests.environment;

import net.biztemplates.Component;
import net.biztemplates.ComponentId;

@ComponentId(TestComponent.COMPONENT_ID)
public interface TestComponent extends Component {

  String COMPONENT_ID = "testComponent";

  String test();

}
