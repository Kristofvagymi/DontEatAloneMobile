package com.example.ivani.hausaufgabe.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.adapter.DetailedInformationVisualizer;
import com.example.ivani.hausaufgabe.adapter.PagerAdapter;
import com.example.ivani.hausaufgabe.adapter.UpcomingEventAdapter;
import com.example.ivani.hausaufgabe.model.EventDetailedModel;
import com.example.ivani.hausaufgabe.model.EventPreviewModel;
import com.example.ivani.hausaufgabe.model.UserModel;
import com.example.ivani.hausaufgabe.network.NetworkManager;

@SuppressLint("ValidFragment")
public class UpcomingEventFragment extends Fragment implements UpcomingEventAdapter.PressListener, DetailedInformationVisualizer {

    private NetworkManager networkManager;

    private UserModel userModel;

    private UpcomingEventAdapter adapter;
    private PagerAdapter pagerAdapter;

    @SuppressLint("ValidFragment")
    public UpcomingEventFragment(UserModel userModel){
        this.userModel=userModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.upcoming_events_layout,container,false);
        networkManager = new NetworkManager(getActivity());
        initFloatingActionButton(root);
        initRecyclerView(root);
        return root;
    }

    private void initFloatingActionButton(View root) {
        FloatingActionButton fabCreateEvent = root.findViewById(R.id.createevent);
        fabCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateEventDialogFragment dialogFragment = new CreateEventDialogFragment();
                dialogFragment.setUserModel(userModel);
                FragmentManager fm = getActivity().getSupportFragmentManager();

                dialogFragment.show(fm, "CREATE_EVENT_DIALOG");
            }
        });
    }

    private void initRecyclerView(View root) {
        RecyclerView recyclerView = root.findViewById(R.id.eventRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UpcomingEventAdapter(this);
        recyclerView.setAdapter(adapter);
        updateEvents();
    }

    public void updateEvents(){
        networkManager.getEvents(adapter, userModel);
    }

    @Override
    public void listItemPressed(EventPreviewModel eventPreviewModel) {
        networkManager.getEventDetails(eventPreviewModel,this);
    }

    @Override
    public void buttonPressed(int eventId, int postition) {
        networkManager.applyToAnEvent(eventId, userModel.getId(), pagerAdapter);
    }

    @Override
    public void showDetailedInformation(EventDetailedModel detailedModel) {
        EventDetailedInfoDialogFragment  eventDetailedInfoDialogFragment = new EventDetailedInfoDialogFragment();
        eventDetailedInfoDialogFragment.setDetailedModel(detailedModel);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        eventDetailedInfoDialogFragment.show(fm, "EVENT_DETAILS_DIALOG");
    }

    public void setTabUpdater(PagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }
}
