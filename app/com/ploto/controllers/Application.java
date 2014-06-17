package com.ploto.controllers;

import com.ploto.services.Activity;
import com.ploto.services.UserService;
import com.ploto.util.PlotoContext;
import com.ploto.views.html.*;

import play.data.Form;
import play.mvc.Content;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;

import static play.data.Form.form;

public class Application extends Controller {
  private static PlotoContext mCtx = new PlotoContext();

  public static Result login() {
    return ok(login.render(form(Login.class)));
  }

  @Security.Authenticated(Secured.class)
  public static Result index() {
    ArrayList<Activity> activities = new ArrayList<Activity>();
    activities.add(new Activity("activity 1", "user", "Sunday 7:00PM"));
    activities.add(new Activity("activity 2", "user 2", "Sunday 9:00PM"));
    Content tmp = com.ploto.views.html.dashboard.render(activities);

    String ctxVal = session().get("app_ctx");
    String s = tmp.body();
    String s2 = tmp.toString();
    return ok(com.ploto.views.html.dashboard.render(activities));
  }

  @Security.Authenticated(Secured.class)
  public static Result logout() {
    session().clear();
    flash("success", "You've been logged out");
    return redirect(
        com.ploto.controllers.routes.Application.login()
    );
  }

  public static Result authenticate() {
    System.out.println("In App::authenticate");
    Form<Login> loginForm = form(Login.class).bindFromRequest();
    loginForm = form(Login.class).bindFromRequest();
    System.out.println("user = " + loginForm.data().get("email") + " pw = " + loginForm.data().get("password"));

    if (loginForm.hasErrors()) {
      return badRequest(com.ploto.views.html.login.render(loginForm));
    } else {
      session("email", loginForm.get().email);
      return redirect(
          com.ploto.controllers.routes.Application.index()
      );
    }
  }

  public static class Login {
    private static PlotoContext mCtx = new PlotoContext();

    public String email;
    public String password;

    public String validate() {
      String verdict = null;

      try {
        UserService userSvc = mCtx.getInjector().getInstance(UserService.class);

        if(!userSvc.authenicateUser(email, password)) {
          verdict = "Invalid credentials";
        }
      } catch (Exception ex) {
        verdict = "internal error";
      }

      System.out.println("In Login::validate");
      return verdict;
    }
  }
}
