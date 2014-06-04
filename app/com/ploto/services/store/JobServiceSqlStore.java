package com.ploto.services.store;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.Candidate;
import com.ploto.services.Position;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 5/18/14.
 */
@Singleton
public class JobServiceSqlStore extends BaseSqlStore implements JobServiceStore {

  private final String CREATE_JOB =
      "INSERT INTO position (customer_id, id, title, description, location, hiring_mgr, recruiter, status) " + "" +
          "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

  private final String REMOVE_JOB = "DELETE FROM position WHERE customer_id = ? AND id = ?";

  private final String FETCH_JOB = "SELECT * FROM position WHERE customer_id = ? AND id = ? LIMIT 1";

  private final String FETCH_OPEN_JOBS = "SELECT * FROM position WHERE customer_id = ? ORDER BY last_updated";

  private final String FETCH_OPEN_JOBS_BY_USER = "SELECT * FROM position WHERE customer_id = ? AND " +
                                                 " (hiring_mgr = ? OR recruiter = ? ORDER BY last_updated";

  @Inject
  private JobServiceSqlStore() {

  }

  @Override
  public Position createJob(String customerId, String title, String description, String location, String hiringMgrid,
                            String recruiterId) throws StoreException {
    String positionId = generateUniqueId();

    Connection dbConn = null;
    try {
      dbConn = getConnection();
      PreparedStatement ps = dbConn.prepareStatement(CREATE_JOB);

      ps.setString(1, customerId);
      ps.setString(2, positionId);
      ps.setString(3, title);
      ps.setString(4, description);
      ps.setString(5, location);
      ps.setString(6, hiringMgrid);

      if(recruiterId != null) {
        ps.setString(7, recruiterId);
      } else {
        ps.setNull(7, Types.VARCHAR);
      }
      ps.setShort(8, Position.OPEN_STATUS);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Failed to create position");
      }

      ps.close();
    } catch (Exception ex) {
      throw new StoreException("Unable to create position", ex);

    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }

    return fetchPosition(customerId, positionId);
  }

  @Override
  public void removeJob(String customerId, String positionId) throws StoreException {
    Connection dbConn = null;
    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(REMOVE_JOB);

      ps.setString(1, customerId);
      ps.setString(2, positionId);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Position not found");
      }

      ps.close();
    } catch (SQLException ex) {
      throw new StoreException("Unable to remove position", ex);
    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }
  }

  @Override
  public List<Position> fetchOpenPositions(String customerId) throws StoreException {
    Connection dbConn = null;

    ArrayList<Position> openPos = new ArrayList<Position>();
    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(FETCH_OPEN_JOBS);

      ps.setString(1, customerId);

      ResultSet results = ps.executeQuery();
      while(results.next()) {
        openPos.add(new Position(results.getString("customer_id"), results.getString("id"), results.getString("title"),
            results.getString("description"), results.getString("location"), results.getString("hiring_mgr"),
            results.getString("recruiter"), results.getShort("status"), results.getTimestamp("posted"),
            results.getTimestamp("last_updated")));
      }

      ps.close();
      results.close();
    } catch (SQLException ex) {
      throw new StoreException("Unable to fetch positions", ex);
    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }

    return openPos;
  }

  @Override
  public List<Position> fetchOpenPositionsByUser(String customerId, String userId) throws StoreException {
    Connection dbConn = null;

    ArrayList<Position> openPos = new ArrayList<Position>();
    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(FETCH_OPEN_JOBS_BY_USER);

      ps.setString(1, customerId);
      ps.setString(2, userId);

      ResultSet results = ps.executeQuery();
      while(results.next()) {
        openPos.add(new Position(results.getString("customer_id"), results.getString("id"), results.getString("title"),
            results.getString("description"), results.getString("location"), results.getString("hiring_mgr"),
            results.getString("recruiter"), results.getShort("status"), results.getTimestamp("posted"),
            results.getTimestamp("last_updated")));
      }

      ps.close();
      results.close();
    } catch (SQLException ex) {
      throw new StoreException("Unable to fetch positions", ex);
    } finally {
      if (dbConn != null) {
        try { dbConn.close(); } catch(SQLException ex) {}
      }
    }
    return openPos;
  }

  private Position fetchPosition(String customerId, String positionId) throws StoreException {
    Position pos = null;
    Connection dbConn = null;

    Preconditions.checkArgument(customerId != null && customerId.length() > 0, "Invalid customer id passed");
    Preconditions.checkArgument(positionId != null && positionId.length() > 0, "Invalid position id passed");

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(FETCH_JOB);
      ps.setString(1, customerId);
      ps.setString(2, positionId);

      ResultSet results = ps.executeQuery();
      results.first();

      pos = new Position(results.getString("customer_id"), results.getString("id"), results.getString("title"),
          results.getString("description"), results.getString("location"), results.getString("hiring_mgr"),
          results.getString("recruiter"), results.getShort("status"), results.getTimestamp("posted"),
          results.getTimestamp("last_updated"));

      ps.close();
      results.close();
    } catch (Exception ex) {
      throw new StoreException("Unable to retrieve position", ex);
    } finally {
      if(dbConn != null) {
        try { dbConn.close(); } catch (SQLException ex) { }
      }
    }

    return pos;
  }

  @Override
  public void applyForJob(String customerId, String positionId, Candidate applicant) throws StoreException {

  }

  @Override
  public void withdrawApplication(String customerId, String positionId, Candidate applicant) throws StoreException {

  }
}
