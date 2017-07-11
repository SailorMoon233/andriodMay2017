package com.retaileragrsmb.API;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hitesh on 5/21/2017.
 */

public interface RechargeService {

    //http://bmxservicesapp.mybluemix.net/BmxServicesApp/RequestProcessServlet?id=9011008229&name=IDEA&value=100

    @GET("/BmxServicesApp/RequestProcessServlet")
    Call<JsonObject> recharge(@Query("id") String number, @Query("name") String operator, @Query("value") String rechargeAmount);
}
