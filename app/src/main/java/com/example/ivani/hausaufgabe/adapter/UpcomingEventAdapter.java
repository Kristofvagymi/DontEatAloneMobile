package com.example.ivani.hausaufgabe.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ivani.hausaufgabe.R;
import com.example.ivani.hausaufgabe.model.EventPreviewModel;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.EventViewHolder> {

    private List<EventPreviewModel> events;
    private PressListener listener;

    public interface PressListener {
        void listItemPressed(EventPreviewModel eventPreviewModel);
        void buttonPressed(int eventId, int position);
    }

    public UpcomingEventAdapter(PressListener listener) {
        this.listener = listener;
        events = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateListItems( List<EventPreviewModel> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull final UpcomingEventAdapter.EventViewHolder eventViewHolder, final int position) {
        eventViewHolder.title.setText(events.get(position).getTitle());
        eventViewHolder.city.setText(events.get(position).getCity());
        eventViewHolder.date.setText(events.get(position).getDate());

        eventViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.buttonPressed(events.get(eventViewHolder.getAdapterPosition()).getId(), eventViewHolder.getAdapterPosition());
            }
        });

    }

    @Override
    public UpcomingEventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_row_layout, viewGroup, false);
        return new UpcomingEventAdapter.EventViewHolder(view);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView city;
        TextView date;
        Button button;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.eventtitle);
            city = itemView.findViewById(R.id.eventCity);
            date = itemView.findViewById(R.id.eventDate);
            button = itemView.findViewById(R.id.accept);
            button.setText(R.string.imin);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.listItemPressed(events.get(getAdapterPosition()));
                }
            });
        }
    }

}
