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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

EditText username,fullname,editTextEmail,editTextPassword;
Button register;
TextView text_login;

    private ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

         username = findViewById(R.id.username);
        fullname = findViewById(R.id.fullname);
        editTextEmail = findViewById(R.id.EditTextEmail);
        editTextPassword = findViewById(R.id.EditTextPassword);

        register = findViewById(R.id.register);
        text_login = findViewById(R.id.text_login);





        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() !=null){
            finish();
            startActivity(new Intent(getApplicationContext(), login_page.class ));
        }

        progressDialog = new ProgressDialog(this);

text_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(RegisterActivity.this, login_page.class));
    }
});


//        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String code = editTextPassword.getText().toString().trim();
//                if (code.isEmpty() || code.length() < 8) {
//                    editTextPassword.setError("Password is Too short");
//                    editTextPassword.requestFocus();
//                    return;
//                }
//
//                registerUser();
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pd = new ProgressDialog(RegisterActivity.this);
               pd.setMessage("Please Wait");
               pd.show();


               String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = editTextEmail.getText().toString();
                String str_password = editTextPassword.getText().toString();


                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) || TextUtils.isEmpty(str_email) ||
                TextUtils.isEmpty(str_password)){
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();

                }else if (str_password.length() <8){
                    Toast.makeText(RegisterActivity.this, "password must have 8 characters", Toast.LENGTH_SHORT).show();
                }
                register(str_username,str_fullname,str_email,str_password);
            }
        });
    }


//    private void registerUser(){
//        String email = editTextEmail.getText().toString();
//        String password = editTextPassword.getText().toString();
//
//        if(TextUtils.isEmpty(email)){
//            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(password)){
//            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        progressDialog.setMessage("Registering User....");
//        progressDialog.show();
//
//
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        progressDialog.dismiss();
//                        if (task.isSuccessful()) {
//                            finish();
//                            AddData();
//
//
//
//                              HashMap<String, Object> hashMap = new HashMap<>();
//                              hashMap.put("id", "userid");
//                              hashMap.put("username", username);
//                              hashMap.put("fullname", fullname);
//                              hashMap.put("bio", "");
//                              hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/follow-9a626.appspot.com/o/boy.png?alt=media&token=683194bd-4628-4858-8ce6-f1cc5a18303f");
//
//
//                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            if (task.isSuccessful()){
//                                pd.dismiss();
//
//                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }                        }
//
//                            });
////                            startActivity(new Intent(getApplicationContext(), login_page.class ));
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Toast.makeText(RegisterActivity.this, "Authentication failed / Already Registerd.",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                });
//
//
//    }


    private void register(final String username, final String fullname, final String email, final String password){
        auth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id",userid);
                    hashMap.put("username", username.toLowerCase());
                    hashMap.put("fullname", fullname);
                    hashMap.put("bio", "");
                    hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/follow-9a626.appspot.com/o/boy.png?alt=media&token=683194bd-4628-4858-8ce6-f1cc5a18303f");


                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                pd.dismiss();

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(RegisterActivity.this, "you can't register with email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



//    private void AddData() {
//
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference().child("User").child(auth.getUid());
//
//
//
//        String name = username.getText().toString().trim();
//        String fulname = fullname.getText().toString().trim();
//
////        String phone = editTextEmail.getText().toString().trim();
////        String address = editTextPassword.getText().toString().trim();
//
//
//        Getprofile_Data User= new Getprofile_Data(name, fulname);
//        databaseReference.setValue(User);
//
//    }
//
//    public void logIn(View view) {
//        if(view == text_login){
//            startActivity(new Intent(this,login_page.class));
//
//        }
//    }
//
//    public void SignUp(View view) {
//        if(view == register){
//
//        }
  //  }
}

