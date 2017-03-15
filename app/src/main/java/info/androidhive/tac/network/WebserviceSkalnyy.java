package info.androidhive.tac.network;

import retrofit.http.GET;
import retrofit.http.Query;
import info.androidhive.tac.model.Klient;

public interface WebserviceSkalnyy {
    @GET("/klientsList.php")
    Klient.klients klients(@Query("uid") String uid);
}