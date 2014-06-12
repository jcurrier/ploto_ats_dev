package com.ploto.controllers;

import com.ploto.services.Activity;
import com.ploto.views.html.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;

/**
 * Created by jeff on 6/3/14.
 */
public class ApplicantsController extends Controller {

  @Security.Authenticated(Secured.class)
  public static Result index() {
    ArrayList<Activity> activities = new ArrayList<Activity>();
    activities.add(new Activity("activity 1", "user", "Sunday 7:00PM"));
    activities.add(new Activity("activity 2", "user 2", "Sunday 9:00PM"));
    session().put("app_ctx", "applicants");
    //return redirect("/");
    return ok(com.ploto.views.html.applicants.render(activities));
  }
}
