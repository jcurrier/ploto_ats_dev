package com.ploto.services;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.store.JobServiceStore;
import com.ploto.services.store.StoreException;
import com.ploto.util.PlotoContext;

/**
 * Created by jeff on 5/17/14.
 */
@Singleton
public class JobService {

  @Inject
  private JobServiceStore mJobServiceStore;

  @Inject
  private JobService() {
    mJobServiceStore = PlotoContext.getInjector().getInstance(JobServiceStore.class);
  }


  public Position createPosition(Position newPosition) throws ServiceException {
    // Validate our input parameters.
    Preconditions.checkNotNull(newPosition);
    Preconditions.checkState(newPosition.getCustomerId() != null && newPosition.getCustomerId().length() > 1,
        "Invalid Job Title");
    Preconditions.checkState(newPosition.getTitle() != null && newPosition.getTitle().length() > 1,
            "Invalid Job Title");
    Preconditions.checkState(newPosition.getDescription() != null && newPosition.getDescription().length() > 1,
            "Invalid Job Description");
    Preconditions.checkState(newPosition.getLocation() != null && newPosition.getLocation().length() > 1,
            "Invalid Job Location");
    Preconditions.checkState(newPosition.getHiringMgrId() != null && newPosition.getHiringMgrId().length() > 1,
        "Invalid Job Location");

    Position pos = null;

    UserService userSvc = PlotoContext.getInjector().getInstance(UserService.class);

    if(userSvc.fetchUser(newPosition.getHiringMgrId()) == null) {
      throw new ServiceException("Hiring manager doesn't exist");
    }

    // Since assigning a recruiter is optional at this point only check for this if we have to.
    if(newPosition.getRecruiterId() != null) {
      if(userSvc.fetchUser(newPosition.getRecruiterId()) == null) {
        throw new ServiceException("Recruiter doesn't exist");
      }
    }

    try {
      pos = mJobServiceStore.createJob(newPosition.getCustomerId(), newPosition.getTitle(),
          newPosition.getDescription(), newPosition.getLocation(), newPosition.getHiringMgrId(),
          newPosition.getRecruiterId());
    } catch (StoreException ex) {

    }

    return pos;
  }

  public void removePosition(Position positionToRemove) {
    // Validate our input parameters.
    Preconditions.checkNotNull(positionToRemove);
    Preconditions.checkState(positionToRemove.getId() != null && positionToRemove.getId().length() > 1,
            "Invalid Job ID");

    try {
      mJobServiceStore.removeJob(positionToRemove.getCustomerId(), positionToRemove.getId());
    } catch (StoreException ex) {

    }
  }

  public ImmutableList<Position> fetchOpenPositions() {

    return null;
  }
};

