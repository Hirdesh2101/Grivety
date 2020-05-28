package com.example.grivety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup_Form extends AppCompatActivity {

    EditText fullname, email;
    Spinner spinner1, spinner2;
    RadioButton button1, button2;
    Button register;
    String userId;
    TextView textView1,textView2,textView3,textView4;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    LottieAnimationView lottieAnimationView;
    EditText passwaord, cnf;
    CheckBox checkBox;
    ImageView imageView1,imageView2,imageView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__form);

        imageView1 = findViewById(R.id.imageView444);
        imageView2 = findViewById(R.id.imageView34);
        imageView3 = findViewById(R.id.imageView45);
        textView4 = findViewById(R.id.textview445);
        checkBox = findViewById(R.id.checkBoxsignup);
        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.email_signup);
        spinner1 = (Spinner) findViewById(R.id.branchspinner);
        spinner2 = (Spinner) findViewById(R.id.startyearspinner);
        button1 = (RadioButton) findViewById(R.id.radio1);
        button2 = (RadioButton) findViewById(R.id.radio2);
        register = (Button) findViewById(R.id.btn_register);
        lottieAnimationView = findViewById(R.id.loadingsignup);
        passwaord = (EditText) findViewById(R.id.signuppassword);
        cnf = (EditText) findViewById(R.id.cnfsignuppassword);
        textView1 = findViewById(R.id.branch);
        textView2 = findViewById(R.id.textView3);
        textView3 = findViewById(R.id.gendertext);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Branch, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Year, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Fullname = fullname.getText().toString();
                final String Email = email.getText().toString();
                String password = passwaord.getText().toString();
                String cnfpassword = cnf.getText().toString();
                String Gender = "";
                String checkboxtext = "";
                final String Branch = spinner1.getSelectedItem().toString();
                final String year = spinner2.getSelectedItem().toString();
                String check1 = "Select Branch";
                String check2 = "Select Year";

                if (button1.isChecked()) {

                    Gender = "Male";
                }
                if (button2.isChecked()) {

                    Gender = "Female";
                }
                if (TextUtils.isEmpty(Fullname)) {
                    Toast.makeText(Signup_Form.this, "Enter Fullname", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(Signup_Form.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Branch.equals(check1)) {
                    Toast.makeText(Signup_Form.this, "Select Branch", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (year.equals(check2)) {
                    Toast.makeText(Signup_Form.this, "Select Year", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Signup_Form.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cnfpassword)) {
                    Toast.makeText(Signup_Form.this, "Enter Confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(Gender)) {
                    Toast.makeText(Signup_Form.this, "Select Gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(Signup_Form.this, "Password should contain minimum 6 digits", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkBox.isChecked()){
                    checkboxtext = "Checked";
                }
                if (TextUtils.isEmpty(checkboxtext)){
                    Toast.makeText(Signup_Form.this, "Please tick the check box", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.equals(cnfpassword)) {

                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    textView1.setAlpha((float) 0.5);
                    textView2.setAlpha((float) 0.5);
                    textView3.setAlpha((float) 0.5);
                    textView4.setAlpha((float) 0.5);
                    imageView1.setAlpha((float) 0.5);
                    imageView2.setAlpha((float) 0.5);
                    imageView3.setAlpha((float) 0.5);
                    email.setClickable(false);
                    email.setAlpha((float) 0.5);
                    checkBox.setClickable(false);
                    checkBox.setAlpha((float) 0.5);
                    fullname.setClickable(false);
                    fullname.setAlpha((float) 0.5);
                    spinner1.setClickable(false);
                    spinner1.setAlpha((float) 0.5);
                    spinner2.setClickable(false);
                    spinner2.setAlpha((float) 0.5);
                    button1.setClickable(false);
                    button1.setAlpha((float) 0.5);
                    button2.setClickable(false);
                    button2.setAlpha((float) 0.5);
                    passwaord.setClickable(false);
                    passwaord.setAlpha((float) 0.5);
                    cnf.setClickable(false);
                    cnf.setAlpha((float) 0.5);
                    register.setClickable(false);
                    register.setAlpha((float) 0.5);


                    final String finalGender = Gender;
                    firebaseAuth.createUserWithEmailAndPassword(Email, password)
                            .addOnCompleteListener(Signup_Form.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        userId = firebaseAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = firestore.collection("Users").document(userId);
                                        Map<String, Object> Users = new HashMap<>();
                                        Users.put("Fullname", Fullname);
                                        Users.put("Gender", finalGender);
                                        Users.put("Branch",Branch);
                                        Users.put("Start Year",year);
                                        Users.put("Email",Email);
                                        documentReference.set(Users);
                                        startActivity(new Intent(getApplicationContext(), addpicture.class));
                                        finish();
                                    } else {
                                        lottieAnimationView.pauseAnimation();
                                        lottieAnimationView.setVisibility(View.INVISIBLE);
                                        textView1.setAlpha((float) 1.0);
                                        textView2.setAlpha((float) 1.0);
                                        textView3.setAlpha((float) 1.0);
                                        textView4.setAlpha((float) 1.0);
                                        imageView1.setAlpha((float) 1.0);
                                        imageView2.setAlpha((float) 1.0);
                                        imageView3.setAlpha((float) 1.0);
                                        email.setClickable(true);
                                        email.setAlpha((float) 1.0);
                                        checkBox.setClickable(true);
                                        checkBox.setAlpha((float) 1.0);
                                        fullname.setClickable(true);
                                        fullname.setAlpha((float) 1.0);
                                        spinner1.setClickable(true);
                                        spinner1.setAlpha((float) 1.0);
                                        spinner2.setClickable(true);
                                        spinner2.setAlpha((float) 1.0);
                                        button1.setClickable(true);
                                        button1.setAlpha((float) 1.0);
                                        button2.setClickable(true);
                                        button2.setAlpha((float) 1.0);
                                        passwaord.setClickable(true);
                                        passwaord.setAlpha((float) 1.0);
                                        cnf.setClickable(true);
                                        cnf.setAlpha((float) 1.0);
                                        register.setClickable(true);
                                        register.setAlpha((float) 1.0);

                                        Toast.makeText(Signup_Form.this, "Registeration failed", Toast.LENGTH_SHORT).show();
                                    }


                                }

                            });

                }
                else {
                    Toast.makeText(Signup_Form.this, "Password not Equals to confirm password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
