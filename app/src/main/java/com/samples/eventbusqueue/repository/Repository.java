package com.samples.eventbusqueue.repository;

/**
 * Created by chrisbraunschweiler1 on 16/11/15.
 */
public interface Repository {

    void performRequest(RepositoryListener listener);

    void performRequest();

    void resume();

    void pause();

    interface RepositoryListener{
        void onSuccess();
    }
}
