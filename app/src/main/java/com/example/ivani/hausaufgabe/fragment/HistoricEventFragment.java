package com.example.ivani.hausaufgabe.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.adapter.DetailedInformationVisualizer;
import com.example.ivani.hausaufgabe.adapter.HistoricEventAdapter;
import com.example.ivani.hausaufgabe.model.EventDetailedModel;
import com.example.ivani.hausaufgabe.model.EventPreviewModel;
import com.example.ivani.hausaufgabe.model.UserModel;
import com.example.ivani.hausaufgabe.network.NetworkManager;

@SuppressLint("ValidFragment")
public class HistoricEventFragment extends Fragment implements HistoricEventAdapter.ButtonPressedListener, DetailedInformationVisualizer {

    private RecyclerView recyclerView;
    private HistoricEventAdapter adapter;
    private NetworkManager networkManager;
    private UserModel userModel;

    @SuppressLint("ValidFragment")
    public HistoricEventFragment(UserModel userModel) {
        this.userModel = userModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.event_history,container,false);
        networkManager = new NetworkManager(getActivity());
        initRecyclerView(root);
        return root;
    }

    private void initRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.eventHistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HistoricEventAdapter(this);
        recyclerView.setAdapter(adapter);
        updateHistoricEvents();
    }

    public void updateHistoricEvents(){
        networkManager.getHistoricEvents(adapter, userModel);
    }

    @Override
    public void buttonPressed(EventPreviewModel eventPreviewModel) {
        networkManager.getEventDetails(eventPreviewModel,this);
    }

    public void showDetailedInformation(EventDetailedModel detailedModel){
        EventDetailedInfoDialogFragment  eventDetailedInfoDialogFragment = new EventDetailedInfoDialogFragment();
        eventDetailedInfoDialogFragment.setDetailedModel(detailedModel);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        eventDetailedInfoDialogFragment.show(fm, "EVENT_DETAILS_DIALOG");
    }

}
