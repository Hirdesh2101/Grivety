package com.example.grivety;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class addpicture extends AppCompatActivity {

    ImageView imageView,imageView3,imageView4,imageView5;
    TextView textView;
    Button add,skip;
    Uri imageuri;
    String userId;
    private StorageReference Folder;
    private static final int PICK_IMAGE = 100;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    LottieAnimationView lottieAnimationView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpicture);

        Folder = FirebaseStorage.getInstance().getReference().child("Profile Images");

        imageView = findViewById(R.id.addimageView);
        imageView3 = findViewById(R.id.imageView33);
        imageView4 = findViewById(R.id.imageView44);
        imageView5 = findViewById(R.id.addimagehint);
        textView = findViewById(R.id.textView44);
        add = findViewById(R.id.addimage);
        skip = findViewById(R.id.skipimage);
        lottieAnimationView = findViewById(R.id.loadingaddpicture);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addprofileimage();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            @Override
            public void onClick(View v) {


                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                add.setClickable(false);
                skip.setClickable(false);
                imageView.setClickable(false);
                imageView3.setAlpha((float) 0.5);
                imageView4.setAlpha((float) 0.5);
                imageView5.setAlpha((float) 0.0);
                textView.setAlpha((float) 0.5);
                imageView.setAlpha((float) 0.5);
                add.setAlpha((float) 0.5);
                skip.setAlpha((float) 0.5);

                final StorageReference Image = Folder.child("image" + UUID.randomUUID().toString());
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
                            documentReference.update(Users);
                            lottieAnimationView.pauseAnimation();
                            lottieAnimationView.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                });
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
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageuri = data.getData();

            Picasso.get().load(imageuri).fit().centerCrop().into(imageView);
            add.setVisibility(View.VISIBLE);
            imageView5.setAlpha((float) 0.0);
        }
    }
}
