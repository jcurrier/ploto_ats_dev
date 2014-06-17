package com.ploto.controllers;

import com.ploto.services.*;
import com.ploto.util.PlotoContext;
import org.joda.time.DateTime;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jeff on 6/16/14.
 */
public class JobsController extends Controller {
  private static PlotoContext mCtx = new PlotoContext();

  @Security.Authenticated(Secured.class)
  public static Result index() {
    ArrayList<Activity> activities = new ArrayList<Activity>();
    activities.add(new Activity("activity 1", "user", "Sunday 7:00PM"));
    activities.add(new Activity("activity 2", "user 2", "Sunday 9:00PM"));
    session().put("app_ctx", "applicants");
    //return redirect("/");
    String userName = request().username();
    CandidateService canSvc = mCtx.getInjector().getInstance(CandidateService.class);
    JobService jobSvc = mCtx.getInjector().getInstance(JobService.class);
    UserService userSvc = mCtx.getInjector().getInstance(UserService.class);

    User currentUser = userSvc.fetchUser(userName);
    // List<Position> positions = jobSvc.fetchOpenPositionsByUser(currentUser);

    /*
    public Position(String customerId, String id, String jobTitle, String description, String location,
        String hiringMgrId, String recruiterId, short status, Date datePosted, java.util.Date lastUpdated) {
        */

    List<Position> positions = new ArrayList<Position>();
    positions.add(new Position("1", "1", "Android Sr. Software Engineer", "a description", "Seattle, WA", "jgarms@twitter.com",
        "mfoley@twitter.com", (short)1, DateTime.now().toDate(), DateTime.now().toDate()));
    positions.add(new Position("1", "2", "Receptionist - Seattle", "a description", "Seattle, WA", "bnordstorm@twitter.com",
        "mfoley@twitter.com", (short)1, DateTime.now().toDate(), DateTime.now().toDate()));
    positions.add(new Position("1", "3", "Software Engineer - Machine Learning/Relevance (Seattle)", "a description",
        "Seattle, WA", "tlarson@twitter.com", "mfoley@twitter.com", (short)1, DateTime.now().toDate(), DateTime.now().toDate()));
    positions.add(new Position("1", "4", "Software Engineer - Product (Seattle)", "a description",
        "Seattle, WA", "jcurrier@twitter.com", "mfoley@twitter.com", (short)1, DateTime.now().toDate(), DateTime.now().toDate()));
    positions.add(new Position("1", "5", "Sr. Software Engineer - Cards Platform (Seattle)", "a description",
        "Seattle, WA", "lliang@twitter.com", "mfoley@twitter.com", (short)1, DateTime.now().toDate(), DateTime.now().toDate()));
    positions.add(new Position("1", "6", "Sr. Software Engineer - Infrastructure Repair & Reporting Services", "a description",
        "Seattle, WA", "ccarson@twitter.com", "mfoley@twitter.com", (short)1, DateTime.now().toDate(), DateTime.now().toDate()));
    positions.add(new Position("1", "7", "Sr. Software Engineer - Web/UI", "a description",
        "Seattle, WA", "jcurrier@twitter.com", "mfoley@twitter.com", (short)1, DateTime.now().toDate(), DateTime.now().toDate()));
    for(Position currentPos : positions) {

    }

    return ok(com.ploto.views.html.jobs.render(positions, activities));
  }
}
