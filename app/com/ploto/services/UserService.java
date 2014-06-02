package com.ploto.services;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.store.StoreException;
import com.ploto.services.store.UserServiceStore;
import com.ploto.util.PlotoContext;

/**
 * Created by jeff on 5/24/14.
 */
@Singleton
public class UserService {
  @Inject
  private UserServiceStore mUserServiceStore;

  @Inject
  private UserService() {
    mUserServiceStore = PlotoContext.getInjector().getInstance(UserServiceStore.class);
  }

  public User createUser(String customerId, String email, String password) {
    Preconditions.checkNotNull(customerId);
    Preconditions.checkNotNull(email);
    Preconditions.checkNotNull(password);
    Preconditions.checkArgument(customerId.length() > 0, "Invalid customer id");
    Preconditions.checkArgument(email.length() > 0 && email.contains("@"), "Invalid email address");
    Preconditions.checkArgument(password.length() > 0, "Invalid password");

    User newUser = null;

    try {
      newUser = mUserServiceStore.createUser(customerId, email, password);
    } catch (StoreException ex) {

    }

    return newUser;
  }

  public void removeUser(String customerId, String email) {
    Preconditions.checkNotNull(email);
    Preconditions.checkArgument(email.length() > 0 && email.contains("@"), "Invalid email address");
    Preconditions.checkNotNull(customerId);
    Preconditions.checkArgument(customerId.length() > 0, "Invalid customer id");

    try {
      mUserServiceStore.removeUser(customerId, email);
    } catch (StoreException ex) {

    }

  }

  public boolean authenicateUser(String email, String password) {
    Preconditions.checkNotNull(email);
    Preconditions.checkArgument(email.length() > 0 && email.contains("@"), "Invalid email address");

    boolean verdict = false;
    try {
      verdict = mUserServiceStore.authenticateUser(email, password);
    } catch (StoreException ex) {
    }

    return verdict;
  }

  public User fetchUser(String customerId, String email) {
    Preconditions.checkNotNull(email);
    Preconditions.checkArgument(email.length() > 0 && email.contains("@"), "Invalid email address");
    Preconditions.checkNotNull(customerId);
    Preconditions.checkArgument(customerId.length() > 0, "Invalid customer id");


    User locatedUser = null;
    try {
      locatedUser = mUserServiceStore.fetchUser(customerId, email);
    } catch (StoreException ex) {

    }

    return locatedUser;
  }

  public boolean changePassword(String customerId, String email, String oldPassword, String newPassword) {
    return false;
  }
}
