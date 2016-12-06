package mx.wedevelop.came.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import mx.wedevelop.came.R;
import mx.wedevelop.came.adapter.ServiceAdapter;
import mx.wedevelop.came.model.Service;

/**
 * Created by root on 5/12/16.
 */
public class ServiceSelectionFmt extends Fragment implements AdapterView.OnItemClickListener, ServiceDialog.ServiceDialogListener {
    public static final String SERVICES = "SERVICES";

    private ServiceAdapter serviceAdapter;
    private List<Service> serviceList = new ArrayList<Service>();
    private Service service;

    public static ServiceSelectionFmt newInstance(List<Service> serviceList) {
        ServiceSelectionFmt serviceFmt = new ServiceSelectionFmt();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ServiceSelectionFmt.SERVICES, (ArrayList<Service>)serviceList);
        serviceFmt.setArguments(bundle);
        return serviceFmt;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.service_selection_fmt, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        serviceList = getArguments().getParcelableArrayList(SERVICES);
        GridView serviceGridView = (GridView) view.findViewById(R.id.service_gridview);
        serviceAdapter = new ServiceAdapter(getActivity(), serviceList);
        serviceGridView.setAdapter(serviceAdapter);
        serviceGridView.setOnItemClickListener(this);
    }

    public void notifyDataChanged(List<Service> serviceList) {
        this.serviceList.clear();
        this.serviceList.addAll(serviceList);
        serviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long time) {
        service = (Service) adapterView.getItemAtPosition(position);
        ServiceDialog.newInstance(service, this).show(getActivity().getSupportFragmentManager(), service.getName());
    }

    @Override
    public void onPositiveAnswer(DialogInterface dialog, int id) {
       serviceAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNegativeAnswer(DialogInterface dialog, int id) {

    }
}
