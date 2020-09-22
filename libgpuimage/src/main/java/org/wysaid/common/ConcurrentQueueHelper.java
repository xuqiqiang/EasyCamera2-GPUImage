package org.wysaid.common;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentQueueHelper {
    protected ConcurrentLinkedQueue<Runnable> mQueue = new ConcurrentLinkedQueue<>();

    public ConcurrentQueueHelper() {

    }

    public void offer(Runnable runnable) {
        mQueue.offer(runnable);
    }

    public void consume() {

        try {

            Runnable runnable = null;

            do {
                runnable = mQueue.poll();
                if(runnable != null)
                    runnable.run();
            } while (runnable != null);
        } catch (Throwable throwable) {
            Log.e(Common.LOG_TAG, "ConcurrentQueueHelper: " + throwable.getLocalizedMessage());
        }
    }

    public boolean isEmpty() {
        return mQueue.isEmpty();
    }

    public void consumeLast() {

        try {
            Runnable runnable = null;

            do {
                runnable = mQueue.poll();
                if(runnable != null)
                    runnable.run();
            } while (runnable != null);
        } catch (Throwable throwable) {
            Log.e(Common.LOG_TAG, "ConcurrentQueueHelper: " + throwable.getLocalizedMessage());
        }
    }

}
