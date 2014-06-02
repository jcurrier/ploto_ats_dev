package com.ploto.services;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.ploto.services.store.CandidateServiceStore;
import com.ploto.services.store.StoreException;
import com.ploto.util.PlotoContext;

/**
 * Created by jeff on 5/18/14.
 */
public class CandidateService {

  @Inject
  private CandidateServiceStore mCandidateSvcStore;

  @Inject
  private CandidateService() {
    mCandidateSvcStore = PlotoContext.getInjector().getInstance(CandidateServiceStore.class);
  }

  public Candidate createCandidate(Candidate newCandidate) {
    Preconditions.checkNotNull(newCandidate);
    Preconditions.checkState(newCandidate.getCustomerId() != null && newCandidate.getCustomerId().length() > 1,
        "Invalid Customer Id");
    Preconditions.checkState(newCandidate.getEmail() != null && newCandidate.getEmail().length() > 1,
        "Invalid Candidate Email");
    Preconditions.checkState(newCandidate.getFirstName() != null && newCandidate.getFirstName().length() > 1,
        "Invalid Candidate First Name");
    Preconditions.checkState(newCandidate.getLastName() != null && newCandidate.getLastName().length() > 1,
        "Invalid Candidate Last Name");
    Preconditions.checkState(newCandidate.getResumeUrl() != null && newCandidate.getResumeUrl().length() > 1,
        "Invalid Candidate resume URL");

    Candidate can = null;

    try {
      can = mCandidateSvcStore.createCandidate(newCandidate.getCustomerId(), newCandidate.getEmail(),
          newCandidate.getFirstName(), newCandidate.getLastName(), newCandidate.getPrimaryPhone(),
          newCandidate.getSecondaryPhone(), newCandidate.getResumeUrl());

    } catch (StoreException ex) {

    }

    return can;
  }

  public void removeCandidate(Candidate candidateToRemove) {
    Preconditions.checkNotNull(candidateToRemove);
    Preconditions.checkState(candidateToRemove.getCustomerId() != null &&
            candidateToRemove.getCustomerId().length() > 1, "Invalid Customer Id");
    Preconditions.checkState(candidateToRemove.getEmail() != null && candidateToRemove.getEmail().length() > 1);

    try {
      mCandidateSvcStore.removeCandidate(candidateToRemove.getCustomerId(), candidateToRemove.getEmail());

    } catch (StoreException ex) {

    }
  }
}
