package com.example.grivety;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class commentadapter extends RecyclerView.Adapter<commentviewholder> {

    ArrayList<comment2> comment2s;


    public commentadapter(ArrayList<comment2> comment2s) {
        this.comment2s = comment2s;
    }

    @NonNull
    @Override
    public commentviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.commentview,parent,false);
        return new commentviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull commentviewholder holder, int position) {

        holder.comment.setText(comment2s.get(position).getComment());
        holder.profilname.setText(comment2s.get(position).getFullname());

        Picasso.get().load(comment2s.get(position).getImage()).placeholder(R.drawable.ic_person_grey_24dp).fit().centerCrop().into(holder.profileimg);
    }

    @Override
    public int getItemCount() {
        return comment2s.size();
    }
}
