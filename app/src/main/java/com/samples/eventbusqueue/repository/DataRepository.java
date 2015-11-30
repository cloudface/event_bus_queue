package com.samples.eventbusqueue.repository;

import android.content.Context;
import android.os.Handler;

import com.samples.eventbusqueue.repository.events.DataFetchedEvent;

import java.util.Vector;

import de.greenrobot.event.EventBus;

/**
 * Created by chrisbraunschweiler1 on 16/11/15.
 */
public class DataRepository implements Repository {

    public static final EventBus EVENT_BUS = EventBus.getDefault();

    private Context mContext;
    private boolean mPaused;
    private Vector<DataFetchedEvent> mBufferedEvents;

    public DataRepository(Context context){
        mContext = context;
        mBufferedEvents = new Vector<>();
    }

    @Override
    public void performRequest(final RepositoryListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    //Post result to main thread
                    Handler mainHandler = new Handler(mContext.getMainLooper());
                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {
                            listener.onSuccess();
                        }
                    };
                    mainHandler.post(myRunnable);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void performRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    //Post result to main thread
                    postEventIfNotPaused(new DataFetchedEvent());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    protected void postEventIfNotPaused(DataFetchedEvent event) {
        if(!mPaused) {
            EVENT_BUS.post(event);
        } else {
            mBufferedEvents.add(event);
        }
    }

    @Override
    public void resume() {
        mPaused = false;
        sendBufferedEvents();
    }

    private void sendBufferedEvents() {
        while(mBufferedEvents.size() > 0){
            final DataFetchedEvent bufferedEvent = mBufferedEvents.elementAt(0);
            mBufferedEvents.removeElementAt(0);
            EVENT_BUS.post(bufferedEvent);
        }
    }

    @Override
    public void pause() {
        mPaused = true;
    }
}
