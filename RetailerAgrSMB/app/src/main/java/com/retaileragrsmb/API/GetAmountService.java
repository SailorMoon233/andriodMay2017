package com.retaileragrsmb.API;

import com.google.gson.JsonObject;
import com.retaileragrsmb.model.AmountResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface GetAmountService {
    @POST("/chaincode")
    Call<JsonObject> getAvailableAmount(@Body JsonObject body);
}
