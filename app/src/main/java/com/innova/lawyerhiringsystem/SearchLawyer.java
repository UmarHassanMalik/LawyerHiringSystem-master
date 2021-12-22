package com.innova.lawyerhiringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.adapter.ViewQuotations;
import com.innova.lawyerhiringsystem.model.Case;
import com.innova.lawyerhiringsystem.model.Lawyer;
import com.innova.lawyerhiringsystem.model.Quotation;

import java.util.ArrayList;

public class SearchLawyer extends AppCompatActivity {

    ListView availbleLaweyersList;
    Button searchBtn;
    EditText searchLawyerType;
    LinearLayout profile;

    String type;
    Lawyer lawyer;
    public static ArrayList<Lawyer> lawyers; // will contain complete lawyer objects with all attributes
    public static String[] lawyerIDs; // will store only the userIds of all lawyers retrieved
    public int lawyerCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lawyer);

        availbleLaweyersList =findViewById(R.id.available_lawyers);
        searchBtn =findViewById(R.id.search_lawyerType_btn);
        searchLawyerType =findViewById(R.id.search_lawyerType);
        profile= findViewById(R.id.displayProfile);
        profile.setVisibility(View.GONE);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // event listener for search button
                // logic-> get entered case title - search quotation for that case title - populate list view with details

                type = searchLawyerType.getText().toString();
                lawyers = new ArrayList<Lawyer>();

                // now query DB for quotation matching entered title
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("users");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                                lawyer = child.getValue(Lawyer.class);
                                if (lawyer.getProfession().equals("1")) {
                                    if (lawyer.getLawyerType().equals(type)) {
                                        // we have found the instance of matching lawyer type
                                        Log.i("matchingLawyer", lawyer.getName());
                                        lawyers.add(lawyer);
                                    }
                                }
                        }

                        // extracting names of all matching lawyers
                        lawyerIDs = new String[lawyers.size()];
                        for (int counter = 0; counter < lawyers.size(); counter++) {
                            lawyerIDs[counter] = lawyers.get(counter).getName();
                            lawyerCounter++;
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(SearchLawyer.this,
                                R.layout.available_case_row, lawyerIDs);
                        availbleLaweyersList.setAdapter(adapter);


                        availbleLaweyersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                // getting which item was clicked
                                String itemClicked = (String) parent.getAdapter().getItem(position);
                                Log.i("selectedType", itemClicked);
//                                confirmHire(itemClicked);
                                Lawyer selectedLawyer = lawyers.get(position);
                                dispplayProfile(selectedLawyer);

                            }
                        });


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }
        });

    }

    public void dispplayProfile(Lawyer Profile)
    {
        TextView lName = findViewById(R.id.sprofile_lawyername);
        TextView lEmail =findViewById(R.id.sprofile_lawyeremail);
        TextView lPhone =findViewById(R.id.sprofile_lawyermobile);
        TextView lCity =findViewById(R.id.sprofile_lawyercity);
        TextView lExperience = findViewById(R.id.sprofile_lawyerexperience);
        TextView lId =findViewById(R.id.sprofile_lawyerid);
        TextView lLocation= findViewById(R.id.sprofile_lawyeraddress);

        lName.setText(String.valueOf(Profile.getName()));
        lEmail.setText(String.valueOf(Profile.getEmail()));
        lPhone.setText(String.valueOf(Profile.getMobile()));
        lCity.setText(String.valueOf(Profile.getCity()));
        lExperience.setText(String.valueOf(Profile.getExperience()));
        lId.setText(String.valueOf(Profile.getLawyerId()));
        lLocation.setText(String.valueOf(Profile.getAddress()));

        profile.setVisibility(View.VISIBLE);
    }
}