// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>

package polybellum.android.async;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Nic Wilson on 3/7/17.
 */

/**
 * An object to manage Android multithreading tasks with a pool of available worker threads.
 */
public class TaskManager {

    /**************************************
     *            STATIC FIELDS
     *************************************/

    /**
     * The number of available cores in the current runtime
     */
    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();

    /**
     * The singleton class instance
     */
    private static TaskManager _instance = null;

    /**************************************
     *          STATIC METHODS
     *************************************/

    /**
     * Get the singleton instance for the Task Manager
     *
     * @param appContext The Android Application Content
     * @return The singleton instance for the Task Manager
     */
    public static TaskManager getInstance(Context appContext){
        if(_instance == null){
            _instance = new TaskManager(appContext);
        }
        return _instance;
    }

    /**
     * Get an Intent Filter object that can be used for a Task Callback
     *
     * @return An Intent Filter initialized with TaskCallback.CALLBACK_CODE
     */
    public static IntentFilter getIntentFilter(){
        IntentFilter intentFilter = new IntentFilter(TaskCallback.CALLBACK_CODE);
        return intentFilter;
    }

    /**
     * Post a task to the Task Manager to run on a background worker thread.<br>
     * If there needs to be callback data from this task make sure to implement IntentResult
     * in the Runnable object.
     *
     * @param appContext The Android Application Context
     * @param task A Runnable task to perform on a background thread
     */
    public synchronized static void postTask(Context appContext, Runnable task){
        getInstance(appContext).innerPostTask(task);
    }

    /**************************************
     *          MEMBER VARIABLES
     *************************************/

    /**
     * A reference to the Android Application Context
     */
    private volatile Context _appContext;

    /**
     * The pool of threads to run background tasks on
     */
    private volatile ThreadPoolExecutor _threadPool;

    /**************************************
     *          PRIVATE METHODS
     *************************************/

    /**
     * The inner method posting tasks to the Task Manager.<br>
     * This is not static and is used from the singleton instance to access the Thread Pool
     *
     * @param task The Runnable task to run on a background worker thread.
     */
    private void innerPostTask(Runnable task){
        synchronized (_threadPool) {
            _threadPool.submit(new BackgroundTask(task));
        }
    }

    /**************************************
     *            CONSTRUCTORS
     *************************************/

    /**
     * Initialize the Task Manager with an Application Context and start up the Thread Pool with
     * a pool equal to the number of available processor cores in the Runtime
     *
     * @param appContext The Android Application Context
     */
    private TaskManager(Context appContext) {
        _appContext = appContext;
        _threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_CORES);
    }

    /**************************************
     *           INNER CLASSES
     *************************************/

    /**
     * A private Task wrapper to the Task Manager Runnables.<br>
     * Performs a broadcast callback when a task is complete.
     */
    private class BackgroundTask implements Runnable{

        /**************************************
         *          MEMBER VARIABLES
         *************************************/

        /**
         * The Runnable to run on a background thread
         */
        private Runnable _task;

        /**************************************
         *            CONSTRUCTORS
         *************************************/

        /**
         * Initialize the objecte and store a reference to the Runnable task
         *
         * @param task The Runnable that will be ran on a background worker thread
         */
        public BackgroundTask(Runnable task){
            _task = task;
        }

        /**************************************
         *           PUBLIC METHODS
         *************************************/

        @Override
        public void run(){
            _task.run();
            Intent intent = new Intent(TaskCallback.CALLBACK_CODE);
            if (_appContext == null) return;

            //Perform a Broadcast to call back to anyone listening for Task callbacks
            synchronized (_appContext) {
                if(_task instanceof IntentResult){
                    ((IntentResult)_task).storeResult(intent);
                }
                LocalBroadcastManager.getInstance(_appContext).sendBroadcast(intent);
            }
        }

    }

}
