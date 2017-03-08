package polybellum.android.async;

import android.app.Activity;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by mtear on 3/7/17.
 */

public abstract class TaskActivity extends Activity implements TaskCallback{

    TaskManagerReceiver taskReceiver;

    protected void onStart(){
        super.onStart();
        taskReceiver = new TaskManagerReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(taskReceiver, TaskManager.getIntentFilter());
    }

    protected void onDestroy(){
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(taskReceiver);
    }

}
