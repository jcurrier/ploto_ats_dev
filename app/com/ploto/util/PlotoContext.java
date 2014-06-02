package com.ploto.util;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.ploto.services.JobService;
import com.ploto.services.UserService;
import com.ploto.services.store.*;

/**
 * Created by jeff on 5/18/14.
 */
public class PlotoContext {
  private static Injector mInjector = null;

  static {
    createProdBindings();
  }

  public static Injector getInjector() {
    return mInjector;
  }

  public static void createTestBindings() {
    mInjector = Guice.createInjector(new AbstractModule() {

      @Override
      protected void configure() {
        bind(JobServiceStore.class).to(JobServiceTestStore.class);
        bind(JobService.class);
        bind(UserServiceStore.class).to(UserServiceSqlStore.class);
        bind(UserService.class);
        bind(CandidateServiceStore.class).to(CandidateServiceSqlStore.class);
        bind(UserService.class);
      }
    });
  }

  public static void createProdBindings() {
    mInjector = Guice.createInjector(new AbstractModule() {

      @Override
      protected void configure() {
        bind(JobServiceStore.class).to(JobServiceSqlStore.class);
        bind(JobService.class);
        bind(UserServiceStore.class).to(UserServiceSqlStore.class);
        bind(UserService.class);
        bind(CandidateServiceStore.class).to(CandidateServiceSqlStore.class);
        bind(UserService.class);
      }
    });
  }
}
