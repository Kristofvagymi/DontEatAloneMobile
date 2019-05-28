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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ivani.hausaufgabe.MainActivity;
import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.model.UserModel;
import com.example.ivani.hausaufgabe.network.NetworkManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInformationFragment extends Fragment {

    private NetworkManager networkManager;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etEmail;
    private EditText etDate;
    private EditText etJob;
    private EditText etHobby;
    private EditText etPassword;
    private EditText etPhone;
    private RadioGroup rdgSex;

    private boolean loadDataFromModel = false;
    private UserModel model;
    private String textOnSubmitButton;

    public void setFragmentArguments(boolean loadDataFromModel, UserModel model, String textOnSubmitButton){
        this.loadDataFromModel = loadDataFromModel;
        this.model = model;
        this.textOnSubmitButton = textOnSubmitButton;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        networkManager = new NetworkManager(getActivity());
        if(!loadDataFromModel){
            textOnSubmitButton = getString(R.string.register);
        }
        View root = inflater.inflate(R.layout.userinformation_layout,container,false);
        etFirstName = root.findViewById(R.id.firstName);
        etLastName = root.findViewById(R.id.lastName);
        etEmail = root.findViewById(R.id.email);
        etDate = root.findViewById(R.id.dateOfBirth);
        etJob = root.findViewById(R.id.job);
        etHobby = root.findViewById(R.id.hobby);
        etPassword = root.findViewById(R.id.password);
        etPhone = root.findViewById(R.id.phone);
        rdgSex = root.findViewById(R.id.sex);

        Button submitButton = root.findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm(createUserModel());
            }
        });

        if(loadDataFromModel){
            etFirstName.setText(model.getFirstName());
            etLastName.setText(model.getLastName());
            etEmail.setText(model.getEmail());
            etDate.setText(model.getDateOfBirth());
            etJob.setText(model.getJob());
            etHobby.setText(model.getHobby());
            etPhone.setText(model.getPhoneNumber());
            if(model.getSex().equals("male"))
                rdgSex.check(R.id.male);
            else
                rdgSex.check(R.id.female);
            submitButton.setText(textOnSubmitButton);
            etEmail.setEnabled(false);
        }





        return root;
    }

    private void submitForm(UserModel userModel) {
        if(!loadDataFromModel) {
            networkManager.register(userModel);
        }
        else{
            networkManager.updateUserModel(userModel);
        }
    }

    private UserModel createUserModel() {
        UserModel userModel = new UserModel();
        userModel.setFirstName(etFirstName.getText().toString());
        userModel.setLastName(etLastName.getText().toString());
        userModel.setEmail(etEmail.getText().toString());
        userModel.setDateOfBirth(etDate.getText().toString());
        userModel.setJob(etJob.getText().toString());
        userModel.setHobby(etHobby.getText().toString());
        userModel.setPassword(etPassword.getText().toString());
        userModel.setPhoneNumber(etPhone.getText().toString());
        userModel.setSex(getSexFromForm());
        return userModel;
    }

    private String getSexFromForm() {
        String sex;
        if(rdgSex.getCheckedRadioButtonId() == R.id.male){
            sex = "male";
        } else {
            sex = "female";
        }
        return sex;
    }
}
