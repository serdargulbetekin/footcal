package com.sgfootcal.android.footcal.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
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

public class LoginScreen extends AppCompatActivity {


    private EditText editTextEmail,editTextPassword;
    private Button buttonsignin,buttonsignup,forgotpassword;
    private ImageView loginleft,loginright;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private View viewAssist;
    private AlertDialog.Builder builderGoalAssisst;
    private AlertDialog dialogGoalAssists;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_screen);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity( new Intent(LoginScreen.this,MainActivity.class));

        }


        progressDialog = new ProgressDialog(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonsignin = (Button) findViewById(R.id.buttonsignin);
        buttonsignup = (Button) findViewById(R.id.buttonsignup);
        forgotpassword =  ( Button) findViewById(R.id.forgotpassword);
        loginleft = (ImageView) findViewById(R.id.loginleft);
        loginright = (ImageView) findViewById(R.id.loginright);

        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userLogin();
            }
        });

        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this,SignUpScreen.class));
            }
        });

        loginleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        loginright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this,SignUpScreen.class));
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });



    }

    public void alertDialog(){

        viewAssist = LayoutInflater.from(this).inflate(R.layout.dialog_forgot_password, null); // no parent

        builderGoalAssisst = new AlertDialog.Builder(this);

        dialogGoalAssists = builderGoalAssisst.create();



        ImageView btnCancel = (ImageView) viewAssist.findViewById(R.id.btnCancel);
        final EditText forgotemail = (EditText) viewAssist.findViewById(R.id.forgotemail);
        final Button buttonchangepw = (Button) viewAssist.findViewById(R.id.buttonchangepw);

        buttonchangepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (forgotemail.getText().toString().trim().equals("")){
                    Toast.makeText(LoginScreen.this,"E-mail adresinizi giriniz",Toast.LENGTH_SHORT).show();

                }
                else{
                    progressDialog.setMessage("E-mail kontrol ediliyor.");
                    progressDialog.show();

                    firebaseAuth.sendPasswordResetEmail(forgotemail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(LoginScreen.this,"Mail Adresinizi kontrol ediniz!",Toast.LENGTH_SHORT).show();
                                dialogGoalAssists.dismiss();
                                progressDialog.dismiss();

                            }
                            else{
                                Toast.makeText(LoginScreen.this,"Hata oluştu!",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

                }
            }
        });




        dialogGoalAssists.setTitle("Şifre Sıfırlama");
        dialogGoalAssists.setIcon(R.drawable.person);
        dialogGoalAssists.requestWindowFeature(Window.FEATURE_RIGHT_ICON);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogGoalAssists.dismiss();
            }
        });

        dialogGoalAssists.setView(viewAssist);
        dialogGoalAssists.show();
    }



    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

private void userLogin(){
    String  email = editTextEmail.getText().toString().trim();
    String  password = editTextPassword.getText().toString().trim();


    if (TextUtils.isEmpty(email)){
        Toast.makeText(this,"Email Giriniz",Toast.LENGTH_SHORT).show();

    }
    else if (TextUtils.isEmpty(password)){
        Toast.makeText(this,"Şifre Giriniz",Toast.LENGTH_SHORT).show();
    }
    else if(password.length() !=6){
        Toast.makeText(this,"Şifre 6 haneli olmalıdır.", Toast.LENGTH_SHORT).show();
    }
    else{
        progressDialog.setMessage("Giriş Yapılıyor");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginScreen.this,MainActivity.class));
                    Toast.makeText(LoginScreen.this,"Giriş Yapıldı.",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
                else {
                    Toast.makeText(LoginScreen.this,"Bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }
        });
    }



}


}
