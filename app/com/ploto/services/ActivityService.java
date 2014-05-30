package com.ploto.services;

/**
 * Created by jeff on 5/26/14.
 */
public class ActivityService {

    private static ActivityService mInstance = null;

    private ActivityService() {

    }

    public synchronized static ActivityService instance() {
        if(mInstance == null) {
            mInstance = new ActivityService();
        }

        return mInstance;
    }
}

