package com.ploto.services.store;

import com.ploto.services.Candidate;
import com.ploto.services.Position;

import java.util.Date;
import java.util.List;

/**
 * Created by jeff on 5/18/14.
 */
public interface JobServiceStore {

  public Position createJob(String customerId, String title, String description, String location, String hiringMgrid,
                            String recruiterId) throws StoreException;

  public void removeJob(String customerId, String id) throws StoreException;

  public void applyForJob(String customerId, String positionId, Candidate applicant) throws StoreException;

  public void withdrawApplication(String customerId, String positionId, Candidate applicant) throws StoreException;

  public List<Position> fetchOpenPositions(String customerId) throws StoreException;

  public List<Position> fetchOpenPositionsByUser(String customerId, String userId) throws StoreException;
}
