package com.example.ivani.hausaufgabe;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.ivani.hausaufgabe.fragment.LoginFragment;
import com.example.ivani.hausaufgabe.fragment.UserInformationFragment;

public class LoginActivity extends AppCompatActivity {
    private LoginFragment loginFragment = new LoginFragment();
    private UserInformationFragment registerFragment = new UserInformationFragment();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentManager.popBackStack();
            switch (item.getItemId()) {
                case R.id.navigation_login:
                    fragmentTransaction.replace(R.id.fragmentHolder, loginFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_register:
                    fragmentTransaction.replace(R.id.fragmentHolder, registerFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentHolder, loginFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
