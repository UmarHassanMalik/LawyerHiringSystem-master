package com.innova.lawyerhiringsystem;
/*
 * This is Dashboard specific to 'Lawyer' role
 * it will be first screen presented to the lawyer after login
 * contains navigation to all the role specific function form bottom navigation bar
 * Applying to available a job, communicating with client, managing profile, showing previous and on going case stats
 * */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.innova.lawyerhiringsystem.fragments.ApplyJobFragment;
import com.innova.lawyerhiringsystem.fragments.HomeFragment;
import com.innova.lawyerhiringsystem.fragments.LawyerHomeFragment;
import com.innova.lawyerhiringsystem.fragments.PostJobFragment;
import com.innova.lawyerhiringsystem.fragments.ProfileFragment;
import com.innova.lawyerhiringsystem.fragments.QuotationsFragment;
import com.innova.lawyerhiringsystem.helper.BottomNavigationBehavior;

public class LawyerDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_dashboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.lawyer_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // attaching bottom sheet behaviour - hide / show on scroll
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        // load the home fragment by default
        loadFragment(new LawyerHomeFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) { // switching between different fragments
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.lawyer_home:
                    fragment = new LawyerHomeFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.lawyer_viewjob:
                    fragment = new ApplyJobFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.lawyer_profile:
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
        transaction.replace(R.id.lawyer_frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
