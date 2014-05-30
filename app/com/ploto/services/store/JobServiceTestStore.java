package com.ploto.services.store;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ploto.services.Position;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jeff on 5/18/14.
 */
@Singleton
public class JobServiceTestStore implements JobServiceStore {

  private Map<String, Position> mJobMap = null;

  @Inject
  private JobServiceTestStore() {
    System.out.println("In ctor for JobServiceTestStore");

    mJobMap = new HashMap<String, Position>();
  }

  @Override
  public Position storeJob(Position newPosition) {

    mJobMap.put(newPosition.getId(), newPosition);
    return newPosition;
  }

  @Override
  public void removeJob(Position positionToRemove) {

  }

  @Override
  public ImmutableList<Position> fetchOpenPositions() {

    return null;
  }
}
