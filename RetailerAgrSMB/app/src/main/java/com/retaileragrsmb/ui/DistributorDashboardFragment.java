package com.retaileragrsmb.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.retaileragrsmb.API.GetAmountService;
import com.retaileragrsmb.R;
import com.retaileragrsmb.common.Utils;
import com.retaileragrsmb.model.AmountResponseModel;
import com.retaileragrsmb.net.ServiceGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hitesh on 5/7/2017.
 */

public class DistributorDashboardFragment extends Fragment{

    @BindView(R.id.spinnerProduct)
    Spinner spinnerProduct;
    @BindView(R.id.spinnerAmount)
    Spinner spinnerAmount;
    @BindView(R.id.spinnerRetailers)
    Spinner spinnerRetailers;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private int amountToTransfer;
    private String selectedProduct;
    private String selectedRetailer;
    private int totalAvailableCount;

    private GetAmountService amountService;
    private ProgressDialog progress;


    public static DistributorDashboardFragment newInstance() {
        return new DistributorDashboardFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_distributor_dashboard, container, false);
        ButterKnife.bind(this, view);

        initSelectListener();
        initSpinnerWithDummyData();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Product Transferred successfully.", Toast.LENGTH_SHORT).show();

                if(totalAvailableCount < amountToTransfer){
                    Toast.makeText(getActivity(), "You do not have sufficient product to transfer.", Toast.LENGTH_SHORT).show();
                }else {
                    transferAmount(String.valueOf(amountToTransfer));
                }
            }
        });

        this.amountService =  ServiceGenerator.createService(GetAmountService.class);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getAvailableAmountFromServer();
    }


    private void initSelectListener(){
        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedProduct = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                amountToTransfer = Integer.parseInt((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerRetailers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedRetailer = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initSpinnerWithDummyData(){
        List<String> listProduct = new ArrayList<String>();
        addListItem(listProduct, "Product", 5);

        ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                listProduct);

        dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(dataAdapterProduct);

        List<String> listAmount = new ArrayList<String>();
        addListItem(listAmount, "", 20);

        ArrayAdapter<String> dataAdapterAmount = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                listAmount);

        dataAdapterAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAmount.setAdapter(dataAdapterAmount);


        List<String> listRetailers = new ArrayList<String>();
        addListItem(listRetailers, "Retailers", 5);

        ArrayAdapter<String> dataAdapterRetailers = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                listRetailers);

        dataAdapterRetailers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRetailers.setAdapter(dataAdapterRetailers);
    }


    private void addListItem(List<String> list, String title, int count){
        for(int i = 1; i <= count; i++){
            list.add(title + i);
        }
    }

    private void getAvailableAmountFromServer() {
        //binding.username.getText().toString()
        showLoadingDialog();
        Call call = amountService.getAvailableAmount(Utils.getAmountRequestBody());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response) {
                dismissLoadingDialog();
                JsonObject responseObject = response.body();

                AmountResponseModel model = new Gson().fromJson(responseObject.toString(), AmountResponseModel.class);

                if (model == null) {
                    //404 or the response cannot be converted to AmountResponseModel.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            Toast.makeText(getActivity(), responseBody.string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error occurs", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //200
                    if(model.getResult() != null && model.getResult().getMessage() != null)
                        amount.setText(model.getResult().getMessage());
                    try{
                        totalAvailableCount = Integer.parseInt(model.getResult().getMessage());
                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
            }
        });
    }

    private void showLoadingDialog() {

        if (progress == null) {
            progress = new ProgressDialog(getActivity());
//            progress.setTitle(getString());
            progress.setMessage("Loading");
        }
        progress.show();
    }

    private void dismissLoadingDialog() {

        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }

    private void transferAmount(String transferAmount) {
        //binding.username.getText().toString()
        showLoadingDialog();
        Call call = amountService.transferAmount(Utils.getTransferAmountRequestBody(transferAmount));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response) {
                dismissLoadingDialog();
                JsonObject responseObject = response.body();

                AmountResponseModel model = new Gson().fromJson(responseObject.toString(), AmountResponseModel.class);

                if (model == null) {
                    //404 or the response cannot be converted to AmountResponseModel.
                    ResponseBody responseBody = response.errorBody();
                    if (responseBody != null) {
                        try {
                            Toast.makeText(getActivity(), responseBody.string(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error occurs", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //200
                    if(model.getResult() != null && model.getResult().getMessage() != null)
                        if(totalAvailableCount >= amountToTransfer) {
                            totalAvailableCount -= amountToTransfer;
                            amount.setText(String.valueOf(totalAvailableCount));
                        }

                        Utils.showAlert(getActivity(),
                                getString(R.string.dialog_title),
                                String.format(getString(R.string.dialog_message), model.getResult().getMessage()));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                dismissLoadingDialog();
            }
        });
    }

}
