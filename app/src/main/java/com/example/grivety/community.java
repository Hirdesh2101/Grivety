package com.example.grivety;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.grivety.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class community extends Fragment {

    FirebaseFirestore db;
    RecyclerView recyclerView;
    java.util.ArrayList<community2> ArrayList;
    communityadapter adapter;
    EditText addquestion;
    Button button1;
    String userId;
    private FirebaseAuth firebaseAuth;
    SwipeRefreshLayout layoutnew2;
    LottieAnimationView lottieAnimationView;
    String User;
    TextView fullname,email;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.community, null);

        addquestion = v.findViewById(R.id.askquestion);
        button1 = v.findViewById(R.id.questionbutton);
        lottieAnimationView = v.findViewById(R.id.loadingcommunity);
        recyclerView = v.findViewById(R.id.questionview);
        layoutnew2 = v.findViewById(R.id.swipe3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        ArrayList = new ArrayList<>();
        fullname = v.findViewById(R.id.namefull);
        email = v.findViewById(R.id.emailfull);

        User = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Users").document(User);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){}
                        final String name2 = documentSnapshot.getString("Fullname");
                        final String name3 = documentSnapshot.getString("Image");
                        fullname.setText(name2);
                        email.setText(name3);
                    }
                });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = addquestion.getText().toString();

                if (TextUtils.isEmpty(question)) {
                    Toast.makeText(getContext(), "Enter a question ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (question.length() < 10) {
                    Toast.makeText(getContext(), "Enter a valid Question", Toast.LENGTH_SHORT).show();
                }
                if (question.length() > 10 || question.length() == 10) {

                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    userId = firebaseAuth.getCurrentUser().getUid();
                    final String namefull2 = fullname.getText().toString();
                    final String namefull3 = email.getText().toString();

                    Map<String, Object> Users = new HashMap<>();
                    Users.put("Question", question);
                    Users.put("Fullname", namefull2);
                    Users.put("Image",namefull3);
                    Users.put("Time", FieldValue.serverTimestamp());
                    db.collection("Community")
                            .add(Users);
                    addquestion.setText("");

                    lottieAnimationView.pauseAnimation();
                    lottieAnimationView.setVisibility(View.INVISIBLE);
                    FragmentTransaction fragment = getFragmentManager().beginTransaction();
                    fragment.detach(community.this);
                    fragment.attach(community.this);
                    fragment.commit();
                }
            }
        });
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        CollectionReference documentReference2 = db.collection("Community");

        documentReference2.orderBy("Time", Query.Direction.DESCENDING).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                            community2 question = new community2(snapshot.getString("Question"),
                                    snapshot.getString("Fullname"),
                                    snapshot.getString("Image"));

                            ArrayList.add(question);
                        }
                        lottieAnimationView.pauseAnimation();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                        adapter = new communityadapter(getContext(),ArrayList);
                        recyclerView.setAdapter(adapter);
                    }

                });
        layoutnew2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FragmentTransaction fragment = getFragmentManager().beginTransaction();
                fragment.detach(community.this);
                fragment.attach(community.this);
                fragment.commit();
                layoutnew2.setRefreshing(false);
            }
        });
        return v;
    }
}
