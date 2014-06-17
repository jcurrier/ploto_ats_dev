package com.ploto.services.store;

import com.ploto.services.Position;
import com.ploto.services.User;

import java.util.List;

/**
 * Created by jeff on 5/23/14.
 */
public interface UserServiceStore {

  public User createUser(String customerId, String userId, String password) throws StoreException;

  public void removeUser(String customerId, String userId) throws StoreException;

  public boolean authenticateUser(String userId, String password) throws StoreException;

  public User fetchUser(String customerId, String userId) throws StoreException;

  public User fetchUser(String userId) throws StoreException;

  public boolean changePassword(String customerId, String userId, String oldPassword,
                                String newPassword) throws StoreException;

}
