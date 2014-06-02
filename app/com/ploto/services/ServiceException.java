package com.ploto.services;

/**
 * Created by jeff on 6/1/14.
 */
public class ServiceException extends Exception {

  public ServiceException(String msg) {
    super(msg);
  }

  public ServiceException(String msg, Exception ex) {
    super(msg, ex);
  }
}
