package com.innova.lawyerhiringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.model.Case;

public class ViewCaseDetails extends AppCompatActivity {

    TextView title, city, budget, statement, courtType, lawyerType;
    Button applyBid, openMessages;
    String selectedCase;
    public static Case cases;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_case_details);

        title = findViewById(R.id.set_case_title);
        city = findViewById(R.id.set_case_city);
        budget = findViewById(R.id.set_case_budget);
        statement = findViewById(R.id.set_case_statement);
        courtType = findViewById(R.id.set_case_court);
        lawyerType = findViewById(R.id.set_case_lawyer);
        applyBid= findViewById(R.id.apply_bid);
        openMessages= findViewById(R.id.open_cmb);

        getIntentData();
        getCaseData();
    }

    public void getIntentData()
    {
        /*Will get which case was selected by lawyer
         * Case Title will obtained*/
        Intent intent = getIntent();
        selectedCase = intent.getStringExtra("caseTitle");
    }

    public void getCaseData()
    {
        /*Search the database
         * for selected case title
         * populate teh screen with these details*/

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("cases");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    for (DataSnapshot innerChild : child.getChildren())
                    {
                        cases = innerChild.getValue(Case.class);
                        Log.i("cases", cases.getTitle());

                        if (cases.getTitle().equals(selectedCase)){
                            title.setText(cases.getTitle());
                            city.setText(cases.getCity());
                            budget.setText(cases.getBudget());
                            statement.setText(cases.getStatement());
                            courtType.setText(cases.getCourttype());
                            lawyerType.setText(cases.getLawyertype());
                            break;
                        }

                    }
                }
                ref.removeEventListener(this);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}