package com.sgfootcal.android.footcal.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sgfjcrmpr.android.foot_cal.R;


/**
 * Created by serdar on 26.8.2017.
 */

public class ProfileScreen extends AppCompatActivity {


    private TextView textViewEmail,editTextPassword;
    private ImageView backToMainAcitivity;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        textViewEmail = (TextView) findViewById(R.id.email);

        backToMainAcitivity = (ImageView) findViewById(R.id.backToMainAcitivity);

        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(ProfileScreen.this,LoginScreen.class));
        }
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseAuth.getCurrentUser() != null) {
            textViewEmail.setText(firebaseUser.getEmail());

             }

        backToMainAcitivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileScreen.this,MainActivity.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileScreen.this,MainActivity.class));
    }

private void userLogin(){
    String  email = textViewEmail.getText().toString().trim();
    String  password = editTextPassword.getText().toString().trim();


    if (TextUtils.isEmpty(email)){
        Toast.makeText(this,"Email Giriniz",Toast.LENGTH_SHORT).show();

    }
    if (TextUtils.isEmpty(password)){
        Toast.makeText(this,"Şifre Giriniz",Toast.LENGTH_SHORT).show();
    }



    progressDialog.setMessage("Registering User");
    progressDialog.show();

    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                startActivity(new Intent(ProfileScreen.this,MainActivity.class));
                Toast.makeText(ProfileScreen.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();

            }
            else {
                Toast.makeText(ProfileScreen.this,"Registered Failed",Toast.LENGTH_SHORT).show();
                progressDialog.cancel();

            }
        }
    });

}


}
