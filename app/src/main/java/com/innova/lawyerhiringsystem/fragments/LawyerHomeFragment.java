package com.innova.lawyerhiringsystem.fragments;

/*This is fragment of 'Home' option [Lawyer Role Only]
 * Will show the lawyer ongoing and previous cases and provide options assosiated options
 * */
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.R;
import com.innova.lawyerhiringsystem.model.Case;
import com.innova.lawyerhiringsystem.model.Quotation;

public class LawyerHomeFragment extends Fragment {

    String openCases, allCases;
    private FirebaseAuth mAuth;
    TextView previousCases, currentCases;
    Quotation quotation;

    public LawyerHomeFragment() {
        // Required empty public constructor
    }

    public static LawyerHomeFragment newInstance(String param1, String param2) {
        LawyerHomeFragment fragment = new LawyerHomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_lawyer_home, container, false);

        previousCases = rootView.findViewById(R.id.content_lawyer_case_history);
        currentCases = rootView.findViewById(R.id.content_lawyer_current_case);

        Typeface face = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/segoeui.ttf");
        previousCases.setTypeface(face);
        currentCases.setTypeface(face);
        loadCases();

        return rootView;
    }

    public void loadCases(){
        /* Database read operation
         *  for cases posted*/

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user= mAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("quotations").child(user.getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                openCases = ""; // clearing strings for recurring read -- attached callback
                allCases = "";
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    quotation = child.getValue(Quotation.class);
                    Log.i("quote", quotation.getCaseTitle());
                    allCases += "☞  " + quotation.getCaseTitle() + "\n";

                    // only if the case status equal OPEN then it will be displayed as currently active case
                    if (quotation.getStatus().equals("HIRED")){
                        openCases += "☞  " +  quotation.getCaseTitle() + "\n";
                    }
                }

                previousCases.setText(allCases);
                currentCases.setText(openCases);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }
}