package com.innova.lawyerhiringsystem.fragments;
/*Role Specific Profile fragment
* Different layout files will be inflated depending upon user role
* The fragment will enable the user to view their profile data
* entered at signup.
*
* UserID [uid] is used as node key to retrieve data
* */
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.innova.lawyerhiringsystem.ClientDashboard;
import com.innova.lawyerhiringsystem.GiveFeedback;
import com.innova.lawyerhiringsystem.LawyerDashboard;
import com.innova.lawyerhiringsystem.Login;
import com.innova.lawyerhiringsystem.R;
import com.innova.lawyerhiringsystem.SearchLawyer;
import com.innova.lawyerhiringsystem.ViewArticle;
import com.innova.lawyerhiringsystem.WelcomeScreen;
import com.innova.lawyerhiringsystem.model.Lawyer;

public class ProfileFragment extends Fragment {

    public static boolean isLawyer;
    public  Lawyer profile = new Lawyer();
    public  static Lawyer cProfile = new Lawyer();

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        getProfile();
        View rootView = null;
        
        if (isLawyer){ // logged in user has role "lawyer"
            rootView = inflater.inflate(R.layout.fragment_lawyer_profile, container, false);

            // populating UI fields for lawyer
            TextView lName = rootView.findViewById(R.id.profile_lawyername);
            TextView lEmail = rootView.findViewById(R.id.profile_lawyeremail);
            TextView lPhone = rootView.findViewById(R.id.profile_lawyermobile);
            TextView lCity = rootView.findViewById(R.id.profile_lawyercity);
            TextView lExperience = rootView.findViewById(R.id.profile_lawyerexperience);
            TextView lId = rootView.findViewById(R.id.profile_lawyerid);
            TextView lLocation= rootView.findViewById(R.id.profile_lawyeraddress);

            lName.setText(String.valueOf(cProfile.getName()));
            lEmail.setText(String.valueOf(cProfile.getEmail()));
            lPhone.setText(String.valueOf(cProfile.getMobile()));
            lCity.setText(String.valueOf(cProfile.getCity()));
            lExperience.setText(String.valueOf(cProfile.getExperience()));
            lId.setText(String.valueOf(cProfile.getLawyerId()));
            lLocation.setText(String.valueOf(cProfile.getAddress()));

            // logout button inflation for lawyer
            Button button = (Button) rootView.findViewById(R.id.lawyer_logout);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), WelcomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            });

            // feedback button inflation for Lawyer
            Button feedback = (Button) rootView.findViewById(R.id.lawyer_feedback);
            feedback.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), GiveFeedback.class));
                }
            });

            // Articles button inflation for Lawyer
            Button article = (Button) rootView.findViewById(R.id.articles);
            article.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ViewArticle.class));
                }
            });


        }
        else{ // logged in user has role "Client"
            rootView = inflater.inflate(R.layout.fragment_profile, container, false);

            // populating UI fields for client
            TextView cName = rootView.findViewById(R.id.profile_name);
            TextView cEmail = rootView.findViewById(R.id.profile_email);
            TextView cPhone = rootView.findViewById(R.id.profile_mobile);
            TextView cCity = rootView.findViewById(R.id.profile_city);

            cName.setText(String.valueOf(cProfile.getName()));
            cEmail.setText(String.valueOf(cProfile.getEmail()));
            cPhone.setText(String.valueOf(cProfile.getMobile()));
            cCity.setText(String.valueOf(cProfile.getCity()));

            // logout button inflation
            Button button = (Button) rootView.findViewById(R.id.client_logout);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getActivity(), WelcomeScreen.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            });

            // feedback button inflation
            Button feedback = (Button) rootView.findViewById(R.id.client_feedback);
            feedback.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), GiveFeedback.class));
                }
            });

            // lawyer search button inflation
            Button search = (Button) rootView.findViewById(R.id.search_lawyer);
            search.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), SearchLawyer.class));
                }
            });
        }
        return  rootView;

    }

    public void getProfile()
    {
        // Establish currently logged in user
        // retrieve its profile data using [uID] -> saves it in profile object
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        // unattached call
        ref.child(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    profile = task.getResult().getValue(Lawyer.class);

                    // determining user role {Client/Lawyer}
                    isLawyer = profile.getProfession().equals("1");
                    cProfile = profile;
                }
            }
        });
    }

    public void logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), WelcomeScreen.class));
    }

}