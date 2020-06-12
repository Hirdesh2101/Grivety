package com.example.grivety;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class recyclerviewadapter extends RecyclerView.Adapter<recyclerviewholder>{

    Context mcontext;
    ArrayList<news2> news2s;

    public recyclerviewadapter(Context mcontext, ArrayList<news2> news2s) {
        this.mcontext = mcontext;
        this.news2s = news2s;
    }

    @NonNull
    @Override
    public recyclerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(mcontext);
        View view = layoutInflater.inflate(R.layout.news1,parent,false);
        return new recyclerviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerviewholder holder, int position) {
        final news2 news2 = news2s.get(position);

        holder.text1.setText(news2s.get(position).getNews());

        Picasso.get().load(news2.getImageurl()).into(holder.image1);

    }

    @Override
    public int getItemCount() {
        return news2s.size();
    }
}
