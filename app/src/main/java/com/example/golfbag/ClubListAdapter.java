/*
 * This class is the adapter for the club list.
 * Gets the data from the club list and populates the recycler view.
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

public class ClubListAdapter extends RecyclerView.Adapter<ClubListAdapter.ViewHolder> {

    private ArrayList<Club> clubList;
    private onClubClickListener listener;

    public interface onClubClickListener{
        void onClubClick(int position);
        void onDeleteClick(int position);
        void onAddClick (int position);
    }

    public void setOnClubClickListener(onClubClickListener listener1) {
        listener = listener1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public ImageView deleteImage;
        public ImageView addImage;

        public ViewHolder(@NonNull View itemView, final onClubClickListener listener1) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            deleteImage = itemView.findViewById(R.id.deleteImage);
            addImage = itemView.findViewById(R.id.addImage);

            //on click listeners for clicking the club, delete image and add image.
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

            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener1 != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener1.onAddClick(position );
                        }
                    }
                }
            });
        }
    }

    public ClubListAdapter(ArrayList<Club> clubList1) {

        clubList = clubList1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
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
