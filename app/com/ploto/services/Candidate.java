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

  public String getCustomerId() { return mCustomerId; }

  public void setCustomerId(String customerId) { this.mCustomerId = customerId; }

  public String getResumeUrl() { return mResumeUrl; }

  public void setResumeUrl(String resumeUrl) {this.mResumeUrl = resumeUrl; }

  public Date getLastUpdated() { return mLastUpdated; }

  public void setLastUpdated(Date lastUpdated) { this.mLastUpdated = lastUpdated; }

  public boolean getIsActive() { return mIsActive; }

  public void setIsActive(boolean isActive) { this.mIsActive = isActive; }

  public Candidate(String customerId, String email, String firstName, String lastName, String primaryPhone,
                   String secondaryPhone, String resumeUrl) {
    mCustomerId = customerId;
    mFirstName = firstName;
    mLastName = lastName;
    mEmail = email;
    mResumeUrl = resumeUrl;
    mPrimaryPhone = primaryPhone;
    mSecondaryPhone = secondaryPhone;
  }

  public Candidate(String customerId, String email, String firstName, String lastName, String primaryPhone,
                   String secondaryPhone, String resumeUrl, boolean isActive, Date lastUpdated) {
    mCustomerId = customerId;
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
