//
package com.edevapps.tests;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotSame;
import static junit.framework.TestCase.assertTrue;

import com.edevapps.tests.environment.AbstractClass;
import com.edevapps.tests.environment.TestClass;
import com.edevapps.tests.environment.TestInteface;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.biztemplates.ReflectionPropertyEditor;
import net.biztemplates.utils.ReflectionUtil;
import net.biztemplates.utils.ReverseIterator;
import net.biztemplates.utils.SerializationUtil;
import net.biztemplates.utils.StringUtil;
import org.junit.Test;

public class UtilsTest {

  @Test
  public void reflectionPropertyEditorTest() {
    TestClass testClass = new TestClass("Y");
    ReflectionPropertyEditor stringProperty =
        new ReflectionPropertyEditor(testClass, String.class, "stringProperty");

    assertEquals("Y", stringProperty.getValue());

    stringProperty.setValue("X");
    assertEquals("X", testClass.getStringProperty());
  }

  @Test
  public void reflectionUtilTest() {
    Method[] methods = ReflectionUtil.methodsByPrefix(TestClass.class, "get");
    assertFalse(methods.length <= 0);
    if (methods.length > 0) {
      assertEquals(methods[0].getName(), "getStringProperty");
    }

    Set<Object> set = new HashSet<>(Arrays.asList(ReflectionUtil.interfaces(TestClass.class)));
    assertFalse(set.size() <= 0);
    if (set.size() > 0) {
      assertTrue(set.contains(Serializable.class));
      assertTrue(set.contains(TestInteface.class));
    }

    set.clear();
    set.addAll(Arrays.asList(ReflectionUtil.parents(TestClass.class)));
    assertFalse(set.size() <= 0);
    if (set.size() > 0) {
      assertTrue(set.contains(AbstractClass.class));
    }

    assertEquals(
        ReflectionUtil.addPrefix("on", "click"),
        "onClick");

    assertEquals(ReflectionUtil.buildGetter("propertyName"), "getPropertyName");
    assertEquals(ReflectionUtil.buildSetter("propertyName"), "setPropertyName");
    assertEquals(ReflectionUtil.removePrefix(3, "getPropertyName"), "propertyName");
  }

  @Test
  public void reverseIteratorTest() {
    List<Integer> list = new ArrayList<>(0);
    list.add(0);
    list.add(1);
    list.add(2);
    ReverseIterator<Integer> ri = new ReverseIterator<>(list);
    Iterator<Integer> i = ri.iterator();
    assertEquals((int) i.next(), 2);
    assertEquals((int) i.next(), 1);
    assertEquals((int) i.next(), 0);
  }

  @Test
  public void serializationUtilTest() {
    TestClass o1 = new TestClass("Test!");
    TestClass o2 = SerializationUtil.duplicate(o1);
    assertNotSame(o1, o2);
    assertEquals(o1.getStringProperty(), o2.getStringProperty());
  }

  @Test
  public void stringUtilTest() {
    String str = StringUtil.replace("aaaa1234bbbb", "1234", "TEST");
    assertEquals("aaaaTESTbbbb", str);
  }

}
