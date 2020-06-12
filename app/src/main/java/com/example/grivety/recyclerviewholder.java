package com.example.grivety;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerviewholder extends RecyclerView.ViewHolder {

    public TextView text1;
    public ImageView image1;
    public CardView cardView;


    public recyclerviewholder(@NonNull View itemView) {
        super(itemView);

        text1 = itemView.findViewById(R.id.textView12);
        image1 = itemView.findViewById(R.id.image12);
        cardView = itemView.findViewById(R.id.cardview);

    }
}
