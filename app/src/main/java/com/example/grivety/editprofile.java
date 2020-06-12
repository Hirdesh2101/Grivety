package com.example.grivety;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class editprofile extends AppCompatActivity {

    EditText fullname;
    Spinner spinner1, spinner2;
    Button edit, cancel;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    String userId;
    Uri imageuri;
    private StorageReference Folder;
    ImageView imageView,imageView3,imageView4,imageView5;
    private static final int PICK_IMAGE = 100;
    TextView textView;
    LottieAnimationView lottieAnimationView;
    String User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        Folder = FirebaseStorage.getInstance().getReference().child("Profile Images");

        fullname = (EditText) findViewById(R.id.editfullname);
        spinner1 = (Spinner) findViewById(R.id.editbranchspinner);
        spinner2 = (Spinner) findViewById(R.id.editstartyearspinner);
        edit = (Button) findViewById(R.id.saveedit);
        cancel = (Button) findViewById(R.id.btn_editcancel);
        imageView = findViewById(R.id.editimageView);
        imageView3 = findViewById(R.id.editimagehint);
        imageView4 = findViewById(R.id.imageVieweditprofile1);
        imageView5 = findViewById(R.id.imageVieweditprofile2);
        textView = findViewById(R.id.editprofiletext);
        lottieAnimationView = findViewById(R.id.loadingeditprofile);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        edit.setClickable(false);
        cancel.setClickable(false);
        imageView.setClickable(false);
        imageView5.setAlpha((float) 0.5);
        imageView4.setAlpha((float) 0.5);
        imageView3.setAlpha((float) 0.0);
        textView.setAlpha((float) 0.5);
        imageView.setAlpha((float) 0.5);
        edit.setAlpha((float) 0.5);
        cancel.setAlpha((float) 0.5);
        fullname.setClickable(false);
        fullname.setAlpha((float) 0.5);
        spinner1.setClickable(false);
        spinner1.setAlpha((float) 0.5);
        spinner2.setClickable(false);
        spinner2.setAlpha((float) 0.5);

        User = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("Users").document(User);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                        String name2 = documentSnapshot.getString("Fullname");
                        String url = documentSnapshot.getString("Image");
                        fullname.setText(name2);
                        Picasso.get().load(url).fit().placeholder(R.drawable.ic_person_grey_24dp).centerCrop().into(imageView);}

                        imageView.setClickable(true);
                        edit.setClickable(true);
                        cancel.setClickable(true);
                        imageView.setAlpha((float) 1.0);
                        edit.setAlpha((float) 1.0);
                        cancel.setAlpha((float) 1.0);
                        imageView3.setAlpha((float) 1.0);
                        imageView4.setAlpha((float) 1.0);
                        imageView5.setAlpha((float) 1.0);
                        textView.setAlpha((float) 1.0);
                        fullname.setClickable(true);
                        fullname.setAlpha((float) 1.0);
                        spinner1.setClickable(true);
                        spinner1.setAlpha((float) 1.0);
                        spinner2.setClickable(true);
                        spinner2.setAlpha((float) 1.0);
                        lottieAnimationView.pauseAnimation();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                    }
                });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addprofileimage();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Fullname = fullname.getText().toString();
                final String Branch = spinner1.getSelectedItem().toString();
                final String year = spinner2.getSelectedItem().toString();
                String check1 = "Select Branch";
                String check2 = "Select Year";
                if (TextUtils.isEmpty(Fullname)) {
                    Toast.makeText(editprofile.this, "Enter Fullname", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Branch.equals(check1)) {
                    Toast.makeText(editprofile.this, "Select Branch", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (year.equals(check2)) {
                    Toast.makeText(editprofile.this, "Select Year", Toast.LENGTH_SHORT).show();
                    return;
                }

                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                edit.setClickable(false);
                cancel.setClickable(false);
                imageView.setClickable(false);
                imageView5.setAlpha((float) 0.5);
                imageView4.setAlpha((float) 0.5);
                imageView3.setAlpha((float) 0.0);
                textView.setAlpha((float) 0.5);
                imageView.setAlpha((float) 0.5);
                edit.setAlpha((float) 0.5);
                cancel.setAlpha((float) 0.5);
                fullname.setClickable(false);
                fullname.setAlpha((float) 0.5);
                spinner1.setClickable(false);
                spinner1.setAlpha((float) 0.5);
                spinner2.setClickable(false);
                spinner2.setAlpha((float) 0.5);

                final StorageReference Image = Folder.child("image"+ UUID.randomUUID().toString());
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = Image.putBytes(data);
                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return Image.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            Uri downloaduri = task.getResult();

                            userId = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("Users").document(userId);
                            Map<String, Object> Users = new HashMap<>();
                            Users.put("Image", String.valueOf(downloaduri));
                            Users.put("Fullname", Fullname);
                            Users.put("Branch",Branch);
                            Users.put("Start Year",year);
                            documentReference.update(Users);
                            lottieAnimationView.pauseAnimation();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editprofile.this,"Editing Profile Failed",Toast.LENGTH_SHORT).show();
                        imageView.setClickable(true);
                        edit.setClickable(true);
                        cancel.setClickable(true);
                        imageView.setAlpha((float) 1.0);
                        edit.setAlpha((float) 1.0);
                        cancel.setAlpha((float) 1.0);
                        imageView3.setAlpha((float) 1.0);
                        imageView4.setAlpha((float) 1.0);
                        imageView5.setAlpha((float) 1.0);
                        textView.setAlpha((float) 1.0);
                        fullname.setClickable(true);
                        fullname.setAlpha((float) 1.0);
                        spinner1.setClickable(true);
                        spinner1.setAlpha((float) 1.0);
                        spinner2.setClickable(true);
                        spinner2.setAlpha((float) 1.0);
                        lottieAnimationView.pauseAnimation();
                        lottieAnimationView.setVisibility(View.INVISIBLE);
                    }
                });

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
    private void addprofileimage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null){
            imageuri = data.getData();
            imageView.setImageURI(imageuri);
            imageView3.setAlpha((float) 0.0);
        }
    }
}
