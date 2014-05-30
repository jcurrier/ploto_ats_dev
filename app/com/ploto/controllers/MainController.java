package com.ploto.controllers;

import com.ploto.services.Activity;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;

/**
 * Created by jeff on 5/26/14.
 */
public class MainController extends Controller {
    public static Result main() {
        ArrayList<Activity> activities = new ArrayList<Activity>();
        activities.add(new Activity("activity 1", "user", "Sunday 7:00PM"));
        activities.add(new Activity("activity 2", "user 2", "Sunday 9:00PM"));
        return ok(com.ploto.views.html.main.render("Your new application is ready.", activities));
    }

    public static Result mainframe() {
        return ok(com.ploto.views.html.mainframe.render("Your new application is ready."));
    }
}
