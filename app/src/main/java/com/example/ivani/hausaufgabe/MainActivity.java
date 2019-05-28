package com.example.ivani.hausaufgabe;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.ivani.hausaufgabe.adapter.PagerAdapter;
import com.example.ivani.hausaufgabe.model.UserModel;

import lombok.Getter;
import lombok.Setter;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Getter
    private UserModel userModel;

    private PagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tablayout);
        setSupportActionBar(toolbar);



        tabLayout.addTab(tabLayout.newTab().setText(R.string.events));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.history));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.profile));
        tabLayout.setTabGravity(tabLayout.GRAVITY_FILL);

        Intent mIntent = getIntent();
        userModel = (UserModel)mIntent.getSerializableExtra("authUser");

        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), userModel, getResources().getString(R.string.change));


        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public void updateUserModel(UserModel userModel){
        this.userModel = userModel;
        adapter.setUserModel(userModel);
    }

    public void closeDialogFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("CREATE_EVENT_DIALOG");
        if (fragment != null) {
            DialogFragment dialogFragment = (DialogFragment) fragment;
            dialogFragment.dismiss();
        }
    }

    public void updateEventLists() {
        adapter.updateEventFragments();
    }
}
