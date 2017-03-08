package polybellum.android.async;

import android.app.Application;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by mtear on 3/6/17.
 */

public class AsyncApp extends Application {

    private ThreadPoolExecutor mPool;

    @Override
    public void onCreate() {
        super.onCreate();

        mPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    }

    public void submitRunnableTask(Runnable task){
        mPool.submit(task);
    }

    @Override
    public void onTerminate(){
        mPool.shutdown();
    }

}
