package com.example.grivety;

import android.app.LauncherActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mcontext,R.style.BottomSheet);
                bottomSheetDialog.setContentView(R.layout.bootomsheetdailog);
                bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                ImageView imageView = bottomSheetDialog.findViewById(R.id.bottomdailogimage);
                TextView textView = bottomSheetDialog.findViewById(R.id.bottomdailogheading);
                TextView textView2 = bottomSheetDialog.findViewById(R.id.bottomdailogdata);

                Picasso.get().load(news2.getImageurl()).into(imageView);
                textView.setText(news2.getNews());
                textView2.setText(news2.getData());
                bottomSheetDialog.show();
            }
        });

        Picasso.get().load(news2.getImageurl()).into(holder.image1);

    }

    @Override
    public int getItemCount() {
        return news2s.size();
    }
}
