package com.example.grivety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login_Form extends AppCompatActivity {

    EditText email,password;
    Button login;
    TextView register,textView1,textView2,forgotpass;
    private FirebaseAuth firebaseAuth;
    LottieAnimationView lottieAnimationView;
    ImageView imageView1,imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__form);

        email = (EditText)findViewById(R.id.email_login);
        password = (EditText)findViewById(R.id.loginpassword);
        login = (Button)findViewById(R.id.login);
        register = findViewById(R.id.registeruser);
        lottieAnimationView = findViewById(R.id.loadinglogin);
        imageView1 = findViewById(R.id.imageView77);
        imageView2 = findViewById(R.id.imageView32);
        textView1 = findViewById(R.id.welcometogrivity);
        textView2 = findViewById(R.id.welcomelogin);
        forgotpass = findViewById(R.id.forgotpassword);
        firebaseAuth = FirebaseAuth.getInstance();


        if(firebaseAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendailog();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = email.getText().toString();
                String Password = password.getText().toString();

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(Login_Form.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Login_Form.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                lottieAnimationView.setVisibility(View.VISIBLE);
                lottieAnimationView.playAnimation();
                email.setAlpha((float) 0.5);
                imageView1.setAlpha((float) 0.5);
                imageView2.setAlpha((float) 0.5);
                textView2.setAlpha((float) 0.5);
                textView1.setAlpha((float) 0.5);
                password.setAlpha((float) 0.5);
                email.setClickable(false);
                register.setAlpha((float) 0.5);
                register.setClickable(false);
                password.setClickable(false);
                login.setClickable(false);
                login.setAlpha((float) 0.5);
                forgotpass.setClickable(false);
                forgotpass.setAlpha((float) 0.5);

                firebaseAuth.signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(Login_Form.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    finish();

                                } else {
                                    lottieAnimationView.pauseAnimation();
                                    lottieAnimationView.setVisibility(View.INVISIBLE);
                                    email.setAlpha((float) 1.0);
                                    imageView1.setAlpha((float) 1.0);
                                    imageView2.setAlpha((float) 1.0);
                                    textView2.setAlpha((float) 1.0);
                                    textView1.setAlpha((float) 1.0);
                                    password.setAlpha((float) 1.0);
                                    email.setClickable(true);
                                    register.setAlpha((float) 1.0);
                                    register.setClickable(true);
                                    password.setClickable(true);
                                    login.setClickable(true);
                                    login.setAlpha((float) 1.0);
                                    forgotpass.setClickable(true);
                                    forgotpass.setAlpha((float) 1.0);
                                    Toast.makeText(Login_Form.this,"Login failed",Toast.LENGTH_SHORT).show();
                                }


                            }
                        });


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Signup_Form.class));
                finish();
            }
        });

    }

    private void opendailog() {
        final Dialog dialog = new Dialog(Login_Form.this);
        dialog.setContentView(R.layout.forgotpass);
        dialog.setCanceledOnTouchOutside(false);

        final EditText emailforgot = dialog.findViewById(R.id.forgetemail);
        Button cancelforgot = dialog.findViewById(R.id.forgotcancel);
        Button okforgot = dialog.findViewById(R.id.forgotok);

        cancelforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = emailforgot.getText().toString();
                if (TextUtils.isEmpty(s)){
                    Toast.makeText(Login_Form.this,"Enter an email",Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(s);
                    Toast.makeText(Login_Form.this,"Email Send to Your Email adress",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

}
