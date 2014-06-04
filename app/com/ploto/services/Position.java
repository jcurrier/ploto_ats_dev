package com.ploto.services;

import java.util.Date;

/**
 * Created by jeff on 5/18/14.
 */
public class Position {
  private String mCustomerid;
  private String mId;
  private String mTitle;
  private String mDescription;
  private String mLocation;
  private String mHiringMgrId;
  private String mRecruiterId;
  private short mStatus;
  private Date mDatePosted;
  private Date mLastUpdated;

  public static final short OPEN_STATUS = 0;
  public static final short CLOSED_STATUS = 1;

  public String getCustomerId() {
    return mCustomerid;
  }

  public void setCustomerId(String customerId) {
    mCustomerid = customerId;
  }

  public String getId() {
    return mId;
  }

  public void setId(String newId) {
    mId = newId;
  }

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String description) {
    mDescription = description;
  }

  public String getLocation() {
    return mLocation;
  }

  public void setLocation(String location) {
    mLocation = location;
  }

  public short getStatus() {
    return mStatus;
  }

  public String getHiringMgrId() {
    return mHiringMgrId;
  }

  public void setHiringMgrId(String hiringMgrId) {
    mHiringMgrId = hiringMgrId;
  }

  public String getRecruiterId() { return mRecruiterId; }

  public void setRecruiterId(String recruiterId) { mRecruiterId = recruiterId; }

  public Date getDatePosted() {
    return mDatePosted;
  }

  public Date getLastUpdateTime() {
    return mLastUpdated;
  }

  public Position() {
  }

  public Position(String customerId, String id, String jobTitle, String description, String location,
                  String hiringMgrId, String recruiterId, short status, Date datePosted, Date lastUpdated) {
    this.mCustomerid = customerId;
    this.mId = id;
    this.mTitle = jobTitle;
    this.mDescription = description;
    this.mLocation = location;
    this.mStatus = status;
    this.mHiringMgrId = hiringMgrId;
    this.mRecruiterId = recruiterId;
    this.mDatePosted = datePosted;
    this.mLastUpdated = lastUpdated;
  }

  public Position(String customerId, String jobTitle, String description, String location, String hiringMgrId,
                  String recruiterId) {
    this.mCustomerid = customerId;
    this.mTitle = jobTitle;
    this.mDescription = description;
    this.mLocation = location;
    this.mHiringMgrId = hiringMgrId;
    this.mRecruiterId = recruiterId;
    this.mStatus = OPEN_STATUS;
  }

  @Override
  public boolean equals(Object obj) {

    if (obj instanceof Position != true) {
      return super.equals(obj);
    }

    Position tmp = (Position)obj;
    if(!this.getCustomerId().equals(tmp.getCustomerId())) {
      return false;
    }

    if(this.getDatePosted() != null &&
        !this.getDatePosted().equals(tmp.getDatePosted())) {
      return false;
    }

    if(!this.getDescription().equals(tmp.getDescription())) {
      return false;
    }

    if(!this.getHiringMgrId().equals(tmp.getHiringMgrId())) {
      return false;
    }

    if(!this.getId().equals(tmp.getId())) {
      return false;
    }

    if(this.getLastUpdateTime() != null &&
        !this.getLastUpdateTime().equals(tmp.getLastUpdateTime())) {
      return false;
    }

    if(!this.getLocation().equals(tmp.getLocation())) {
      return false;
    }

    if(!this.getRecruiterId().equals(tmp.getRecruiterId())) {
      return false;
    }

    if(this.getStatus() != tmp.getStatus()) {
      return false;
    }

    if(!this.getTitle().equals(tmp.getTitle())) {
      return false;
    }

    return true;
  }
}
