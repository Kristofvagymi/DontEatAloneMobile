package com.example.ivani.hausaufgabe.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ivani.hausaufgabe.MainActivity;
import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.model.LoginModel;
import com.example.ivani.hausaufgabe.model.UserModel;
import com.example.ivani.hausaufgabe.network.NetworkManager;

import java.io.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private NetworkManager networkManager;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_layout ,container,false);
        etEmail = root.findViewById(R.id.etEmail);
        etPassword = root.findViewById(R.id.etPassword);
        btnLogin = root.findViewById(R.id.btnLogin);
        networkManager = new NetworkManager(getActivity());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginModel loginModel = new LoginModel(etEmail.getText().toString(),etPassword.getText().toString());
                networkManager.login(loginModel);
            }
        });

        return root;
    }


}
