package com.ploto.util;

/**
 * Created by jeff on 5/18/14.
 */
public class PhoneNumber {
  private String mCountryCode;

  private String mAreaCode;

  private String mNumber;

  public String getPhoneNumber() {
    return String.format("+%s (%s)%s", mCountryCode, mAreaCode, mNumber);
  }

  public String getCountryCode() {
    return mCountryCode;
  }

  public String getAreaCode() {
    return mAreaCode;
  }

  public String getSubscriberNumber() {
    return mNumber;
  }

  public PhoneNumber(String areaCode, String phoneNumber) {
    mCountryCode = "01";
    mAreaCode = areaCode;
    mNumber = phoneNumber;
  }

  public PhoneNumber(String countryCode, String areaCode, String phoneNumber) {
    mCountryCode = countryCode;
    mAreaCode = areaCode;
    mNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return String.format("+%s (%s)%s", mCountryCode, mAreaCode, mNumber);
  }
}
