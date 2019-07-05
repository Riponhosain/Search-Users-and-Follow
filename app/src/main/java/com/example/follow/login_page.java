package com.example.follow;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_page extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button login;
    TextView text_signup;

    FirebaseAuth auth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextPassword = findViewById(R.id.EditTextPassword);
        login = findViewById(R.id.login);
        text_signup = findViewById(R.id.text_signup);


        progressDialog = new ProgressDialog(this);

        //  auth = FirebaseAuth.getInstance();
    }
        private void userlogin () {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Signing in User....");
            progressDialog.show();

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {

                                Toast.makeText(login_page.this, "Login failed(Chack Your Email/Password)",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });

//        text_signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(login_page.this, RegisterActivity.class));
//            }
//        });
//
//       login .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                final ProgressDialog pd = new ProgressDialog(login_page.this);
//                pd.setMessage("Please Wait");
//                pd.show();
//
//
//                String str_email = email.getText().toString();
//                String str_password = password.getText().toString();
//
//                if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)){
//                    Toast.makeText(login_page.this, "All fields are required", Toast.LENGTH_SHORT).show();
//                }else {
//                    auth.signInWithEmailAndPassword(str_email, str_password).addOnCompleteListener(login_page.this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//
//
//                            if (task.isSuccessful()){
//                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").
//                                        child(auth.getCurrentUser().getUid());
//
//                                reference.addValueEventListener(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                        pd.dismiss();
//                                        Intent intent = new Intent(login_page.this, MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
//                                        finish();
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//                            }else {
//                                pd.dismiss();
//                                Toast.makeText(login_page.this, "Authentication failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//                }
//
//            }
//        });

        }


    public void signup(View view) {
        if(view == text_signup){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    public void Login(View view) {
        if(view == login){
            userlogin();
        }
    }
}
