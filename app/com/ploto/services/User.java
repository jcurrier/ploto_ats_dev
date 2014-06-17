package com.ploto.services;

import com.google.common.base.Preconditions;

import java.util.Date;

/**
 * Created by jeff on 5/23/14.
 */
public class User {
  private String mCustomerId = null;
  private String mEmail = null;
  private String mPassword = null;
  private Boolean mIsActive = false;
  private Date mLastUpdated = null;

  public String getCustomerId() { return mCustomerId; }

  public void setCustomerId(String customerId) {
    this.mCustomerId = customerId;
  }

  public String getEmail() {
    return mEmail;
  }

  public void setEmail(String email) {
    Preconditions.checkArgument(email != null && email.length() > 0, "Invalid email");
    Preconditions.checkArgument(email.contains("@"));

    this.mEmail = email;
  }

  public String getPassword() {
    return mPassword;
  }

  public void setPassword(String password) {
    Preconditions.checkArgument(password != null && password.length() > 0, "Invalid password");
    this.mPassword = password;
  }

  public Boolean getIsActive() {
    return mIsActive;
  }

  public void setIsActive(Boolean verdict) {
    this.mIsActive = verdict;
  }

  public Date getLastUpdated() {
    return mLastUpdated;
  }

  public void setLastUpdated(Date lastUpdated) {
    this.mLastUpdated = lastUpdated;
  }

  public User(String customerId, String email, String password, Boolean isActive, Date lastUpdated) {
    this.mCustomerId = customerId;
    this.mEmail = email;
    this.mPassword = password;
    this.mIsActive = isActive;
    this.mLastUpdated = lastUpdated;
  }
}
