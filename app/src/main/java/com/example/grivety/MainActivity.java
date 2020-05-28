package com.example.grivety;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    TextView news,community,senior,books;
    ViewPager viewPager;
    PagerViewAdapter pagerViewAdapter;
    NavigationView navigationView;
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    String User;
    private long backpressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        news = (TextView)findViewById(R.id.news_button);
        community = (TextView)findViewById(R.id.community_button);
        senior = (TextView)findViewById(R.id.seniors_button);
        books = (TextView)findViewById(R.id.books_button);
        navigationView =(NavigationView)findViewById(R.id.navigationtab) ;


        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        viewPager = (ViewPager)findViewById(R.id.fragmentcontainer);

        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());

        viewPager.setAdapter(pagerViewAdapter);

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });

        senior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
            }
        });

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawerlayout);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this , drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_format_list_24dp);

        navigationView.setNavigationItemSelectedListener(this);


        User = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("Users").document(User);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){}
                        String name3 = documentSnapshot.getString("Fullname");
                        String url = documentSnapshot.getString("Image");

                        View hview = navigationView.getHeaderView(0);
                        TextView nav_user = (TextView)hview.findViewById(R.id.headername);
                        ImageView imageView = (ImageView)hview.findViewById(R.id.drawerimage);
                        Picasso.get().load(url).into(imageView);
                        nav_user.setText(name3);

                    }
                });




        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                onChangeTab(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void onChangeTab(int position) {

        if (position == 0)
        {
            news.setTextSize(22);

            community.setTextSize(20);

            senior.setTextSize(20);

            books.setTextSize(20);
        }
        if (position == 1)
        {
            news.setTextSize(20);

            community.setTextSize(22);

            senior.setTextSize(20);

            books.setTextSize(20);
        }
        if (position == 2)
        {
            news.setTextSize(20);

            community.setTextSize(20);

            senior.setTextSize(22);

            books.setTextSize(20);
        }
        if (position == 3)
        {
            news.setTextSize(20);

            community.setTextSize(20);

            senior.setTextSize(20);

            books.setTextSize(22);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.signout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login_Form.class));
                finish();
                break;

            case R.id.feedback:
                final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                View v = getLayoutInflater().inflate(R.layout.feedback_dailog,null);
                Button cancel = (Button)v.findViewById(R.id.cancel);
                Button ok = (Button)v.findViewById(R.id.ok);
                final RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

                alert.setView(v);

                final AlertDialog alertDialog = alert.create();
                alertDialog.setCanceledOnTouchOutside(true);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                Float rate = rating;

                                DocumentReference documentReference = firestore.collection("Feedback").document(User);
                                Map<String, Object> Users = new HashMap<>();
                                Users.put("Rating", rate);
                                documentReference.set(Users);
                            }
                        });
                        Toast.makeText(getBaseContext(),String.valueOf(ratingBar.getProgress()),Toast.LENGTH_SHORT);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                break;

            case R.id.editprofile:
                startActivity(new Intent(getApplicationContext(),editprofile.class));
                break;

            case R.id.contact:
                final AlertDialog.Builder alert2 = new AlertDialog.Builder(MainActivity.this);
                View v1 = getLayoutInflater().inflate(R.layout.contactus,null);
                Button ok2 = (Button)v1.findViewById(R.id.ok2);

                alert2.setView(v1);

                final AlertDialog alertDialog2 = alert2.create();
                alertDialog2.setCanceledOnTouchOutside(true);

                ok2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Toast.makeText(MainActivity.this,"Thank You",Toast.LENGTH_SHORT);
                        alertDialog2.dismiss();
                    }
                });
                alertDialog2.show();
                break;

                case R.id.profile:
                    showDialog();
                    break;

        }
        return true;
    }

    private void showDialog() {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.profile);
        dialog.setCanceledOnTouchOutside(true);

        Button ok3 = dialog.findViewById(R.id.ok3);
        final TextView branch = dialog.findViewById(R.id.profilebranch);
        final TextView profilename = dialog.findViewById(R.id.profilename);
        final TextView profileyear = dialog.findViewById(R.id.profileyear);
        final TextView profileemail = dialog.findViewById(R.id.profileemail);
        final ImageView profileimage = dialog.findViewById(R.id.profileimage);
        User = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("Users").document(User);
        documentReference.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){}
                        String name2 = documentSnapshot.getString("Fullname");
                        String branch2 = documentSnapshot.getString("Branch");
                        String year2 = documentSnapshot.getString("Start Year");
                        String email = documentSnapshot.getString("Email");
                        String url = documentSnapshot.getString("Image");
                        profilename.setText(name2);
                        branch.setText("Branch: "+branch2);
                        profileyear.setText("Year: "+year2+" year");
                        profileemail.setText(email);
                        Picasso.get().load(url).fit().centerCrop().into(profileimage);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        ok3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Thank You",Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onBackPressed() {
        if (backpressed + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }else {
            Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();
        }
        backpressed = System.currentTimeMillis();
    }
}
