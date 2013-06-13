package com.tacs13G6;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GaeTestServletTest {

  private GaeTestServlet gaeTestServlet;

  private final LocalServiceTestHelper helper =
      new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig())
          .setEnvIsLoggedIn(true)
          .setEnvAuthDomain("localhost")
          .setEnvEmail("test@localhost");

  @Before
  public void setupGaeTestServlet() {
    helper.setUp();
    gaeTestServlet = new GaeTestServlet();
  }

  @After
  public void tearDownHelper() {
    helper.tearDown();
  }

  @Test
  public void testDoGet() throws IOException, EntityNotFoundException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    
    StringWriter stringWriter = new StringWriter();
    when(response.getWriter()).thenReturn(new PrintWriter(stringWriter));
    gaeTestServlet.doGet(request, response);
    
    String resultText = stringWriter.toString();
    assertTrue("Feed are being save", resultText.contains("saving test user feeds..."));
    assertTrue("Db's query are being execute.",resultText.contains("getting user feeds..."));
    assertTrue("Feed are being retrieve",resultText.contains("Empty: No user feeds...") || resultText.contains("martin"));    
  }
}
