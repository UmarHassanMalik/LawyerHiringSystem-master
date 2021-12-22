package com.innova.lawyerhiringsystem.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.CMB;
import com.innova.lawyerhiringsystem.PlaceBid;
import com.innova.lawyerhiringsystem.R;
import com.innova.lawyerhiringsystem.WelcomeScreen;
import com.innova.lawyerhiringsystem.adapter.ViewQuotations;
import com.innova.lawyerhiringsystem.model.Case;
import com.innova.lawyerhiringsystem.model.Quotation;

import java.util.ArrayList;

public class QuotationsFragment extends Fragment {

    EditText query;
    String title;
    public ArrayList<Quotation> quotations;
    Quotation quotation;
    ListView listView;

    public QuotationsFragment() {
        // Required empty public constructor
    }

    public static QuotationsFragment newInstance(String param1, String param2) {
        QuotationsFragment fragment = new QuotationsFragment();
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
        View rootview =  inflater.inflate(R.layout.fragment_quotations, container, false);

        Button button = (Button) rootview.findViewById(R.id.search_case_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { // event listener for search button
                // logic-> get entered case title - search quotation for that case title - populate list view with details
                query = rootview.findViewById(R.id.search_case);
                title = query.getText().toString();
                quotations = new ArrayList<Quotation>();

                // now query DB for quotation matching entered title
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("quotations");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            for (DataSnapshot innerChild : child.getChildren())
                            {
                                quotation = innerChild.getValue(Quotation.class);

                                if (quotation.getCaseTitle().equals(title)){
                                    // we have found the instance of quotation for case
                                    Log.i("casequote", quotation.getLawyerid());
                                    quotations.add(quotation);

                                }
                            }
                        }
                        // Create the adapter to convert the array to views
                        ViewQuotations adapter = new ViewQuotations(getActivity(), quotations);
                        // Attach the adapter to a ListView
                        listView =  rootview.findViewById(R.id.view_quotations);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {
                                // getting which item was clicked
                                Quotation itemClicked = (Quotation) parent.getAdapter().getItem(position);
//                                Log.i("selectedbid", itemClicked.getCaseTitle());
                                confirmHire(itemClicked);
                                adapter.clear();
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

        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Any view setup should occur here.  E.g., view lookups and attaching view listeners.

    }

    public void confirmHire(Quotation quote)
    {

        if (quote.getStatus().equals("HIRED")){
            Toast.makeText(getActivity(),"Lawyer Already Hired!",Toast.LENGTH_LONG).show();
            startActivity(new Intent(getActivity(), CMB.class).putExtra("case", quote.getCaseTitle()));
            return;
        }

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
//set icon
                .setIcon(android.R.drawable.ic_dialog_alert)
//set title
                .setTitle("Confirm Lawyer")
//set message
                .setMessage("Are you sure you want to hire this Lawyer?")
//set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {



                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        quote.setStatus("HIRED");

                        // update hiring status in DB
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference writeQuotation = database.getReference("quotations");
                        writeQuotation.child(quote.getLawyerid()).child(quote.getCaseTitle()).setValue(quote);

                        Toast.makeText(getActivity(),"You have hired " + quote.getName(),Toast.LENGTH_LONG).show();

                        // After Hiring divert to Messaging board
                        startActivity(new Intent(getActivity(), CMB.class).putExtra("casetitle", quote.getCaseTitle()));



                    }
                })
//set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(getActivity(),"Selection Cancelled!",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }
}