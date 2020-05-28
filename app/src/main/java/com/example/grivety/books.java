package com.example.grivety;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class books extends Fragment {

    Button button;
    String mystring = "The dealer will contact you soon.";
    Spinner spinner1,spinner2;
    String userId;
    EditText phone;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.books, null);

        phone = v.findViewById(R.id.phone);
        button = v.findViewById(R.id.buttonsubmit);
        spinner1 = v.findViewById(R.id.books2);
        spinner2 = v.findViewById(R.id.books);


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Semester = spinner1.getSelectedItem().toString();
                final String Year = spinner2.getSelectedItem().toString();
                String phoneno = phone.getText().toString();

                String check1 = "Select Year";
                String check2 = "Select Semester";

                if (Year.equals(check1)) {
                    Toast.makeText(getContext(), "Select Year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Semester.equals(check2)) {
                    Toast.makeText(getContext(), "Select Semester ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneno)) {
                    Toast.makeText(getContext(), "Enter Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (phoneno.length() < 10 || phoneno.length() > 10) {
                    Toast.makeText(getContext(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
                if (phoneno.length() == 10) {

                    userId = firebaseAuth.getCurrentUser().getUid();

                    DocumentReference documentReference = firestore.collection("Books").document(userId);
                    Map<String, Object> Users = new HashMap<>();
                    Users.put("Year", Year);
                    Users.put("Semester", Semester);
                    Users.put("Phone Number", phoneno);
                    documentReference.set(Users);
                    Toast.makeText(getContext(), mystring, Toast.LENGTH_LONG).show();
                }
            }


        });
        return v;
    }
}