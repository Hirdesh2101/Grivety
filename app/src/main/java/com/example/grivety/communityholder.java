package com.example.grivety;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class communityholder extends RecyclerView.ViewHolder {

    public TextView question;
    public TextView profilname,show1,hide,namefull2,emailfull2;
    public ImageView profileimg;
    public EditText commentbox;
    public Button addcomment;
    public RecyclerView commentview;

    public communityholder(@NonNull View itemView) {
        super(itemView);
        question = itemView.findViewById(R.id.communityquestion);
        profilname = itemView.findViewById(R.id.communityname);
        profileimg = itemView.findViewById(R.id.communityimage);
        commentbox = itemView.findViewById(R.id.communitycomment);
        addcomment = itemView.findViewById(R.id.commentbutton);
        commentview = itemView.findViewById(R.id.commentview);
        show1 = itemView.findViewById(R.id.btn_showcomments);
        hide = itemView.findViewById(R.id.btn_hidecomments);
        namefull2 = itemView.findViewById(R.id.namefull2);
        emailfull2 = itemView.findViewById(R.id.emailfull2);

    }
}
