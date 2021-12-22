package com.innova.lawyerhiringsystem;
/*
* Login Screen
* Principally logins both client and lawyer
* Firebase email Auth is incorporated
* Forgot Password screen is linked
* */
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.model.Lawyer;

public class Login extends AppCompatActivity {

    Button btn_login, need_help;
    EditText email,password;
    ProgressBar loading;
    LinearLayout items,helpView;
    String url;
    Boolean emailFocusState=false,passFocusState=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etxt_email);
        password = (EditText) findViewById(R.id.etxt_password);
        loading = (ProgressBar) findViewById(R.id.loading);
        items = (LinearLayout) findViewById(R.id.items);
        helpView = (LinearLayout) findViewById(R.id.help_view);

        //initially hiding the loading bar
        loading.setVisibility(View.GONE);

        need_help = (Button) findViewById(R.id.need_help);
        need_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Login.this,LoginHelpActivity.class));
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M) // for devices above marshmallow
            @Override
            public void onClick(View view) {

                if (IsConnected()) {


                    if (email.getText().toString().trim().matches("")) {
                        email.setError("Enter Email");
                        email.requestFocus();
                        return;
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
                        email.setError("Invalid Email");
                        email.requestFocus();
                        return;
                    } else if (password.getText().toString().matches("")) {
                        password.setError("Enter Password");
                        password.requestFocus();
                        return;
                    } else {

                        //send data to firebase for verification
                        if (email.isFocused() || password.isFocused()) {
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                        }
                        startLoading();
                        login(email.getText().toString(), password.getText().toString());

                    }
                }else{
                    final AppCompatDialog dialog = new AppCompatDialog(Login.this);
                    dialog.setContentView(R.layout.dialog_no_internet);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    Button back =(Button) dialog.findViewById(R.id.btn_back);
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View view) {

                            dialog.dismiss();
                            return;
                        }
                    });

                    dialog.show();
                    return;
                }

            }
        });


    }


    //disable edit text and show loading bar
    public  void startLoading(){
        items.setVisibility(View.GONE);
        btn_login.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    //enable edit text and show button
    public void showItems(){
        items.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
    }

    // checking for internet connection
    @RequiresApi(api = Build.VERSION_CODES.M)  //only for network connectivity check api level 23 is required - will not work on lower api
    public boolean IsConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {

            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // Connected to the Internet
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(Login.this,WelcomeScreen.class));
        finish();
    }

    //all login connectivity will be performed here
    public void login(String email, String password){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Singin", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(Login.this, "Login successful.",
                                    Toast.LENGTH_SHORT).show();
                            getRole(user);

//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Singin", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            showItems();
//                            updateUI(null);
                        }
                    }
                });

    }

    public void getRole(FirebaseUser user)
    {
        // this function will determine if user being logged in 'lawyer' or 'client'
        // then will be redirected to different dashboards
        String userIdentifier = user.getUid();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        ref.child(userIdentifier).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Lawyer profile = dataSnapshot.getValue(Lawyer.class);
                System.out.println(profile);
                if (profile.getProfession().equals("0"))
                {
                    //go to client dashboard if logged in user has role "Client"
                    startActivity(new Intent(Login.this, ClientDashboard.class));
                    finish();
                }
                else{
                    // go to lawyer dashboard
                    startActivity(new Intent(Login.this, LawyerDashboard.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}