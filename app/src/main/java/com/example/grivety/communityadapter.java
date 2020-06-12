package com.example.grivety;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class communityadapter extends RecyclerView.Adapter<communityholder> {

    Context mcontext3;
    FirebaseFirestore db;
    ArrayList<community2> community2s;
    String User;
    FirebaseAuth firebaseAuth;

    public communityadapter(Context mcontext3, ArrayList<community2> community2s) {
        this.mcontext3 = mcontext3;
        this.community2s = community2s;
    }

    @NonNull
    @Override
    public communityholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mcontext3);
        View view = layoutInflater.inflate(R.layout.community2,parent,false);
        return new communityholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final communityholder holder, int position) {
        final community2 community2 = community2s.get(position);

        holder.profilname.setText(community2s.get(position).getFullname());
        holder.question.setText(community2s.get(position).getQuestion());

        Picasso.get().load(community2.getImage()).placeholder(R.drawable.ic_person_grey_24dp).fit().centerCrop().into(holder.profileimg);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        User = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Users").document(User);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){}
                        String name2 = documentSnapshot.getString("Fullname");
                        String name3 = documentSnapshot.getString("Image");
                       holder.namefull2.setText(name2);
                       holder.emailfull2.setText(name3);
                    }
                });

        final String namefull3 = holder.question.getText().toString();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext3);
        holder.commentview.setLayoutManager(linearLayoutManager);

        holder.addcomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<comment2> finalarray = new ArrayList<>();

                String s = holder.commentbox.getText().toString();
                String s2 = holder.namefull2.getText().toString();
                String s3 = holder.emailfull2.getText().toString();

                if (TextUtils.isEmpty(s)){
                    Toast.makeText(mcontext3,"Enter a comment",Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.show1.setVisibility(View.INVISIBLE);
                db = FirebaseFirestore.getInstance();

                DocumentReference doc = db.collection("Comments").document(String.valueOf(System.currentTimeMillis()));
                Map<String, Object> Userse = new HashMap<>();
                Userse.put("Question",namefull3);
                Userse.put("Comments", s);
                Userse.put("Fullname", s2);
                Userse.put("Image", s3);
                doc.set(Userse);


                CollectionReference documentReferencer = db.collection("Comments");
                documentReferencer
                        .whereEqualTo("Question",namefull3)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    comment2 questionr = new comment2(snapshot.getString("Comments"),
                                            snapshot.getString("Fullname"),
                                            snapshot.getString("Image"));
                                    finalarray.add(questionr);
                                }
                                commentadapter commentadapter = new commentadapter(finalarray);
                                holder.commentview.setAdapter(commentadapter);
                            }

                        });

                holder.commentbox.setText("");
                holder.hide.setVisibility(View.VISIBLE);
            }
        });

        holder.show1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.show1.setVisibility(View.INVISIBLE);
                final ArrayList<comment2> finalarray = new ArrayList<>();

                db = FirebaseFirestore.getInstance();

                CollectionReference documentReferencer = db.collection("Comments");
                documentReferencer
                        .whereEqualTo("Question",namefull3)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                                    comment2 questionr = new comment2(snapshot.getString("Comments"),
                                            snapshot.getString("Fullname"),
                                            snapshot.getString("Image"));
                                    finalarray.add(questionr);
                                }
                                commentadapter commentadapter = new commentadapter(finalarray);
                                holder.commentview.setAdapter(commentadapter);
                            }

                        });


                holder.hide.setVisibility(View.VISIBLE);

            }
        });
        holder.hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.show1.setVisibility(View.VISIBLE);
                holder.hide.setVisibility(View.INVISIBLE);
                final ArrayList<comment2> arrays = new ArrayList<>();
                commentadapter commentadapter = new commentadapter(arrays);
                holder.commentview.setAdapter(commentadapter);
            }
        });

    }

    @Override
    public int getItemCount() {
        return community2s.size();
    }
}
