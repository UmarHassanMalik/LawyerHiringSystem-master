package com.innova.lawyerhiringsystem.fragments;
/* A Fragment for Posting cases
*  This fragment wil  write 'bid/ case' to firebase realtime database
*   Role Specific - client
* */

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.innova.lawyerhiringsystem.R;
import com.innova.lawyerhiringsystem.Register;
import com.innova.lawyerhiringsystem.WelcomeScreen;
import com.innova.lawyerhiringsystem.model.Case;

public class PostJobFragment extends Fragment {

    EditText title, city, budget, statement, courtType, lawyerType;
    Button postcase;
    private FirebaseAuth mAuth;

    public PostJobFragment() {
        // Required empty public constructor
    }

    public static PostJobFragment newInstance(String param1, String param2) {
        PostJobFragment fragment = new PostJobFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_post_job, container, false);
        title = rootview.findViewById(R.id.case_title);
        city = rootview.findViewById(R.id.city);
        budget = rootview.findViewById(R.id.budget);
        statement = rootview.findViewById(R.id.describe);
        courtType = rootview.findViewById(R.id.court_type);
        lawyerType = rootview.findViewById(R.id.lawyer_type);
        // submit case
        postcase = rootview.findViewById(R.id.postcase);
        postcase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             postCase();
                title.setText("");
                city.setText("");
                budget.setText("");
                statement.setText("");
                courtType.setText("");
                lawyerType.setText("");
            }
        });

        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Any view setup should occur here.  E.g., view lookups and attaching view listeners.

    }

    public void postCase(){
        /* A method to write to database case details
        * parent node id will be userID
        * Individual case will further have nodeID as case title*/

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();

        String caseTitle, caseCity, caseBudget, caseStatment, crtType, lwrType, email, uid;
        caseTitle = title.getText().toString().trim();
        caseCity = city.getText().toString().trim();
        caseBudget = budget.getText().toString().trim();
        caseStatment = statement.getText().toString().trim();
        crtType = courtType.getText().toString().trim();
        lwrType = lawyerType.getText().toString().trim();
        email = user.getEmail();
        uid = user.getUid();
        Case clientCase = new Case(caseTitle,caseCity,caseBudget,caseStatment,crtType,lwrType,email,"","true", 0," ", uid);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("cases");
        // 'cases' node structure -> cases/userID/caseTitle
        ref.child(uid).child(caseTitle).setValue(clientCase);

        Toast.makeText(getActivity(), "Case posted Successfully. In bidding state.",
                Toast.LENGTH_LONG).show();

    }


}