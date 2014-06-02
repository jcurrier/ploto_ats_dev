package com.ploto.services.store;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.Candidate;
import com.ploto.services.Position;

import java.sql.*;

/**
 * Created by jeff on 6/1/14.
 */
@Singleton
public class CandidateServiceSqlStore extends BaseSqlStore implements CandidateServiceStore {

  private final String CREATE_CANDIDATE =
      "INSERT INTO applicant (customer_id, id, first_name, last_name, primary_phone, secondary_phone, resume_url) " +
          "VALUES(?, ?, ?, ?, ?, ?, ?)";

  private final String REMOVE_CANDIDATE = "DELETE FROM applicant WHERE customer_id = ? AND id = ?";

  private final String FETCH_CANDIDATE = "SELECT * FROM applicant WHERE customer_id = ? AND id = ? LIMIT 1";

  @Inject
  private CandidateServiceSqlStore() {

  }

  @Override
  public Candidate createCandidate(String customerId, String emailAddress, String firstName, String lastName,
                                   String primaryPhone, String secondaryPhone, String cvUrl) throws StoreException {

    Connection dbConn = null;
    try {
      dbConn = getConnection();
      PreparedStatement ps = dbConn.prepareStatement(CREATE_CANDIDATE);

      ps.setString(1, customerId);
      ps.setString(2, emailAddress);
      ps.setString(3, firstName);
      ps.setString(4, lastName);

      if(primaryPhone == null) {
        ps.setNull(5, Types.VARCHAR);
      } else {
        ps.setString(5, primaryPhone);
      }

      if(secondaryPhone == null) {
        ps.setNull(6, Types.VARCHAR);
      } else {
        ps.setString(6, secondaryPhone);
      }

      ps.setString(7, cvUrl);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Failed to create candidate");
      }

    } catch (Exception ex) {
      throw new StoreException("Unable to create candidate", ex);

    } finally {
    }

    return fetchCandidate(customerId, emailAddress);

  }

  @Override
  public void removeCandidate(String customerId, String candidateId) throws StoreException {
    Connection dbConn = null;
    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(REMOVE_CANDIDATE);

      ps.setString(1, customerId);
      ps.setString(2, candidateId);

      int rowCount = ps.executeUpdate();
      if (rowCount != 1) {
        throw new StoreException("Candidate not found");
      }

    } catch (SQLException ex) {
      throw new StoreException("Unable to remove Candidate", ex);
    } finally {
    }

  }

  private Candidate fetchCandidate(String customerId, String candidateId) throws StoreException{
    Candidate candidate = null;
    Connection dbConn = null;

    Preconditions.checkArgument(customerId != null && customerId.length() > 0, "Invalid customer id passed");
    Preconditions.checkArgument(candidateId != null && candidateId.length() > 0, "Invalid candidate id passed");

    try {
      dbConn = getConnection();

      PreparedStatement ps = dbConn.prepareStatement(FETCH_CANDIDATE);
      ps.setString(1, customerId);
      ps.setString(2, candidateId);

      ResultSet results = ps.executeQuery();
      results.first();

      candidate = new Candidate(results.getString("customer_id"), results.getString("id"),
          results.getString("first_name"), results.getString("last_name"), results.getString("primary_phone"),
          results.getString("secondary_phone"), results.getString("resume_url"), results.getBoolean("is_active"),
          results.getDate("last_updated"));

    } catch (Exception ex) {
      throw new StoreException("Unable to retrieve candidate", ex);
    } finally {
    }

    return candidate;
  }
}
