package com.ploto.services;

import com.ploto.util.PhoneNumber;

import java.util.Date;

/**
 * Created by jeff on 5/18/14.
 */
public class Candidate {
  private String mCustomerId;
  private String mId;
  private String mFirstName;
  private String mLastName;
  private String mEmail;
  private String mPrimaryPhone;
  private String mSecondaryPhone;
  private boolean mIsActive = false;
  private String mResumeUrl;
  private Date mLastUpdated;

  public String getFirstName() {
    return mFirstName;
  }

  public String getLastName() {
    return mLastName;
  }

  public String getEmail() {
    return mEmail;
  }

  public String getPrimaryPhone() {
    return mPrimaryPhone;
  }

  public String getSecondaryPhone() {
    return mSecondaryPhone;
  }

  public Candidate(String customerId, String email, String firstName, String lastName, String primaryPhone,
                   String secondaryPhone, String resumeUrl, boolean isActive, Date lastUpdated) {
    mFirstName = firstName;
    mLastName = lastName;
    mEmail = email;
    mResumeUrl = resumeUrl;
    mPrimaryPhone = primaryPhone;
    mSecondaryPhone = secondaryPhone;
    mIsActive = isActive;
    mLastUpdated = lastUpdated;
  }

}
