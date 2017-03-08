// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>

package polybellum.android.async;

import android.content.Intent;

/**
 * Created by Nic Wilson on 3/7/17.
 */

/**
 * An inteface signifying that this object receivers callbacks from the Task Manager
 */
public interface TaskCallback {

    /**************************************
     *            STATIC FIELDS
     *************************************/

    /**
     * String for the intent action that is used for the callback
     */
    public final static String CALLBACK_CODE = "polybellum.android.async.Callback";

    /**************************************
     *          PUBLIC METHODS
     *************************************/

    /**
     * The method for handling callbacks from the Task Manager
     *
     * @param intent An intent passed from the ran task.<br>
     *               If the task implements IntentResult this can be populated with
     *               extra variables.
     */
    public void onTaskComplete(Intent intent);

}
