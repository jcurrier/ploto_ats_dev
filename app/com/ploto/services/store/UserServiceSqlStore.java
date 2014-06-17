package com.ploto.services.store;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.Position;
import com.ploto.services.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by jeff on 5/23/14.
 */
@Singleton
public class UserServiceSqlStore extends BaseSqlStore implements UserServiceStore {

  private final String CREATE_USER =
          "INSERT INTO user (customer_id, id, password) VALUES(?, ?, ?)";

  private final String REMOVE_USER = "DELETE FROM user WHERE customer_id = ? AND id = ?";

  private final String FETCH_USER = "SELECT * FROM user WHERE customer_id = ? AND id = ? LIMIT 1";

  private final String SIMPLE_FETCH_USER = "SELECT * FROM user WHERE id = ? LIMIT 1";

  @Inject
  private UserServiceSqlStore() {

  }

  @Override
  public User createUser(String customerId, String userId, String password) throws StoreException {
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(CREATE_USER);

      ps.setString(1, customerId);
      ps.setString(2, userId);
      ps.setString(3, password);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Failed to create user");
      }

      ps.close();
    } catch (Exception ex) {
      throw new StoreException("Unable to create user", ex);

    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }

    return fetchUser(customerId, userId);
  }

  @Override
  public void removeUser(String customerId, String userId) throws StoreException {
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(REMOVE_USER);

      ps.setString(1, customerId);
      ps.setString(2, userId);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Failed to remove user");
      }

      ps.close();
    } catch (Exception ex) {
      throw new StoreException("Unable to remove user", ex);

    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }
  }

  @Override
  public boolean authenticateUser(String userId, String password) throws StoreException {
    boolean verdict = false;

    User user = null;
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(SIMPLE_FETCH_USER);
      ps.setString(1, userId);

      ResultSet results = ps.executeQuery();
      results.first();

      if(results.getString("id").equals(userId) &&
          results.getString("password").equals(password) &&
          results.getBoolean("is_active") == true) {
        verdict = true;
      }

      ps.close();
      results.close();
    } catch (Exception ex) {
      throw new StoreException("Unable to retrieve user", ex);
    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }

    return verdict;
  }

  @Override
  public User fetchUser(String customerId, String userId) throws StoreException {
    User user = null;
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(FETCH_USER);
      ps.setString(1, customerId);
      ps.setString(2, userId);

      ResultSet results = ps.executeQuery();
      results.first();

      user = new User(results.getString("customer_id"), results.getString("id"), results.getString("password"),
              results.getBoolean("is_active"), results.getTimestamp("last_updated"));

      ps.close();
      results.close();
    } catch (Exception ex) {
      throw new StoreException("Unable to retrieve user", ex);
    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }

    return user;
  }

  @Override
  public User fetchUser(String userId) throws StoreException {
    User user = null;
    Connection dbConn = null;

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(SIMPLE_FETCH_USER);
      ps.setString(1, userId);
      ResultSet results = ps.executeQuery();
      results.first();

      user = new User(results.getString("customer_id"), results.getString("id"), results.getString("password"),
          results.getBoolean("is_active"), results.getTimestamp("last_updated"));

      ps.close();
      results.close();
    } catch (Exception ex) {
      throw new StoreException("Unable to retrieve user", ex);
    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }

    return user;
  }

  @Override
  public boolean changePassword(String customerId, String userid, String oldPassword,
                                String newPassword) throws StoreException {
    return false;
  }

}
