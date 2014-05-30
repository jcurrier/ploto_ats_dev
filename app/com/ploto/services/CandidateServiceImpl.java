package com.ploto.services;

/**
 * Created by jeff on 5/18/14.
 */
public class CandidateServiceImpl implements CandidateService {
  private static CandidateServiceImpl mInstance = null;

  private CandidateServiceImpl() {

  }

  public static CandidateServiceImpl Instance() {

    // BUGBUG: Rip this out and replace with dependency injection later.
    if (mInstance == null) {
      mInstance = new CandidateServiceImpl();
    }

    return mInstance;
  }

  public void CreateCandidate(Candidate newCandidate) {

  }

  public void RemoveCandidate(Candidate candidateToRemove) {

  }
}
