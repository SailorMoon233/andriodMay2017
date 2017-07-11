package com.retaileragrsmb.net;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


public class ServiceGenerator {
    private static final String API_BASE_URL_DISTRIBUTOR = "https://afb46f7cd7e949ed9b3f19a9fc9517c0-vp0.us.blockchain.ibm.com:5004";
    private static final String API_BASE_URL_RETAILER = "http://bmxservicesapp.mybluemix.net";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builderDistributor =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL_DISTRIBUTOR)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit.Builder builderRetailer =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL_RETAILER)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createDistributorService(Class<S> serviceClass) {
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builderDistributor.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createRechargeService(Class<S> serviceClass){
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builderRetailer.client(client).build();
        return retrofit.create(serviceClass);
    }
}
