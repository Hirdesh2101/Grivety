package com.example.grivety;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class drawer_header extends AppCompatActivity {

    TextView name;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestone;
    String User;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_header);

        name = (TextView) findViewById(R.id.headername);
        imageView = (ImageView) findViewById(R.id.drawerimage);

        firebaseAuth = FirebaseAuth.getInstance();
        firestone = FirebaseFirestore.getInstance();

        User = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firestone.collection("Users").document(User);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                      if (documentSnapshot.exists()){}
                      String name2 = documentSnapshot.getString("Fullname");
                      String url = documentSnapshot.getString("Image");
                      name.setText(name2);
                      Picasso.get().load(url).into(imageView);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}
