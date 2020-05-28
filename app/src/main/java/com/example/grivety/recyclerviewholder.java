package com.example.grivety;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class recyclerviewholder extends RecyclerView.ViewHolder {

    public TextView text1;
    public ImageView image1;


    public recyclerviewholder(@NonNull View itemView) {
        super(itemView);

        text1 = itemView.findViewById(R.id.textView12);
        image1 = itemView.findViewById(R.id.image12);

    }
}
