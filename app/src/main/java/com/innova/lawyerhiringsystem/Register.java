package com.innova.lawyerhiringsystem;
/* Register Class
* Will be use to register both Lawyer and Client
*  Contains animation and hidden fields for both roles
*  Mode: Firebase Email Auth
* */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ArrayAdapter<String> proAdapter;
    ArrayAdapter<String> cityAdapter;
    CheckBox accept;
    Animation shake;
    ProgressDialog pd;
    String url;
    LinearLayout tandc;
    Integer status;
    String status1;
    ArrayList selectedItems = new ArrayList();
//    List<String> city = new ArrayList<String>();
    CircleImageView propic;
    TextView title;
    Button register_btn;
    Spinner spnr_profession, spnr_lawyer_loc;
    ImageButton img_upload;
    LinearLayout lawyerPart;
    HashMap<String, String> enteredData = new HashMap<String, String>();
    Bitmap bitmap;
    EditText name, email, password, lawyerId, city, officeAddress, exp, phone, lawyer_id_a, lawyer_id_b, lawyerType;

    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initializing / attaching views
        tandc = (LinearLayout) findViewById(R.id.tandc);
        name = (EditText) findViewById(R.id.etxt_name);
        email = (EditText) findViewById(R.id.etxt_email);
        password = (EditText) findViewById(R.id.etxt_password);
        //lawyerId = (EditText) findViewById(R.id.etxt_lawyer_id);
        officeAddress = (EditText) findViewById(R.id.etxt_office_address);
        exp = (EditText) findViewById(R.id.etxt_exp);
        lawyer_id_a = (EditText) findViewById(R.id.etxt_lawyer_a);
        lawyer_id_b = (EditText) findViewById(R.id.etxt_lawyer_b);
        accept = (CheckBox) findViewById(R.id.accept);
        phone = (EditText) findViewById(R.id.etxt_phone);
        city = (EditText) findViewById(R.id.spnr_city);
        lawyerType = (EditText) findViewById(R.id.type_lawyer);

        shake = AnimationUtils.loadAnimation(this, R.anim.shakeanim);

        //Profile Picture
        propic = (CircleImageView) findViewById(R.id.profile_image);
        img_upload = (ImageButton) findViewById(R.id.btn_img_upload);
        img_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, PICK_IMAGE_REQUEST);
            }
        });

        //Registration Button
        register_btn = (Button) findViewById(R.id.register);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().length() < 5 || !checkname(name.getText().toString())) {

                    name.setError("Full name required");
                    name.requestFocus();
                    register_btn.startAnimation(shake);
                    return;

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {

                    email.setError("Invalid Email Address");
                    email.requestFocus();
                    register_btn.startAnimation(shake);
                    return;
                } else if (phone.getText().length() <10) {
                    phone.setError("Invalid phone number", null);
                    phone.requestFocus();
                    phone.startAnimation(shake);
                    return;
                } else if (password.getText().length() < 6) {

                    password.setError("Minimum 6 characters", null);
                    password.requestFocus();
                    register_btn.startAnimation(shake);
                    return;

                } else if (city.getText().length() < 5) {
                    city.setError("Invalid city name", null);
                    city.requestFocus();
                    register_btn.startAnimation(shake);
                    return;
                } else if (spnr_profession.getSelectedItemPosition() == 1) {

                    if (lawyer_id_a.getText().toString().matches("")) {

                        lawyer_id_a.setError("Invalid Lawyer ID");
                        lawyer_id_b.requestFocus();
                        register_btn.startAnimation(shake);
                        return;

                    } else if (lawyer_id_b.getText().toString().matches("")) {

                        lawyer_id_b.setError("Invalid Lawyer ID");
                        lawyer_id_b.requestFocus();
                        register_btn.startAnimation(shake);
                        return;

                    } else if (officeAddress.getText().length() < 10) {

                        officeAddress.setError("Invalid Address");
                        officeAddress.requestFocus();
                        register_btn.startAnimation(shake);
                        return;

                    } else if (exp.getText().toString().matches("")) {
                        exp.setError("Invalid Experience Year");
                        exp.requestFocus();
                        register_btn.startAnimation(shake);
                        return;
                    } else if (!android.text.TextUtils.isDigitsOnly(exp.getText())) {

                        exp.setError("Only Numbers are Allowed");
                        exp.requestFocus();
                        register_btn.startAnimation(shake);
                        return;
                    } else if (exp.getText().toString().length() > 2 || Integer.valueOf(exp.getText().toString()) > 30) {
                        exp.setError("Invalid years of Experience");
                        exp.requestFocus();
                        register_btn.startAnimation(shake);
                        return;
                    }
                } else if (!accept.isChecked()) {

                    Toast.makeText(getApplicationContext(), "Please accept the Terms and Conditions to continue", Toast.LENGTH_SHORT).show();
                    accept.startAnimation(shake);
                    return;

                }

                {
                    pd = new ProgressDialog(Register.this);
                    pd.setMessage("Creating Account..");
                    pd.show();

                    //adding data to the hashmap -- firebase easy push
                    enteredData.put("name", name.getText().toString().trim());
                    enteredData.put("email", email.getText().toString().trim());
                    enteredData.put("password", password.getText().toString());
                    enteredData.put("mobile", "" + phone.getText().toString());
                    enteredData.put("city", city.getText().toString().trim());
                    enteredData.put("profession", Integer.valueOf(spnr_profession.getSelectedItemPosition()).toString());
                    enteredData.put("lawyerId", spnr_lawyer_loc.getSelectedItem().toString() + "/" + lawyer_id_a.getText().toString().toUpperCase().trim() + "/" + lawyer_id_b.getText().toString());
                    enteredData.put("experience", exp.getText().toString());
                    enteredData.put("address", officeAddress.getText().toString().trim());
                    enteredData.put("lawyerType", lawyerType.getText().toString().trim());
                    enteredData.put("courtType", " ");
                    enteredData.put("user_id"," " );

                    register(enteredData);
                }
            }
        });

        lawyerPart = (LinearLayout) findViewById(R.id.lawyer_part);
        //initializing lawyer id locations
        spnr_lawyer_loc = (Spinner) findViewById(R.id.spnr_lawyer_loc);
        final List<String> loc = new ArrayList<String>();

        loc.add("KAR");
        loc.add("MYS");

        proAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, loc);
        proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_lawyer_loc.setAdapter(proAdapter);


        //initializing profession spinner
        spnr_profession = (Spinner) findViewById(R.id.spnr_profession);
        final List<String> code = new ArrayList<String>();
        code.add("Client");
        code.add("Lawyer");

        proAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, code);
        proAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnr_profession.setAdapter(proAdapter);

        lawyerPart = (LinearLayout) findViewById(R.id.lawyer_part);

        //checking user type
        spnr_profession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    lawyerPart.setVisibility(View.GONE);
                } else if (i == 1) {
                    lawyerPart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri filePath = data.getData();
                try {
                    //Getting the Bitmap from Gallery
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                    SharedPreferences.Editor profile = getSharedPreferences("profile", MODE_PRIVATE).edit();
                    profile.putString("propic", filePath.toString());
                    profile.commit();
                    //Setting the Bitmap to ImageView
                    propic.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    public boolean checkname(String id) {
        Pattern p = Pattern.compile("[a-zA-Z\\s]*");
        Matcher m = p.matcher(id);
        return m.matches();
    }

    public void register(HashMap<String, String> userData) {
        // Registration Logic will be added here


        String email = userData.get("email");
        String password = userData.get("password");
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FirebaseAuth", "createUserWithEmail:success");

                            FirebaseUser user= mAuth.getCurrentUser();
                            //pushing profile to firebase database
                            pushProfile(userData, user.getUid());

                            Toast.makeText(Register.this, "Account Created.",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,WelcomeScreen.class));
                            finish();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FirebaseAuth", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });

    }

    public void pushProfile(HashMap<String, String> userData, String uid) {
        //the function writes profile data to firebase database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");
        ref.child(uid).setValue(userData);

    }
}


