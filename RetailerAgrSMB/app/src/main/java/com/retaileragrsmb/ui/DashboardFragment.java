package com.retaileragrsmb.ui;

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

import com.retaileragrsmb.R;
import com.retaileragrsmb.ui.adapter.RechargeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        
        spinnerOperator.setOnItemSelectedListener(this);

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

        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        String item = adapterView.getItemAtPosition(position).toString();

        Toast.makeText(getActivity(), item + "  selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
