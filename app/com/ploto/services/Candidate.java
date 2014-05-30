package com.ploto.services;

import com.ploto.util.PhoneNumber;

/**
 * Created by jeff on 5/18/14.
 */
public class Candidate {
  private String mFirstName;
  private String mLastName;
  private String mEmail;
  private PhoneNumber mPrimaryPhone;
  private PhoneNumber mSecondaryPhone;

  public String getFirstName() {
    return mFirstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getEmail() {
    return mEmail;
  }

  public PhoneNumber getPrimaryPhone() {
    return mPrimaryPhone;
  }

  public PhoneNumber getSecondaryPhone() {
    return mSecondaryPhone;
  }

  public Candidate(String firstName, String lastName, String email, PhoneNumber primaryPhone,
                   PhoneNumber secondaryPhone) {
    mFirstName = firstName;
    mLastName = lastName;
    mEmail = email;
    mPrimaryPhone = primaryPhone;
    mSecondaryPhone = secondaryPhone;
  }

}
