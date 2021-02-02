/*
 * This class is the adapter for the bag list.
 * Gets the data from the bag list and populates the recycler view.
 *
 * @author  Robbie Flockhart - 40343879
 */
package com.example.golfbag;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BagListAdapter extends RecyclerView.Adapter<BagListAdapter.ViewHolder> {

    private ArrayList<Club> clubList;
    private onClubClickListener listener;

    public interface onClubClickListener{
        void onClubClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnClubClickListener(onClubClickListener listener1) {
        listener = listener1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public ImageView deleteImage;

        public ViewHolder(@NonNull View itemView, final onClubClickListener listener1) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            deleteImage = itemView.findViewById(R.id.deleteImage);

            //on click listeners for clicking the club and delete image.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener1.onClubClick(position);
                        }
                    }
                }
            });

            deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener1.onDeleteClick(position );
                        }
                    }
                }
            });
        }
    }

    public BagListAdapter(ArrayList<Club> clubList1) {

        clubList = clubList1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bag_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, listener );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Club currentClub = clubList.get(position);

        holder.imageView.setImageResource(currentClub.getImage());
        holder.textView1.setText(currentClub.getName());
        holder.textView2.setText(currentClub.getDescription());
    }

    @Override
    public int getItemCount() {
        return clubList.size();
    }
}