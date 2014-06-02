package com.ploto.services.store;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.Candidate;
import com.ploto.services.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeff on 5/18/14.
 */
@Singleton
public class JobServiceTestStore implements JobServiceStore {

  private Map<String, Position> mJobMap = null;

  @Inject
  private JobServiceTestStore() {
    System.out.println("In ctor for JobServiceTestStore");

    mJobMap = new HashMap<String, Position>();
  }

  @Override
  public Position createJob(String customerId, String title, String description, String location, String hiringMgrid,
                            String recruiterId) throws StoreException {
    return null;
  }

  @Override
  public void removeJob(String customerId, String id) throws StoreException {

  }

  @Override
  public void applyForJob(String customerId, String positionId, Candidate applicant) throws StoreException {

  }

  @Override
  public void withdrawApplication(String customerId, String positionId, Candidate applicant) throws StoreException {

  }

  @Override
  public ImmutableList<Position> fetchOpenPositions(String customerId) throws StoreException {
    return null;
  }
}
