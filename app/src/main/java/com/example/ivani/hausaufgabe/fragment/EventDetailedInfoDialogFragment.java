package com.example.ivani.hausaufgabe.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.adapter.ParticipantsAdapter;
import com.example.ivani.hausaufgabe.model.EventDetailedModel;
import com.example.ivani.hausaufgabe.network.NetworkManager;

import java.util.Objects;

import lombok.Setter;

public class EventDetailedInfoDialogFragment extends DialogFragment {

    @Setter
    private EventDetailedModel detailedModel;

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.event_detailed_layout, null);
        TextView tvTitle =root.findViewById(R.id.title);
        TextView tvDescription =root.findViewById(R.id.description);
        TextView tvCountry =root.findViewById(R.id.country);
        TextView tvCity =root.findViewById(R.id.city);
        TextView tvMaxPart =root.findViewById(R.id.maxpart);
        TextView tvDate =root.findViewById(R.id.date);
        TextView tvFirstName =root.findViewById(R.id.fnData);
        TextView tvLastName =root.findViewById(R.id.lnData);
        TextView tvEmail =root.findViewById(R.id.email);

        System.out.println(detailedModel);

        tvTitle.setText(detailedModel.getTitle());
        tvDescription.setText(detailedModel.getDescription());
        tvCountry.setText(detailedModel.getCountry());
        tvCity.setText(detailedModel.getCity());
        tvMaxPart.setText( Integer.toString(detailedModel.getMaxparticipants()) );
        tvDate.setText(detailedModel.getDate());
        tvFirstName.setText(detailedModel.getCreatedBy().getFirstName());
        tvLastName.setText(detailedModel.getCreatedBy().getLastName());
        tvEmail.setText(detailedModel.getCreatedBy().getEmail());


        ParticipantsAdapter pAdapter = new ParticipantsAdapter(detailedModel.getParticipants());

        RecyclerView rvParticipants = root.findViewById(R.id.participantsRecyclerView);
        rvParticipants.setAdapter(pAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(root)
                .setNegativeButton(getString(R.string.cancel), null)
                .setCancelable(true)
                .setTitle(R.string.eventdetails);

        AlertDialog alertDialog = builder.create();

        Objects.requireNonNull(alertDialog.getWindow()).setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        return alertDialog;
    }
}
