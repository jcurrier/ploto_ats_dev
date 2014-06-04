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
import java.util.List;
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
    settings.put("db.plotodb.minConnectionsPerPartition", "10");
    settings.put("db.plotodb.maxConnectionsPerPartition", "15");
    settings.put("db.plotodb.connectionTimeout", "5 seconds");
    settings.put("db.plotodb.partitionCount", "3");
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

      userSvc.removeUser(customerId, mgr.getEmail());
      userSvc.removeUser(customerId, recruiter.getEmail());

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

  @Test
  public void fetchJobsTest() {
    String customerId = "test-" + System.currentTimeMillis();
    int positionCount = 3;
    Position createdPositions[] = new Position[positionCount];

    UserService userSvc = mCtx.getInjector().getInstance(UserService.class);
    JobService jobSvc = mCtx.getInjector().getInstance(JobService.class);

    User mgr = userSvc.createUser(customerId, "mgr@myco.com", "password");
    User recruiter = userSvc.createUser(customerId, "recruiter@myco.com", "password");

    for(int index=0;index<positionCount;index++) {
      String title = "position title - " + System.currentTimeMillis();
      String desc = "job description";
      String location = "Seattle, WA";

      // First, create a job.
      try {
        Position p = new Position(customerId, title, desc, location, mgr.getEmail(),
            recruiter.getEmail());
        createdPositions[index] = jobSvc.createPosition(p);
        Assert.assertNotNull("Got back null position object", createdPositions[index]);
        Assert.assertEquals(createdPositions[index].getCustomerId(), customerId);
        Assert.assertEquals(createdPositions[index].getTitle(), title);
        Assert.assertEquals(createdPositions[index].getDescription(), desc);
        Assert.assertEquals(createdPositions[index].getLocation(), location);
        Assert.assertEquals(createdPositions[index].getHiringMgrId(), mgr.getEmail());
        Assert.assertEquals(createdPositions[index].getRecruiterId(), recruiter.getEmail());

      } catch (ServiceException ex) {
        System.out.println("failure occurred");
        System.out.println("customer id = " + customerId);
        System.out.println("mgr id = "+ mgr.getEmail() + " recruiter = " + recruiter.getEmail());
        Assert.fail(ex.toString());
      }
    }

    try {
      List<Position> positions = jobSvc.fetchOpenPositions(customerId);

      Assert.assertEquals(positions.size(), positionCount);
      for(int index=0;index<positionCount;index++) {
        Assert.assertTrue(positions.contains(createdPositions[index]));
      }
    } catch (Exception ex) {

      Assert.fail(ex.toString());
    }

    // userSvc.removeUser(customerId, mgr.getEmail());
    // userSvc.removeUser(customerId, recruiter.getEmail());
  }

  @AfterClass
  public static void tearDown() {

    if (mApp != null) {
      Helpers.stop(mApp);
    }
  }
}
