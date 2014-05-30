package com.ploto.settings;

import com.ploto.util.PlotoContext;
import play.Application;
import play.GlobalSettings;

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
    (*/


  @Override
  public void onStart(Application application) {
    super.onStart(application);
    mCtx = new PlotoContext();
        /*
            mInjector = Guice.createInjector(new AbstractModule() {

                @Override
                protected void configure() {
                    bind(JobServiceStore.class).to(JobServiceSqlStore.class);
                    bind(JobService.class);

                    System.out.println("Injections bound.");
                }
            });
            */
  }
}
