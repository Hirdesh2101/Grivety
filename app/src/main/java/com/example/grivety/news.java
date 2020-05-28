package com.example.grivety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class news extends Fragment {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<news2> ArrayList;
    recyclerviewadapter adapter;
    SwipeRefreshLayout layoutnew;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news,null);

        ArrayList = new ArrayList<>();


        recyclerView = v.findViewById(R.id.recyclerview);
        layoutnew = v.findViewById(R.id.swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        db = FirebaseFirestore.getInstance();
        fetchnews();


        layoutnew.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FragmentTransaction fragment = getFragmentManager().beginTransaction();
                fragment.detach(news.this);
                fragment.attach(news.this);
                fragment.commit();
                layoutnew.setRefreshing(false);
            }
        });

        return v;
    }

    private void fetchnews() {
        layoutnew.setRefreshing(true);
        CollectionReference documentReference = db.collection("News");

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            news2 finalnewss = new news2(snapshot.getString("News"),
                                    snapshot.getString("Url"));
                            ArrayList.add(finalnewss);
                        }
                        adapter = new recyclerviewadapter(getContext(),ArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();


                    }

                });
        layoutnew.setRefreshing(false);
    }


}


