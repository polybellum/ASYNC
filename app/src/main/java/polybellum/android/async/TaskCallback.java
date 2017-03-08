package polybellum.android.async;

import android.content.Intent;

/**
 * Created by mtear on 3/7/17.
 */

public interface TaskCallback {

    public final static String CALLBACK_CODE = "polybellum.android.async.Callback";

    public void onTaskComplete(Intent intent);

}
