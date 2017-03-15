package info.androidhive.tac.activity;

import android.support.v7.app.AppCompatActivity;

import com.octo.android.robospice.SpiceManager;
import info.androidhive.tac.network.SampleRetrofitSpiceService;

public abstract class BaseActivity extends AppCompatActivity {
    private SpiceManager spiceManager = new SpiceManager(SampleRetrofitSpiceService.class);

    @Override
    protected void onStart() {
        spiceManager.start(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

}
