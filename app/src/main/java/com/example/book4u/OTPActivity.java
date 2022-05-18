package com.example.book4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

public class OTPActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    LinearLayout linearLayout;
    LottieAnimationView lottieAnimationView;
    TextView textView;
    EditText digit1, digit2, digit3, digit4, digit5, digit6;
    Button verifyBtn;

    String getBackEndOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        digit1 = (EditText) findViewById(R.id.digit1);
        digit2 = (EditText) findViewById(R.id.digit2);
        digit3 = (EditText) findViewById(R.id.digit3);
        digit4 = (EditText) findViewById(R.id.digit4);
        digit5 = (EditText) findViewById(R.id.digit5);
        digit6 = (EditText) findViewById(R.id.digit6);

        verifyBtn = (Button) findViewById(R.id.verifyButton);

        textView = (TextView) findViewById(R.id.otpStatement);
        textView.setText(String.format(
                "Enter the OTP sent on +91-%s",getIntent().getStringExtra("phoneNo")
        ));

        getBackEndOtp = getIntent().getStringExtra("backEndOtp");

        numberOTPMove();
    }

    public void loginMethod(View view){
        if(!digit1.getText().toString().trim().isEmpty() && !digit2.getText().toString().trim().isEmpty() && !digit3.getText().toString().trim().isEmpty() && !digit4.getText().toString().trim().isEmpty() && !digit5.getText().toString().trim().isEmpty() && !digit6.getText().toString().trim().isEmpty()){
            String enteredOtp = digit1.getText().toString() +
                    digit2.getText().toString() +
                    digit3.getText().toString() +
                    digit4.getText().toString() +
                    digit5.getText().toString() +
                    digit6.getText().toString() ;

            if(getBackEndOtp != null){
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getBackEndOtp, enteredOtp);
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                                Toast.makeText(OTPActivity.this,"Successfully verified!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), onboardMain.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            pushUserToMongo();
                        }else{
                            Toast.makeText(OTPActivity.this,"Please enter the correct OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else{
                Toast.makeText(OTPActivity.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
            }

        }else{

        }
    }

    public void pushUserToMongo(){

        String userName, email, phone, password;
        int dept;
        userName = getIntent().getStringExtra("userName");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
        dept = getIntent().getIntExtra("dept", 0);
        password = getIntent().getStringExtra("password");

        RequestQueue requestQueue = Volley.newRequestQueue(OTPActivity.this);
        JSONObject params = new JSONObject();
        try{
            params.put("username", userName);
            params.put("email", email);
            params.put("phone_no", phone);
            params.put("department_id", dept);
            params.put("password", password);
        }catch(JSONException e){}

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getString(R.string.baseUrl) + "add_user", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(OTPActivity.this, "Signed up Successfully!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OTPActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    public void numberOTPMove(){
        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    digit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    digit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    digit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    digit5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        digit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    digit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}