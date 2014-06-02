package com.ploto.services.test;

import com.ploto.services.*;
import com.ploto.util.PlotoContext;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeff on 5/18/14.
 */

public class JobServiceTest {
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
  public void createAndRemoveJobTest() {
    String customerId = "test-" + System.currentTimeMillis();
    String title = "position title - " + System.currentTimeMillis();
    String desc = "job description";
    String location = "Seattle, WA";
    String hiringMgrId = "mgr_123";
    String recruiterId = "recruiter_123";
    Date posted = new Date(System.currentTimeMillis());
    Date lastUpdate = new Date(System.currentTimeMillis());

    UserService userSvc = mCtx.getInjector().getInstance(UserService.class);
    JobService jobSvc = mCtx.getInjector().getInstance(JobService.class);

    User mgr = userSvc.createUser(customerId, "mgr@myco.com", "password");
    User recruiter = userSvc.createUser(customerId, "recruiter@myco.com", "password");

    // First, create a job.
    try {
      Position p = jobSvc.createPosition(new Position(customerId, title, desc, location, mgr.getEmail(),
          recruiter.getEmail()));
      Assert.assertEquals(p.getCustomerId(), customerId);
      Assert.assertEquals(p.getTitle(), title);
      Assert.assertEquals(p.getDescription(), desc);
      Assert.assertEquals(p.getLocation(), location);
      Assert.assertEquals(p.getHiringMgrId(), mgr.getEmail());
      Assert.assertEquals(p.getRecruiterId(), recruiter.getEmail());

      // Next, simply remove it.
      jobSvc.removePosition(p);

      userSvc.removeUser(mgr.getEmail());
      userSvc.removeUser(recruiter.getEmail());

    } catch (ServiceException ex) {
      Assert.fail(ex.toString());
    }

    // Now, try to create some invalid jobs.
    try {
      jobSvc.createPosition(null);
      Assert.fail("didn't get expected exception on null paraneter");
    } catch (Exception ex) {
    }

    Position testPos = new Position();
    try {
      testPos.setId("123");
      jobSvc.createPosition(testPos);
      Assert.fail("didn't get expected exception on null title");
    } catch (Exception ex) {
    }

    try {
      testPos.setTitle("Title");
      jobSvc.createPosition(testPos);
      Assert.fail("didn't get expected exception on null description");
    } catch (Exception ex) {
    }

    try {
      testPos.setDescription("Desc");
      jobSvc.createPosition(testPos);
      Assert.fail("didn't get expected exception on null location");
    } catch (Exception ex) {
    }
  }

  @AfterClass
  public static void tearDown() {

    if (mApp != null) {
      Helpers.stop(mApp);
    }
  }
}
