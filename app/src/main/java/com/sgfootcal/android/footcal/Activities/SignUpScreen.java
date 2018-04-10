package com.sgfootcal.android.footcal.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sgfjcrmpr.android.foot_cal.R;


/**
 * Created by serdar on 26.8.2017.
 */

public class SignUpScreen extends AppCompatActivity {


    private EditText editTextEmail,editTextPassword,editTextPassword2;
    private Button buttonRegister,buttonAlreadyRegistered;
    private ImageView signupbacktologin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextPassword2 = (EditText) findViewById(R.id.password2);
        buttonRegister = (Button) findViewById(R.id.buttonsignin);
        buttonAlreadyRegistered = (Button) findViewById(R.id.buttonsignup);
        signupbacktologin = (ImageView) findViewById(R.id.signupbacktologin);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        signupbacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(editTextEmail.getText().toString()) && TextUtils.isEmpty(editTextPassword.getText().toString())) {

                    startActivity(new Intent(SignUpScreen.this, LoginScreen.class));

                } else {
                    alertDialog();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {


        if (TextUtils.isEmpty(editTextEmail.getText().toString()) && TextUtils.isEmpty(editTextPassword.getText().toString())) {

            startActivity(new Intent(SignUpScreen.this, LoginScreen.class));

        } else {
            alertDialog();
        }

    }

private void registerUser(){
    String  email = editTextEmail.getText().toString().trim();
        String  password = editTextPassword.getText().toString().trim();
            String  password2 = editTextPassword2.getText().toString().trim();


    if (TextUtils.isEmpty(email)){
        Toast.makeText(this,"Email Giriniz!",Toast.LENGTH_SHORT).show();

    }
    else  if (TextUtils.isEmpty(password)){
        Toast.makeText(this,"Şifre Giriniz!",Toast.LENGTH_SHORT).show();
    }
    else  if (TextUtils.isEmpty(password2)){
        Toast.makeText(this,"Şifre-Tekrar Giriniz!",Toast.LENGTH_SHORT).show();
    }
    else  if (!password.equals(password2)){
        Toast.makeText(this,"Şifreler aynı olmalı!",Toast.LENGTH_SHORT).show();
    }
    else{

        progressDialog.setMessage("Kullanıcı Kaydediliyor...");
        progressDialog.show();



        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Toast.makeText(SignUpScreen.this,"Kullanıcı Kaydedildi",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();
                    startActivity(new Intent(SignUpScreen.this,LoginScreen.class));

                }
                else {
                    Toast.makeText(SignUpScreen.this,"Bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();

                }
            }
        });
    }




}
    public void alertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpScreen.this);

        builder.setTitle("KAYIT EKRANI");
        builder.setMessage("Kayıt yapılamayacak. Yine de çıkmak istiyor musunuz?");
        builder.setIcon(R.drawable.left);

        builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
                dialog.dismiss();


            }
        });

        builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(),LoginScreen.class));
            }
        });
        builder.setNeutralButton("OYLA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


}
