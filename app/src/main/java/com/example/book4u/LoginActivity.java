package com.example.book4u;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    LottieAnimationView lottieAnimationView;
    TextView welcomeText, signupStatement, signupLink, forgotPassword;
    EditText email, pass;
    Button login;

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String SHARED_USER = "local_user";
    public static final String SHARED_USERID = "local_userid";
    public static final String SHARED_DEPARTMENT = "local_depid";

    SharedPreferences sharedpreferences;
    String username, userid, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        constraintLayout = (ConstraintLayout) findViewById(R.id.loginContainer);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.lottieAnimationView);
        welcomeText = (TextView) findViewById(R.id.welcomeBack);
        signupStatement = (TextView) findViewById(R.id.linkStatement);
        signupLink = (TextView) findViewById(R.id.linkButton);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        login = (Button) findViewById(R.id.loginButton);

        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        username = sharedpreferences.getString(SHARED_USER, null);
        userid = sharedpreferences.getString(SHARED_USERID, null);
        department = sharedpreferences.getString(SHARED_DEPARTMENT, null);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().trim().isEmpty() && pass.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
                }else{
                    navigate_home(email.getText().toString(), pass.getText().toString());
                }
            }
        });
    }

    private void navigate_home(String email, String password) {

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest fetch = new JsonObjectRequest(Request.Method.GET, getString(R.string.baseUrl) + "login_function?user_name="+email+"&password="+password, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                        String id = response.getString("id");
                        String department_id = response.getString("department_id");
                        String user = response.getString("name");
                        String verfication = response.getString("v_status");

                        if (verfication.equals("true")){
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(SHARED_USER, user.toString().trim());
                            editor.putString(SHARED_USERID, id.toString().trim());
                            editor.putString(SHARED_DEPARTMENT, department_id.toString().trim());
                            editor.apply();
                            Intent intent = new Intent(getApplicationContext(), BottomTabActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Verification Pending!", Toast.LENGTH_SHORT).show();
                        }

                        Toast.makeText(getApplicationContext(), "Signed in successfully!!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Log.d("responseE", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responseError", error.toString());
                Toast.makeText(getApplicationContext(), "Failed to login!", Toast.LENGTH_SHORT).show();
            }
        });

        rq.add(fetch);
    }

    public void signUp(View view){
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }
}