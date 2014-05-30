package com.ploto.services.store;

import play.db.DB;

import javax.sql.DataSource;
import java.rmi.server.UID;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by jeff on 5/23/14.
 */
public class BaseSqlStore {

  protected DataSource mDb = null;

  public BaseSqlStore() {
    mDb = DB.getDataSource("plotodb");
  }

  protected Connection getConnection() throws SQLException {
    Connection dbConn = mDb.getConnection();
    dbConn.setAutoCommit(false);

    return mDb.getConnection();
  }

  protected String generateUniqueId() {
    UID uniqueId = new UID();
    return uniqueId.toString();
  }
}
