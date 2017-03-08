package polybellum.android.async;

import android.content.Intent;
import android.util.Log;

/**
 * Created by mtear on 3/6/17.
 */

public class TestTask implements Runnable, IntentResult {
    int _num;
    public TestTask(int num){
        _num = num;
    }
    @Override
    public void run() {
        try {
            Log.v("test", _num + " started");
            Thread.sleep(3000);
            Log.v("test", _num + " finished");
        }catch(Exception e){

        }

    }

    @Override
    public void storeResult(Intent intent) {
        intent.putExtra("ID", _num);
    }
}
