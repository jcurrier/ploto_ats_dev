package com.ploto.services.store;

import com.ploto.services.Candidate;

/**
 * Created by jeff on 6/1/14.
 */
public interface CandidateServiceStore {

  public Candidate CreateCandidate(String customerId, String emailAddress, String firstName, String lastName,
                                   String primaryPhone, String secondaryPhone, String cvUrl) throws StoreException;

  public void RemoveCandidate(String customerId, String candidateId) throws StoreException;
}
