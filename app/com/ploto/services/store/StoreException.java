package com.ploto.services.store;

/**
 * Created by jeff on 5/23/14.
 */
public class StoreException extends Exception {

  public StoreException(String msg) {
    super(msg);
  }

  public StoreException(String msg, Exception ex) {
    super(msg, ex);
  }
}
