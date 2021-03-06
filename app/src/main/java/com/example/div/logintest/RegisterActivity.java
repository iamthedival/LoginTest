package com.example.div.logintest;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //variable declarations
        final EditText etName = (EditText)findViewById(R.id.etName);
        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final EditText etEmail = (EditText)findViewById(R.id.etEmail);
        final EditText etPassword = (EditText)findViewById(R.id.etPassword);
        final Button btnRegister = (Button) findViewById(R.id.btnRegister);
        final ProgressBar proRegister = (ProgressBar) findViewById(R.id.proRegister);

            btnRegister.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    //more variable declarations
                    final String name = etName.getText().toString();
                    final String username = etUsername.getText().toString();
                    final String email = etEmail.getText().toString();
                    final String password = etPassword.getText().toString();

                    proRegister.setVisibility(View.VISIBLE); //This app needs more animation. That way it makes users feel like something is being done

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                           try {
                               JSONObject jsonResponse = new JSONObject(response);
                               boolean success = jsonResponse.getBoolean("success");
                                //Checks to see if registration is successful or not
                               if (success) {
                                   Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                   RegisterActivity.this.startActivity(intent);
                               }else{
                                   AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                   builder.setMessage("Register Failed")
                                           .setNegativeButton("Retry",null)
                                           .create()
                                           .show();
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                        }
                    };
                    RegisterRequest registerRequest = new RegisterRequest(name, username, email, password,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            });
        }
    }

