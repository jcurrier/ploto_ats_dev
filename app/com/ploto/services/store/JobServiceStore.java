package com.ploto.services.store;

import com.google.common.collect.ImmutableList;
import com.ploto.services.Position;

/**
 * Created by jeff on 5/18/14.
 */
public interface JobServiceStore {

  public Position storeJob(Position newPosition) throws StoreException;

  public void removeJob(Position positionToRemove) throws StoreException;

  public ImmutableList<Position> fetchOpenPositions() throws StoreException;
}
