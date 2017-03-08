package polybellum.android.async;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by mtear on 3/7/17.
 */

public class TaskManager {

    private static int NUMBER_OF_CORES =
            Runtime.getRuntime().availableProcessors();

    static TaskManager _instance = null;

    public static TaskManager getInstance(Context appContext){
        if(_instance == null){
            _instance = new TaskManager(appContext);
        }
        return _instance;
    }

    public synchronized static void postTask(Context appContext, Runnable task){
        getInstance(appContext).innerPostTask(task);
    }

    private void innerPostTask(Runnable task){
        synchronized (_threadPool) {
            _threadPool.submit(new BackgroundTask(task));
        }
    }

    private volatile Context _appContext;
    private volatile ThreadPoolExecutor _threadPool;

    private TaskManager(Context appContext){
        _appContext = appContext;
        _threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(NUMBER_OF_CORES);
    }

    public static IntentFilter getIntentFilter(){
        IntentFilter intentFilter = new IntentFilter(TaskCallback.CALLBACK_CODE);
        return intentFilter;
    }

    public class BackgroundTask implements Runnable{

        private Runnable _task;

        public BackgroundTask(Runnable task){
            _task = task;
        }

        @Override
        public void run(){
            _task.run();
            Intent intent = new Intent(TaskCallback.CALLBACK_CODE);
            if (_appContext == null) return;
            synchronized (_appContext) {
                if(_task instanceof IntentResult){
                    ((IntentResult)_task).storeResult(intent);
                }
                LocalBroadcastManager.getInstance(_appContext).sendBroadcast(intent);
            }
        }
    }

}
