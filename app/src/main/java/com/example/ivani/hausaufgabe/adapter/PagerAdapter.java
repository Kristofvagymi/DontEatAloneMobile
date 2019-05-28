package com.example.ivani.hausaufgabe.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ivani.hausaufgabe.fragment.UpcomingEventFragment;
import com.example.ivani.hausaufgabe.fragment.HistoricEventFragment;
import com.example.ivani.hausaufgabe.fragment.UserInformationFragment;
import com.example.ivani.hausaufgabe.model.UserModel;

import lombok.Setter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int tabCount;

    @Setter
    private UserModel userModel;
    private String titleOfButton;
    private UpcomingEventFragment upcomingEventTab;
    private HistoricEventFragment historycTab;

    public PagerAdapter(FragmentManager fm, int tabCount, UserModel userModel,String titleOfButton) {
        super(fm);
        this.tabCount = tabCount;
        this.userModel = userModel;
        this.titleOfButton = titleOfButton;
        this.upcomingEventTab = new UpcomingEventFragment(userModel);
        this.upcomingEventTab.setTabUpdater(this);
        this.historycTab = new HistoricEventFragment(userModel);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return upcomingEventTab;
            case 1:
                return historycTab;
            case 2:
                UserInformationFragment changeProfileTab= new UserInformationFragment();
                changeProfileTab.setFragmentArguments(true, userModel, titleOfButton);
                return changeProfileTab;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void updateEventFragments(){
        upcomingEventTab.updateEvents();
        historycTab.updateHistoricEvents();
    }
}