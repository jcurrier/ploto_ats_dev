package com.ploto.services.test;

import com.ploto.services.Candidate;
import com.ploto.services.CandidateService;
import com.ploto.util.PlotoContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeff on 5/18/14.
 */
public class CandidateServiceTest {

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
  public void createAndRemoveCandidateTest() {
    String customerId = "test-" + System.currentTimeMillis();
    String firstName = "Jeff";
    String lastName = "Currier";
    String email = "jeff"+System.currentTimeMillis()+"@jeff.com";
    String priPhone = "425-503-3179";
    String secPhone = "425-605-0921";
    String resumeUrl = "http://blobstore.co/myresume.pdf";

    CandidateService canSvc = mCtx.getInjector().getInstance(CandidateService.class);

    // First, create a candidate.
    Candidate can = CreateCandidateObject(customerId, email, firstName, lastName, priPhone, secPhone, resumeUrl);
    can = canSvc.createCandidate(can);
    Assert.assertEquals(can.getCustomerId(), customerId);
    Assert.assertEquals(can.getEmail(), email);
    Assert.assertEquals(can.getFirstName(), firstName);
    Assert.assertEquals(can.getLastName(), lastName);
    Assert.assertEquals(can.getPrimaryPhone(), priPhone);
    Assert.assertEquals(can.getSecondaryPhone(), secPhone);
    Assert.assertEquals(can.getResumeUrl(), resumeUrl);

    // Next, simply remove it.
    canSvc.removeCandidate(can);
  }

  private Candidate CreateCandidateObject(String customerId, String emailAddress, String firstName, String lastName,
                                   String primaryPhone, String secondaryPhone, String cvUrl) {
    return new Candidate(customerId, emailAddress, firstName, lastName, primaryPhone, secondaryPhone, cvUrl);
  }
}
