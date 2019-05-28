package com.example.ivani.hausaufgabe.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.model.CreateEventModel;
import com.example.ivani.hausaufgabe.model.UserModel;
import com.example.ivani.hausaufgabe.network.NetworkManager;

import java.util.Objects;

import lombok.Setter;

public class CreateEventDialogFragment extends DialogFragment {




    private NetworkManager networkManager;

    private EditText etTitle;
    private EditText numOfParticipants;
    private EditText description;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText country;
    private EditText city;
    @Setter
    private UserModel userModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.create_event_layout, null);
        networkManager = new NetworkManager(getActivity());
        etTitle = root.findViewById(R.id.eventtitle);
        numOfParticipants  = root.findViewById(R.id.participants);
        description = root.findViewById(R.id.description);
        datePicker = root.findViewById(R.id.datePicker);
        timePicker = root.findViewById(R.id.timePicker);
        country = root.findViewById(R.id.country);
        city = root.findViewById(R.id.city);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(root)
                .setNegativeButton(getString(R.string.cancel), null)
                .setCancelable(true)
                .setTitle(R.string.createevent)
                .setPositiveButton(getString(R.string.save), null);

        AlertDialog alertDialog = builder.create();

        Objects.requireNonNull(alertDialog.getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button positiveButton = ((AlertDialog) getDialog()).getButton(Dialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateEventModel eventModel= buildCreateEventModel();
                networkManager.createEvent(eventModel);
            }
        });
    }

    private CreateEventModel buildCreateEventModel() {
        CreateEventModel eventModel = new CreateEventModel();
        eventModel.setTitle(etTitle.getText().toString());
        eventModel.setCity(city.getText().toString());
        eventModel.setCountry(country.getText().toString());
        eventModel.setDescription(description.getText().toString());
        try {
            eventModel.setNumOfMaxParticipants(Integer.valueOf(numOfParticipants.getText().toString()));
        } catch (NumberFormatException numFormat){
            System.err.println(numFormat);
        }
        eventModel.setDateTime(createDateAndTime());
        eventModel.setUserId(userModel.getId());
        return eventModel;
    }


    private String createDateAndTime() {
        return datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth()+" "+ timePicker.getHour()+":"+timePicker.getMinute();
    }
}
