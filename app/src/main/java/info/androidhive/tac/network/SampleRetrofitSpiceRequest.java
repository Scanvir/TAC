package info.androidhive.tac.network;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;
import info.androidhive.tac.model.Klient;

public class SampleRetrofitSpiceRequest extends RetrofitSpiceRequest<Klient.klients, WebserviceSkalnyy> {

    private String uid;

    public SampleRetrofitSpiceRequest(String uid) {
        super(Klient.klients.class, WebserviceSkalnyy.class);
        this.uid = uid;
    }

    @Override
    public Klient.klients loadDataFromNetwork() {
        return getService().klients(uid);
    }
}
