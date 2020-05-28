package com.example.grivety;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class commentviewholder extends RecyclerView.ViewHolder {

    public TextView comment;
    public TextView profilname;
    public ImageView profileimg;

    public commentviewholder(@NonNull View itemView) {
        super(itemView);
        comment = itemView.findViewById(R.id.commenttext);
        profilname = itemView.findViewById(R.id.commentname);
        profileimg = itemView.findViewById(R.id.commentimage2);
    }
}
