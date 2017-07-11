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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.retaileragrsmb.API.RechargeService;
import com.retaileragrsmb.R;
import com.retaileragrsmb.common.Utils;
import com.retaileragrsmb.model.AmountResponseModel;
import com.retaileragrsmb.model.RechargeModel;
import com.retaileragrsmb.model.RechargeResponseModel;
import com.retaileragrsmb.net.ServiceGenerator;
import com.retaileragrsmb.ui.adapter.RechargeAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hitesh on 5/6/2017.
 */

public class DashboardFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.spinner)
    Spinner spinnerOperator;
    @BindView(R.id.et_Number)
    EditText etNumber;
    @BindView(R.id.list_recharge)
    ListView listRecharge;
    private ProgressDialog progress;
    private RechargeService rechargeService;
    private String selectedOperator;
    private  String selectedAmount;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        
        spinnerOperator.setOnItemSelectedListener(this);

        rechargeService = ServiceGenerator.createRechargeService(RechargeService.class);

        List<String> list = new ArrayList<String>();
        list.add("Idea");
        list.add("Airtel");
        list.add("Vodafone");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperator.setAdapter(dataAdapter);

        RechargeAdapter adapter = new RechargeAdapter(getActivity(), null);
        listRecharge.setAdapter(adapter);

        listRecharge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RechargeModel  model = (RechargeModel) adapterView.getItemAtPosition(i);
                selectedAmount = String.valueOf(model.getAmount());
            }
        });

        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        selectedOperator = adapterView.getItemAtPosition(position).toString();

        Toast.makeText(getActivity(), selectedOperator + "  selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @OnClick(R.id.btnSubmit)
    public void onSubmitClick(){

        if(etNumber.getText().toString().trim().length() < 10){
            Toast.makeText(getActivity(), "Enter valid 10 digit number.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedAmount == null || selectedAmount.length() == 0){
            Toast.makeText(getActivity(), "Select a valid recharge.", Toast.LENGTH_SHORT).show();
            return;
        }

        rechargeNumber(etNumber.getText().toString().trim(), selectedOperator, selectedAmount);

    }

    private void rechargeNumber(final String number, String operator, String amount) {
        //binding.username.getText().toString()
        showLoadingDialog();
        Call call = rechargeService.recharge(number, operator, amount);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response) {
                dismissLoadingDialog();
                JsonObject responseObject = response.body();

                RechargeResponseModel model = new Gson().fromJson(responseObject.toString(), RechargeResponseModel.class);

                if (model == null) {
                    //404 or the response cannot be converted to RechargeModel.
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
                    Utils.showAlert(getActivity(),
                            getString(R.string.dialog_title_recharge),
                            String.format(getString(R.string.dialog_message_recharge), number, model.getId()));
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
}
