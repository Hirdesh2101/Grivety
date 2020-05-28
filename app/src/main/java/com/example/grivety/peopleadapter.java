package com.example.grivety;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class peopleadapter extends RecyclerView.Adapter<peopleholder> implements Filterable{

    Context mcontext2;
    ArrayList<senior2> senior2s;
    ArrayList<senior2> senior2sall;
    Dialog mydailog;

    public peopleadapter(Context mcontext2, ArrayList<senior2> senior2s) {
        this.mcontext2 = mcontext2;
        this.senior2s = senior2s;
        this.senior2sall = new ArrayList<>(senior2s);
    }

    @NonNull
    @Override
    public peopleholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v ;

        v= LayoutInflater.from(mcontext2).inflate(R.layout.peopleview,parent,false);
        final peopleholder vholder = new peopleholder(v);

        mydailog = new Dialog(mcontext2);
        mydailog.setContentView(R.layout.dailog_people);
        mydailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mydailog.setCanceledOnTouchOutside(true);


        vholder.layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dailogname = mydailog.findViewById(R.id.onclickname);
                TextView dailogbranch = mydailog.findViewById(R.id.onclickbranch);
                TextView dailogyear = mydailog.findViewById(R.id.onclickyear);
                ImageView dailogimage = mydailog.findViewById(R.id.onclickimage);
                dailogname.setText(senior2s.get(vholder.getAdapterPosition()).getName());
                dailogbranch.setText(senior2s.get(vholder.getAdapterPosition()).getBranch());
                dailogyear.setText(senior2s.get(vholder.getAdapterPosition()).getYear()+" Year");
                Picasso.get().load(senior2s.get(vholder.getAdapterPosition()).getImage()).placeholder(R.drawable.ic_person_grey_24dp).fit().centerCrop().into(dailogimage);
                mydailog.show();
            }
        });


        return vholder;
    }

    @Override
    public void onBindViewHolder(@NonNull peopleholder holder, int position) {
        final senior2 news2 = senior2s.get(position);

        holder.text1.setText(senior2s.get(position).getName());

        Picasso.get().load(news2.getImage())
                .placeholder(R.drawable.ic_person_grey_24dp)
                .fit().centerCrop().into(holder.image1);
    }

    @Override
    public int getItemCount() {
        return senior2s.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<senior2> filteredlist = new ArrayList<>();
            if (constraint.toString().isEmpty()){
                filteredlist.addAll(senior2sall);
            }else{
                for(senior2 senior2sd : senior2sall){
                    if (senior2sd.getName().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredlist.add(senior2sd);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredlist;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            senior2s.clear();
            senior2s.addAll((Collection<? extends senior2>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
