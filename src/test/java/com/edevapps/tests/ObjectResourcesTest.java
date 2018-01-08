//
package com.edevapps.tests;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;

import com.edevapps.tests.environment.ComponentsResource;
import com.edevapps.tests.environment.TestClass;
import com.edevapps.tests.environment.TestComponent;
import com.edevapps.tests.environment.TestComponentAdapter;
import net.biztemplates.Component;
import net.biztemplates.integration.InMemoryObjectResourceResolver;
import net.biztemplates.integration.ObjectResource;
import net.biztemplates.integration.ObjectResourceQualifier;
import net.biztemplates.integration.ObjectResources;
import org.junit.Test;

public class ObjectResourcesTest {

  @Test
  public void baseTest() {

    String testResourceName = "myResource";
    String testResolverName = "myResolver";

    ComponentsResource components = new ComponentsResource(testResourceName);
    TestClass obj = new TestClass("test");
    components.add(obj);
    InMemoryObjectResourceResolver resolver = new InMemoryObjectResourceResolver(testResolverName);
    resolver.add(components);

    int curResolvers = ObjectResources.countResolvers();
    ObjectResources.registerResolver(resolver);
    assertEquals(resolver, ObjectResources.resolver(testResolverName));

    ObjectResource<String, Component> resource = ObjectResources.firstResource(
        new ObjectResourceQualifier(testResourceName, Component.class, String.class));
    assertEquals(components, resource);

    TestComponent comp = ObjectResources.object(TestComponent.class, TestComponent.COMPONENT_ID);
    assertSame(obj, comp);

    comp = ObjectResources.component(TestComponent.class);
    assertSame(obj, comp);

    TestComponentAdapter adapter =
        ObjectResources
            .adapter(TestComponentAdapter.class, TestComponent.class, TestComponent.COMPONENT_ID);
    assertSame(obj, adapter.object());
    adapter.test();

    adapter = ObjectResources.componentAdapter(TestComponentAdapter.class, TestComponent.class);
    assertSame(obj, adapter.object());

    ObjectResourceQualifier qualifier = ObjectResources
        .findFirstQualifier(TestComponent.class, String.class);
    assertNotNull(qualifier);

    ObjectResources.removeResolver(testResolverName);
    assertEquals(curResolvers, ObjectResources.countResolvers());
  }

}
