package com.ploto.services.store;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by jeff on 5/23/14.
 */
@Singleton
public class UserServiceSqlStore extends BaseSqlStore implements UserServiceStore {

  private final String CREATE_USER =
          "INSERT INTO user (email, password) VALUES(?, ?)";

  private final String REMOVE_USER = "DELETE FROM user WHERE email = ?";

  private final String FETCH_USER = "SELECT * FROM user WHERE email = ? LIMIT 1";

  @Inject
  private UserServiceSqlStore() {

  }

  @Override
  public User createUser(String email, String password) throws StoreException {
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(CREATE_USER);

      ps.setString(1, email);
      ps.setString(2, password);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Failed to create user");
      }

    } catch (Exception ex) {
      throw new StoreException("Unable to create user", ex);

    } finally {
    }

    return fetchUser(email);
  }

  @Override
  public void removeUser(String email) throws StoreException {
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(REMOVE_USER);

      ps.setString(1, email);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Failed to remove user");
      }

    } catch (Exception ex) {
      throw new StoreException("Unable to remove user", ex);

    } finally {
    }
  }

  @Override
  public boolean authenicateUser(String email, String password) throws StoreException {
    return false;
  }

  @Override
  public User fetchUser(String email) throws StoreException {
    User user = null;
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(FETCH_USER);
      ps.setString(1, email);

      ResultSet results = ps.executeQuery();
      results.first();

      user = new User(results.getString("email"), results.getString("password"), results.getBoolean("is_active"),
              results.getTimestamp("last_updated"));

    } catch (Exception ex) {
      throw new StoreException("Unable to retrieve user", ex);
    } finally {
    }

    return user;
  }

  @Override
  public boolean changePassword(String email, String oldPassword, String newPassword) throws StoreException {
    return false;
  }
}
