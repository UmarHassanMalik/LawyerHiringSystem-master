package com.innova.lawyerhiringsystem.getstarted;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.innova.lawyerhiringsystem.R;

public class GetStarted extends FragmentActivity {

    private static final int NUM_PAGES = 3;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            if (position == 0)
            {
                return new ScreenSlidePage();
            }
            else if (position == 1)
            {
                return new DescribeLawyer();
            }
            else if (position == 2)
            {
                return new RecieveFragment();
            }
            return new ScreenSlidePage();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}