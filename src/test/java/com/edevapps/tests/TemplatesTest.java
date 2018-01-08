//
package com.edevapps.tests;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;

import com.edevapps.tests.environment.ObjectCollectionQuery;
import com.edevapps.tests.environment.StringCollectionQuery;
import com.edevapps.tests.environment.TestClass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import net.biztemplates.CompositeBuilderImpl;
import net.biztemplates.Configuration;
import net.biztemplates.Event;
import net.biztemplates.EventsRecorder;
import net.biztemplates.QueryResolverImpl;
import net.biztemplates.Query;
import net.biztemplates.QueryQualifier;
import org.junit.Test;

public class TemplatesTest {

  @Test
  public void compositeBuilderImplTest() {
    Integer i = 0;
    CompositeBuilderImpl<Integer> builders = new CompositeBuilderImpl<>();
    builders.add((v) -> {
      v++;
      return v;
    });
    builders.add((v) -> {
      v++;
      return v;
    });
    builders.add((v) -> {
      v++;
      return v;
    });
    i = builders.build(i);
    assertEquals(i, Integer.valueOf(3));
  }

  @Test
  public void queryQualifierTest() {
    QueryQualifier queryQualifier1
        = new QueryQualifier("query", TestClass.class, "stringProperty");
    QueryQualifier queryQualifier2
        = new QueryQualifier("query", TestClass.class, "stringProperty");
    assertTrue(queryQualifier1.equals(queryQualifier2));
  }

  @Test
  public void defaultQueryResolverTest() {

    ArrayList<String> strings = new ArrayList<>();
    strings.add("qwertyTest1");
    strings.add("qwertyTest2");
    strings.add("qwertyTest3");
    ArrayList<Object> objects = new ArrayList<>();
    objects.add(new String(""));
    TestClass testClass = new TestClass("");
    objects.add(testClass);
    objects.add(new Integer(0));

    QueryResolverImpl queries = new QueryResolverImpl();
    queries.add(new StringCollectionQuery());
    queries.add(new ObjectCollectionQuery());

    QueryQualifier strQueryQualifier =
        new QueryQualifier(
            StringCollectionQuery.QUERY_NAME,
            String.class,
            StringCollectionQuery.P_SERARCH_STRING);
    QueryQualifier objQueryQualifier =
        new QueryQualifier(
            ObjectCollectionQuery.QUERY_NAME,
            Object.class,
            ObjectCollectionQuery.P_SERARCH_OBJECT);

    Query<String, Collection<String>> strQuery = queries.resolveOf(strQueryQualifier);
    Query<Object, Collection<Object>> objQuery = queries.resolveOf(objQueryQualifier);

    Map<String, Object> params = new HashMap<>();//NOPMD
    params.put(StringCollectionQuery.P_SERARCH_STRING, "Test2");
    params.put(ObjectCollectionQuery.P_SERARCH_OBJECT, TestClass.class);
    strQuery.setParameters(params);
    objQuery.setParameters(params);

    String str = strQuery.searchFirst(strings);
    Object obj = objQuery.searchFirst(objects);

    assertEquals("qwertyTest2", str);
    assertSame(testClass, obj);
  }

  @Test
  public void eventsRecorderTest() {
    String memberName = "test";
    EventsRecorder recorder = new EventsRecorder();
    recorder.startRecord();
    recorder.observer().handleEvent("event1");
    recorder.observer().handleEvent("event2");
    recorder.observer().handleEvent("event3");
    assertEquals(recorder.isRecord(), true);
    assertEquals(recorder.eventsChain().length, 0);
    recorder.stopRecord();
    assertEquals(recorder.isRecord(), false);
    Event[] events = recorder.eventsChain();
    assertEquals(events[0].getEvent(), "event1");
    assertEquals(events[1].getEvent(), "event2");
    assertEquals(events[2].getEvent(), "event3");
    assertEquals(recorder.isEmpty(), false);
    recorder.clearEvents();
    assertEquals(recorder.isEmpty(), true);
  }

  @Test
  public void domainConfigurationTest() {
    Configuration.load(TemplatesTest.class);
    assertEquals(Configuration.containsProperty("test.resource"), true);
    String property = Configuration.property("test.resource");
    assertEquals(property, "testResource");
    Configuration.load("META-INF/custom.configuration.properties", TemplatesTest.class);
    property = Configuration.properties("custom.configuration").get("test.resource");
    assertEquals(property, "testResource");
  }
}
