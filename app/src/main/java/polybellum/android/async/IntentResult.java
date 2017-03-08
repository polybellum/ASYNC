// Copyright (C) 2017 polybellum
// Licensed under http://www.apache.org/licenses/LICENSE-2.0 <see LICENSE file>

package polybellum.android.async;

import android.content.Intent;

/**
 * Created by Nic Wilson on 3/7/17.
 */

/**
 * An interface signifying that this object will store a result in an Intent
 */
public interface IntentResult {

    /**************************************
     *          PUBLIC METHODS
     *************************************/

    /**
     * The function overridden to store a result in an Intent
     *
     * @param intent The intent to store the result to
     */
    public void storeResult(Intent intent);
}
