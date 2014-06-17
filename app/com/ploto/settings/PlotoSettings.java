package com.ploto.settings;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ploto.services.JobService;
import com.ploto.services.store.JobServiceSqlStore;
import com.ploto.services.store.JobServiceStore;
import com.ploto.util.PlotoContext;
import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.mvc.Http;
import play.mvc.SimpleResult;

/**
 * Created by jeff on 5/18/14.
 */
public class PlotoSettings extends GlobalSettings {
  private PlotoContext mCtx = null;

  /*
  private static Injector mInjector = null;

  public static Injector getInjector() {
    return mInjector;
  }
  */

  @Override
  public void onStart(Application application) {
    super.onStart(application);
    /*
    mCtx = new PlotoContext();
    mCtx.createProdBindings();
    */
  }
}
