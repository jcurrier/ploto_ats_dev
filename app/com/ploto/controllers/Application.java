package com.ploto.controllers;

import com.ploto.services.Activity;
import com.ploto.services.UserService;
import com.ploto.services.store.StoreException;
import com.ploto.settings.PlotoSettings;
import com.ploto.util.PlotoContext;
import play.*;
import play.data.*;
import play.data.Form;
import play.mvc.*;

import static play.data.Form.*;

import views.html.helper.*;
import com.ploto.views.html.*;

import java.util.ArrayList;

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
    return ok(main.render("Your new application is ready.", activities));
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
