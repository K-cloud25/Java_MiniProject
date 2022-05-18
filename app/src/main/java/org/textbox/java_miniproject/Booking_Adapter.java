package org.textbox.java_miniproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Booking_Adapter extends RecyclerView.Adapter<Booking_Adapter.myViewHolder>{

    private ArrayList<Sports_Booking_Class> array;

    public Booking_Adapter(ArrayList<Sports_Booking_Class> _array){
        this.array = _array;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.booking_layout_item,null);
        myViewHolder viewHolder = new myViewHolder(listItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Sports_Booking_Class current_Booking = array.get(position);

        holder.bookingTimingTV.setText(current_Booking.getTime());
        holder.bookingStatusTV.setText(current_Booking.getStatus().toString());
        holder.bookingDateTV.setText(current_Booking.getDate());
        holder.bookingNameTV.setText(current_Booking.getSport());
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder{

        public TextView bookingNameTV,bookingStatusTV,bookingDateTV,bookingTimingTV;

        public myViewHolder(View itemView){
            super(itemView);
            bookingNameTV = itemView.findViewById(R.id.bookingNameTV);
            bookingDateTV = itemView.findViewById(R.id.bookingDateTV);
            bookingStatusTV = itemView.findViewById(R.id.bookingStatusTV);
            bookingTimingTV = itemView.findViewById(R.id.bookingTimeTV);
        }

    }

}
