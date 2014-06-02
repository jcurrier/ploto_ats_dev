package com.ploto.services.test;

import com.ploto.services.User;
import com.ploto.services.UserService;
import com.ploto.util.PlotoContext;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeff on 5/24/14.
 */
public class UserServiceTest {
  private static PlotoContext mCtx = new PlotoContext();
  private static FakeApplication mApp = null;

  @BeforeClass
  public static void setup() {

    PlotoContext.createProdBindings();
    Map<String, String> settings = new HashMap<String, String>();
    settings.put("db.plotodb.driver", "com.mysql.jdbc.Driver");
    settings.put("db.plotodb.url", "jdbc:mysql://localhost/plotodb");
    settings.put("db.plotodb.user", "ploto");
    settings.put("db.plotodb.password", "dev");
    mApp = Helpers.fakeApplication(settings);
    Helpers.start(mApp);
  }

  @Test
  public void createAndRemoveUserTest() {
    String customerId = "test_co";
    String email = "joe" + System.currentTimeMillis() + "@ploto.co";
    String pw = "super_secure";

    UserService userSvc = mCtx.getInjector().getInstance(UserService.class);

    User newUser = userSvc.createUser(customerId, email, pw);
    Assert.assertNotNull(newUser);
    Assert.assertEquals(newUser.getEmail(), email);
    Assert.assertEquals(newUser.getPassword(), pw);
    Assert.assertTrue(newUser.getIsActive());

    User foundUser = userSvc.fetchUser(customerId, email);
    Assert.assertNotNull(foundUser);

    userSvc.removeUser(customerId, email);

    foundUser = userSvc.fetchUser(customerId, email);
    Assert.assertNull(foundUser);
  }

  @Test
  public void authenticateUserTest() {
    String customerId = "test_co";
    String email = "joe" + System.currentTimeMillis() + "@ploto.co";
    String pw = "super_secure";

    UserService userSvc = mCtx.getInjector().getInstance(UserService.class);

    User newUser = userSvc.createUser(customerId, email, pw);
    Assert.assertNotNull(newUser);
    Assert.assertEquals(newUser.getEmail(), email);
    Assert.assertEquals(newUser.getPassword(), pw);
    Assert.assertTrue(newUser.getIsActive());

    boolean verdict = userSvc.authenicateUser(email, pw);
    Assert.assertTrue("Unable to auth user", verdict);

    verdict = userSvc.authenicateUser(email, "bogus password");
    Assert.assertFalse("Was able to auth user and shouldn't have been able to", verdict);

  }

  @AfterClass
  public static void tearDown() {

    if (mApp != null) {
      Helpers.stop(mApp);
    }
  }
}
