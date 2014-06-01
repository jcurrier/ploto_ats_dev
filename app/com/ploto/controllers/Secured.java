package com.ploto.controllers;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

/**
 * Created by jeff on 5/31/14.
 */
public class Secured extends Security.Authenticator{

    @Override
    public String getUsername(Http.Context context) {
        return context.session().get("email");
    }

    @Override
    public Result onUnauthorized(Http.Context context) {
        return redirect(com.ploto.controllers.routes.Application.login());
    }
}
