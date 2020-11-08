package com.fullstackjava;

import com.fullstackjava.client.DemoTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class DemoSuite extends GWTTestSuite {
  public static Test suite() {
    TestSuite suite = new TestSuite("Tests for Demo");
    suite.addTestSuite(DemoTest.class);
    return suite;
  }
}
