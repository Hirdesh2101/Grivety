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
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
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
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class news extends Fragment {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ArrayList<news2> ArrayList;
    recyclerviewadapter adapter;
    SwipeRefreshLayout layoutnew;
    SliderView sliderView;
    LottieAnimationView lottieAnimationView;
    List<SliderItem> mSliderItems;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.news,null);

        ArrayList = new ArrayList<>();
        mSliderItems = new ArrayList<>();


        recyclerView = v.findViewById(R.id.recyclerview);
        layoutnew = v.findViewById(R.id.swipe);
        lottieAnimationView = v.findViewById(R.id.loadingnews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        db = FirebaseFirestore.getInstance();
        sliderView = v.findViewById(R.id.imageSlider);

        fetchnews();
        fetchslider();

        layoutnew.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FragmentTransaction fragment = getFragmentManager().beginTransaction();
                layoutnew.setRefreshing(true);
                fragment.detach(news.this);
                fragment.attach(news.this);
                fragment.commit();
                layoutnew.setRefreshing(false);
            }
        });

        return v;
    }

    private void fetchslider() {
        layoutnew.setRefreshing(true);

        CollectionReference documentReference = db.collection("News");

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            SliderItem finalnewss = new SliderItem(snapshot.getString("News"),
                                    snapshot.getString("Url"));
                            mSliderItems.add(finalnewss);
                        }
                        SliderAdapterExample adapter = new SliderAdapterExample(getContext(),mSliderItems);
                        sliderView.setSliderAdapter(adapter);
                        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
                        sliderView.setIndicatorSelectedColor(Color.WHITE);
                        sliderView.setIndicatorUnselectedColor(Color.GRAY);
                        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                        sliderView.startAutoCycle();

                    }

                });
        layoutnew.setRefreshing(false);
    }

    private void fetchnews() {
        layoutnew.setRefreshing(true);
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        CollectionReference documentReference = db.collection("News");

        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            news2 finalnewss = new news2(snapshot.getString("News"),
                                    snapshot.getString("Url"),
                                    snapshot.getString("Data"));
                            ArrayList.add(finalnewss);
                        }
                        adapter = new recyclerviewadapter(getContext(),ArrayList);
                        lottieAnimationView.pauseAnimation();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                });
        layoutnew.setRefreshing(false);
    }


}


