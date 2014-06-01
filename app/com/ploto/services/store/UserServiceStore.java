package com.ploto.services.store;

import com.ploto.services.User;

/**
 * Created by jeff on 5/23/14.
 */
public interface UserServiceStore {

  public User createUser(String customerId, String email, String password) throws StoreException;

  public void removeUser(String email) throws StoreException;

  public boolean authenicateUser(String email, String password) throws StoreException;

  public User fetchUser(String email) throws StoreException;

  public boolean changePassword(String email, String oldPassword, String newPassword) throws StoreException;
}
