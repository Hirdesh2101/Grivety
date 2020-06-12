package com.example.grivety;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class peopleholder extends RecyclerView.ViewHolder {

    public RelativeLayout layout1;
    public TextView text1;
    public ImageView image1;

    public peopleholder(@NonNull View itemView) {
        super(itemView);
        text1 = itemView.findViewById(R.id.peoplename);
        image1 = itemView.findViewById(R.id.peoplepic);
        layout1 = itemView.findViewById(R.id.peopleview);
    }
}
