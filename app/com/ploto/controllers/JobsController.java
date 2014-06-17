package com.ploto.controllers;

import com.ploto.services.*;
import com.ploto.util.PlotoContext;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
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
    List<Position> positions = jobSvc.fetchOpenPositionsByUser(currentUser);

    for(Position currentPos : positions) {

    }

    return ok(com.ploto.views.html.jobs.render(positions, activities));
  }
}
