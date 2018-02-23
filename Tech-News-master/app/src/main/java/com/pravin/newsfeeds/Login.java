package com.pravin.newsfeeds;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText email_, password_;
    private Button auth_;
    private FirebaseAuth mAuth_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Link Id to it's Object
        email_ = (EditText) findViewById(R.id.email);
        password_ = (EditText) findViewById(R.id.password);
        auth_ = (Button) findViewById(R.id.auth);
        mAuth_ = FirebaseAuth.getInstance();

        auth_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Email and Password from respective EditText
                String email = email_.getText().toString().trim();
                String password = password_.getText().toString().trim();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    //Login_To_Your_Firebase_DataBase
                    mAuth_.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                //Switch_Activity_To_AddNews
                                startActivity(new Intent(Login.this, AddNews.class));
                                Login.this.finish();

                            } else {

                                //On_Task_UnSuccessful
                                Toast.makeText(Login.this, "Email or Password incorrect", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                } else {

                    //If Login Id and Password is Empty
                    Toast.makeText(Login.this, "Please Enter Fields Correctly ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
