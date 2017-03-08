// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>

package polybellum.android.async;

import android.app.Activity;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Nic Wilson on 3/7/17.
 */

/**
 * An Activity that registers and unregisters a TaskManagerReceiver.<br>
 * It also contains a TaskCallback to receive results from the Task Manager.
 */
public abstract class TaskActivity extends Activity implements TaskCallback{

    /**************************************
     *          MEMBER VARIABLES
     *************************************/

    /**
     * A receiver for callbacks from the Task Manager
     */
    private TaskManagerReceiver taskReceiver;

    /**************************************
     *          PROTECTED METHODS
     *************************************/

    /**
     * Set up a local receiver for the Task Manager callbacks
     */
    @Override
    protected void onStart(){
        super.onStart();
        taskReceiver = new TaskManagerReceiver(this);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(taskReceiver, TaskManager.getIntentFilter());
    }

    /**
     * Unregister the receiver for the Task Manager callbacks
     */
    @Override
    protected void onDestroy(){
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(taskReceiver);
    }

}
