package com.example.eventmanagement;


    import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

    public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

        private List<HotelRatingClass> hotelList;
        private Context context;

        public HotelAdapter(List<HotelRatingClass> hotelList) {
            this.hotelList = hotelList;
        }

        @NonNull
        @Override
        public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
            return new HotelViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
            HotelRatingClass hotel = hotelList.get(position);

            // Bind hotel data to the ViewHolder
            holder.hotelNameTextView.setText("Hotel ID: " + hotel.getHotelId());
            holder.hotelRatingTextView.setText("Average Rating: " + hotel.getAverageRating());
        }

        @Override
        public int getItemCount() {
            return hotelList.size();
        }

        public class HotelViewHolder extends RecyclerView.ViewHolder{

            TextView hotelNameTextView;
            TextView hotelRatingTextView;

            public HotelViewHolder(@NonNull View itemView) {
                super(itemView);
                hotelNameTextView = itemView.findViewById(R.id.hotelNameTextView);
                hotelRatingTextView = itemView.findViewById(R.id.hotelRatingTextView);
            }
        }
    }



