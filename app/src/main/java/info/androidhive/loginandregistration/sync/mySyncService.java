package info.androidhive.loginandregistration.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class mySyncService extends Service {
    private static mySyncAdapter mSyncAdapter = null;
    public SyncService() {
        super();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (mSyncAdapter == null) {
            mSyncAdapter = new mySyncAdapter(getApplicationContext(), true);
        }
    }
    @Override
    public IBinder onBind(Intent arg0) {
        return mSyncAdapter.getSyncAdapterBinder();
    }
}