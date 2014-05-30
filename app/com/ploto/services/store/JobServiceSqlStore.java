package com.ploto.services.store;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jeff on 5/18/14.
 */
@Singleton
public class JobServiceSqlStore extends BaseSqlStore implements JobServiceStore {

  private final String CREATE_JOB =
          "INSERT INTO position (id, title, description, location, status) VALUES(?, ?, ?, ?, ?)";

  private final String REMOVE_JOB = "DELETE FROM position WHERE id = ?";

  private final String FETCH_JOB = "SELECT * FROM position WHERE id = ? LIMIT 1";

  @Inject
  private JobServiceSqlStore() {

  }

  @Override
  public Position storeJob(Position newPosition) throws StoreException {
    String positionId = generateUniqueId();

    Connection dbConn = null;
    try {
      dbConn = getConnection();

      newPosition.setId(positionId);
      PreparedStatement ps = dbConn.prepareStatement(CREATE_JOB);

      ps.setString(1, newPosition.getId());
      ps.setString(2, newPosition.getTitle());
      ps.setString(3, newPosition.getDescription());
      ps.setString(4, newPosition.getLocation());
      ps.setShort(5, newPosition.getStatus());

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Failed to create position");
      }

    } catch (Exception ex) {
      throw new StoreException("Unable to create position", ex);

    } finally {
    }

    return fetchPosition(positionId);
  }

  @Override
  public void removeJob(Position positionToRemove) throws StoreException {
    Connection dbConn = null;
    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(REMOVE_JOB);

      ps.setString(1, positionToRemove.getId());

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Position not found");
      }

    } catch (SQLException ex) {
      throw new StoreException("Unable to remove position", ex);
    } finally {
    }
  }

  @Override
  public ImmutableList<Position> fetchOpenPositions() throws StoreException {
    return null;
  }

  private Position fetchPosition(String id) throws StoreException {
    Position pos = null;
    Connection dbConn = null;

    Preconditions.checkArgument(id != null && id.length() > 0, "Invalid position id passed");

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(FETCH_JOB);
      ps.setString(1, id);

      ResultSet results = ps.executeQuery();
      results.first();

      pos = new Position(results.getString("id"), results.getString("title"), results.getString("description"),
              results.getString("location"), results.getShort("status"), results.getTimestamp("posted"),
              results.getTimestamp("last_updated"));

    } catch (Exception ex) {
      throw new StoreException("Unable to retrieve position", ex);
    } finally {
    }

    return pos;
  }
}
