package polybellum.android.async;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by mtear on 3/7/17.
 */

public class TaskManagerReceiver extends BroadcastReceiver {

    TaskCallback _callback;

    public TaskManagerReceiver(TaskCallback callback){
        _callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        _callback.onTaskComplete(intent);
    }
}
