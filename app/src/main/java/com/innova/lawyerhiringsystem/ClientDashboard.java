package com.innova.lawyerhiringsystem;
/*
* This is Dashboard specific to client
* it will be first screen presented to client after login
* contains navigation to all the role specific function form bottom navigation bar
* Posting a job, checking the quotations, viewing profile, showing previous and current case stats
* */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.innova.lawyerhiringsystem.fragments.HomeFragment;
import com.innova.lawyerhiringsystem.fragments.PostJobFragment;
import com.innova.lawyerhiringsystem.fragments.ProfileFragment;
import com.innova.lawyerhiringsystem.fragments.QuotationsFragment;
import com.innova.lawyerhiringsystem.helper.BottomNavigationBehavior;

public class ClientDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_dashboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        // load the home fragment by default
        loadFragment(new HomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) { // switching between different fragments
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.postjob:
                    fragment = new PostJobFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.quotations:
                    fragment = new QuotationsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    /**
     * loading fragment into FrameLayout
     *
     * @param fragment
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}