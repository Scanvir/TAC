package info.androidhive.tac.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class SampleRetrofitSpiceService extends RetrofitGsonSpiceService {

    private final static String BASE_URL = "http://ws.skalnyy.com/android_api";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(WebserviceSkalnyy.class);
    }

    @Override
    protected String getServerUrl() {
        return BASE_URL;
    }

}
