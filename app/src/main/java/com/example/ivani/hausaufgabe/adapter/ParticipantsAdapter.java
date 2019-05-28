package com.example.ivani.hausaufgabe.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.model.UserModel;

import java.util.List;

public class ParticipantsAdapter extends RecyclerView.Adapter<ParticipantsAdapter.ParticipantViewHolder> {

    private List<UserModel> users;

    public ParticipantsAdapter(List<UserModel> users){
        this.users = users;
    }

    @NonNull
    @Override
    public ParticipantsAdapter.ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.participant_layout, viewGroup, false);
        return new ParticipantsAdapter.ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantsAdapter.ParticipantViewHolder participantViewHolder, int position) {
        participantViewHolder.firstName.setText(users.get(position).getFirstName());
        participantViewHolder.lastName.setText(users.get(position).getLastName());
        participantViewHolder.email.setText(users.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ParticipantViewHolder extends RecyclerView.ViewHolder {

        TextView firstName;
        TextView lastName;
        TextView email;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.fnData);
            lastName = itemView.findViewById(R.id.lnData);
            email = itemView.findViewById(R.id.email);
        }
    }
}
