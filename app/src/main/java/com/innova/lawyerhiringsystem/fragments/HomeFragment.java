package com.innova.lawyerhiringsystem.fragments;

/*This is fragment of 'Home' option
 * Will show the user current, previous cases and other stats
 * */
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.PlaceBid;
import com.innova.lawyerhiringsystem.R;
import com.innova.lawyerhiringsystem.ViewCaseDetails;
import com.innova.lawyerhiringsystem.model.Article;
import com.innova.lawyerhiringsystem.model.Case;
import com.innova.lawyerhiringsystem.model.Lawyer;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    String openCases, allCases;
    ArrayList<Case> userCases;
    ArrayList<String> userCasesTitle;
    private FirebaseAuth mAuth;
    TextView  currentCases;
    ListView previousCases;
    Case cases;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home, container, false);
        userCases = new ArrayList<Case>();
        userCasesTitle = new ArrayList<String>();

        previousCases = rootview.findViewById(R.id.content_case_history);
        previousCases.setDivider(null);

        currentCases = rootview.findViewById(R.id.content_current_case);
        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/segoeui.ttf");
        currentCases.setTypeface(face);
        loadCases();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                userCasesTitle);

        previousCases.setAdapter(arrayAdapter);

        previousCases.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                // getting which item was clicked
                String itemClicked = (String) parent.getAdapter().getItem(position);
                Log.i("selectedType", itemClicked);

                Intent intent = new Intent(getActivity(), ViewCaseDetails.class);
                intent.putExtra("caseTitle", itemClicked);
                startActivity(intent);



            }
        });
        return rootview;
    }

    public void loadCases(){
        /* Database read operation
        *  for cases posted*/

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("cases").child(user.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                openCases = ""; // clearing strings for recurring read -- attached callback
                allCases = "";
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    cases = child.getValue(Case.class);
//                    Log.i("cases", cases.getTitle());
//                    allCases += "☞  " + cases.getTitle()+ "\n";

                    userCasesTitle.add(cases.getTitle());

                    // only if the case status isOpen then it will be displayed as current case
                    if (cases.isIsopen() == "true"){
                        openCases += "☞  " +  cases.getTitle() + "\n";
                    }
                }

//                previousCases.setText(allCases);

                currentCases.setText(openCases);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }


}