package com.example.book4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    LottieAnimationView lottieAnimationView;
    TextView welcomeText, signupStatement, signupLink, forgotPassword;
    EditText userName, pass, number, year, dept;
    Button login;

    int deptId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sign_up);
        constraintLayout = (ConstraintLayout) findViewById(R.id.loginContainer);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.signupGIF);
        welcomeText = (TextView) findViewById(R.id.welcomeBack);
        signupStatement = (TextView) findViewById(R.id.linkStatement);
        signupLink = (TextView) findViewById(R.id.linkButton);

        login = (Button) findViewById(R.id.loginButton);

        userName = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.password);
        number = (EditText) findViewById(R.id.number);
        year = (EditText) findViewById(R.id.year);
        dept = (EditText) findViewById(R.id.dept);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "testing......", Toast.LENGTH_SHORT).show();
                if(!userName.getText().toString().trim().isEmpty() && !pass.getText().toString().trim().isEmpty() && !number.getText().toString().trim().isEmpty() && !year.getText().toString().trim().isEmpty() && !dept.getText().toString().trim().isEmpty()){
                     otpMethod(view);
//                    RequestQueue requestQueue = Volley.newRequestQueue(SignUpActivity.this);
//                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getString(R.string.baseUrl) + "check_user?user_name=" +userName.getText().toString().trim() +"&phone_no=" +number.getText().toString().trim(), null, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.d("response", response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//
//                        }
//                    });

                }
                else {
                    Toast.makeText(SignUpActivity.this, "All fields are required",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loginMethod(View view){

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }

    public void otpMethod(View view) {
        if(!number.getText().toString().trim().isEmpty()){
            if((number.getText().toString().trim()).length() == 10){
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + number.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        SignUpActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(SignUpActivity.this,"Successfully verified!", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(SignUpActivity.this,"Verification Failed"+e.toString(), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String backEndOtp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(backEndOtp, forceResendingToken);
                                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                intent.putExtra("phoneNo", number.getText().toString());
                                intent.putExtra("backEndOtp", backEndOtp);

                                intent.putExtra("userName", userName.getText().toString().trim());
                                intent.putExtra("email", "dummyemail@gmail.com");
                                intent.putExtra("phone", number.getText().toString().trim());


                                if(dept.getText().toString().trim().toLowerCase().equals("computer")){
                                    deptId = 1;
                                }else if (dept.getText().toString().trim().toLowerCase().equals("it")){
                                    deptId = 2;
                                }
                                else if (dept.getText().toString().trim().toLowerCase().equals("civil")){
                                    deptId = 3;
                                }else if (dept.getText().toString().trim().toLowerCase().equals("mechanical")){
                                    deptId = 4;
                                }else if (dept.getText().toString().trim().toLowerCase().equals("metallurgy")){
                                    deptId = 5;
                                }else if (dept.getText().toString().trim().toLowerCase().equals("entc")){
                                    deptId = 6;
                                }else if (dept.getText().toString().trim().toLowerCase().equals("electrical")){
                                    deptId = 7;
                                }else if (dept.getText().toString().trim().toLowerCase().equals("ddgm")){
                                    deptId = 8;
                                }
                                intent.putExtra("dept", deptId);
                                intent.putExtra("password", pass.getText().toString().trim());

                                startActivity(intent);

                            }
                        }
                );
            }else {
                Toast.makeText(SignUpActivity.this,"Enter correct mobile number", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(SignUpActivity.this,"PLease enter phone number", Toast.LENGTH_LONG).show();
        }

    }
}