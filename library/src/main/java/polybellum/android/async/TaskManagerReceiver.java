// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>

package polybellum.android.async;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Nic Wilson on 3/7/17.
 */

/**
 * A BroadcastReceiver for Task Manager callbacks.<br>
 * Stores a TaskCallback and uses it to perform callback events.
 */
public class TaskManagerReceiver extends BroadcastReceiver {

    /**************************************
     *          MEMBER VARIABLES
     *************************************/

    /**
     * A reference to who to give the callback to
     */
    TaskCallback _callback;

    /**************************************
     *            CONSTRUCTORS
     *************************************/

    /**
     * Initialize the variable and set a reference to the Task callback
     *
     * @param callback A reference to the task callback object
     */
    public TaskManagerReceiver(TaskCallback callback){
        _callback = callback;
    }

    /**************************************
     *          PUBLIC METHODS
     *************************************/

    @Override
    public void onReceive(Context context, Intent intent) {
        _callback.onTaskComplete(intent);
    }
}
