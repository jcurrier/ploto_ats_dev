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
        String email = "joe"+System.currentTimeMillis()+"@ploto.co";
        String pw = "super_secure";

        UserService jobSvc = mCtx.getInjector().getInstance(UserService.class);

        User newUser = jobSvc.createUser(email, pw);
        Assert.assertNotNull(newUser);
        Assert.assertEquals(newUser.getEmail(), email);
        Assert.assertEquals(newUser.getPassword(), pw);
        Assert.assertTrue(newUser.getIsActive());

        User foundUser = jobSvc.fetchUser(email);
        Assert.assertNotNull(foundUser);

        jobSvc.removeUser(email);

        foundUser = jobSvc.fetchUser(email);
        Assert.assertNull(foundUser);

    }

    @Test
    public void authenticateUserTest() {

    }

    @AfterClass
    public static void tearDown() {

        if(mApp != null) {
            Helpers.stop(mApp);
        }
    }
}
