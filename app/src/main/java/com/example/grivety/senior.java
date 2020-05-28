package com.example.grivety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class senior extends Fragment {


    FirebaseFirestore db;
    RecyclerView recyclerView;
    java.util.ArrayList<senior2> ArrayList,Arraylist2,ArrayList3;
    peopleadapter adapter,adapter2;
    SwipeRefreshLayout layoutnew;
    SearchView searchView;
    Spinner spinner1, spinner2;
    Button button,button2;
    LottieAnimationView lottieAnimationView;
    LottieAnimationView lottieAnimationView2;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.senior, null);

        ArrayList = new ArrayList<>();
        Arraylist2 = new ArrayList<>();
        ArrayList3 = new ArrayList<>();


        recyclerView = v.findViewById(R.id.peoplelist);
        layoutnew = v.findViewById(R.id.swipe2);
        spinner1 = (Spinner) v.findViewById(R.id.branchfilter);
        spinner2 = (Spinner) v.findViewById(R.id.startyearfilter);
        searchView = v.findViewById(R.id.searchview);
        button = v.findViewById(R.id.filterbutton);
        button2 = v.findViewById(R.id.filtercancel);
        lottieAnimationView = v.findViewById(R.id.selectfilterofpeople);
        lottieAnimationView2 = v.findViewById(R.id.loadinglogin2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        final ArrayAdapter<CharSequence> adapter23 = ArrayAdapter.createFromResource(getContext(), R.array.Year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter23);

        db = FirebaseFirestore.getInstance();

        final String Branch1 = spinner1.getSelectedItem().toString();
        final String year1 = spinner2.getSelectedItem().toString();

        String check1 = "Select Branch";
        String check2 = "Select Year";
        if (Branch1.equals(check1)&&year1.equals(check2)){
            lottieAnimationView.setVisibility(View.VISIBLE);
            lottieAnimationView.playAnimation();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Branch2 = spinner1.getSelectedItem().toString();
                final String year2 = spinner2.getSelectedItem().toString();

                String check1 = "Select Branch";
                String check2 = "Select Year";

                if (Branch2.equals(check1)) {
                    Toast.makeText(getContext(), "Select Branch", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (year2.equals(check2)) {
                    Toast.makeText(getContext(), "Select Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                lottieAnimationView.pauseAnimation();
                lottieAnimationView.setVisibility(View.INVISIBLE);
                button.setClickable(false);
                spinner1.setEnabled(false);
                spinner2.setEnabled(false);
                lottieAnimationView2.setVisibility(View.VISIBLE);
                lottieAnimationView2.playAnimation();

                fetchpeople2();


            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragment2 = getFragmentManager().beginTransaction();
                fragment2.detach(senior.this);
                fragment2.attach(senior.this);
                fragment2.commit();
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                searchView.setVisibility(View.INVISIBLE);
            }
        });

        layoutnew.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               final FragmentTransaction fragment2 = getFragmentManager().beginTransaction();
                fragment2.detach(senior.this);
                fragment2.attach(senior.this);
                fragment2.commit();
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                layoutnew.setRefreshing(false);
            }
        });

        return v;
    }

    private void fetchpeople2() {

        final String Branch = spinner1.getSelectedItem().toString();
        final String year = spinner2.getSelectedItem().toString();

        CollectionReference documentReference = db.collection("Users");

        documentReference.whereEqualTo("Branch",Branch)
                .whereEqualTo("Start Year",year)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            senior2 finalnewss = new senior2(snapshot.getString("Fullname"),
                                    snapshot.getString("Branch"),
                                    snapshot.getString("Start Year"),
                                    snapshot.getString("Image"));

                            Arraylist2.add(finalnewss);
                        }
                        adapter2 = new peopleadapter(getContext(),Arraylist2);
                        lottieAnimationView2.pauseAnimation();
                        lottieAnimationView2.setVisibility(View.INVISIBLE);
                        recyclerView.setAdapter(adapter2);
                        searchView.setVisibility(View.VISIBLE);
                        adapter2.notifyDataSetChanged();

                    }

                });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!= null){
                    adapter2.getFilter().filter(newText);}
                return false;
            }
        });
    }


    private void fetchpeople() {
        layoutnew.setRefreshing(true);


        CollectionReference documentReference = db.collection("Users");

        documentReference.orderBy("Fullname")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            senior2 finalnewss = new senior2(snapshot.getString("Fullname"),
                                    snapshot.getString("Branch"),
                                    snapshot.getString("Start Year"),
                                    snapshot.getString("Image"));

                            ArrayList.add(finalnewss);
                        }
                        adapter = new peopleadapter(getContext(),ArrayList);

                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }

                });
        layoutnew.setRefreshing(false);
    }

}