package com.cuberto.bubbletabsampleapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.cuberto.bubbleicontabbarandroid.TabBubbleAnimator;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabBubbleAnimator tabBubbleAnimator;
    private String[] titles = new String[]{"Home", "Clock", "Folder", "Menu"};
    private int[] colors = new int[]{R.color.home, R.color.clock, R.color.folder, R.color.menu};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentList.add(new TabFragment(titles[0], colors[0]));
        mFragmentList.add(new TabFragment(titles[1], colors[1]));
        mFragmentList.add(new TabFragment(titles[2], colors[2]));
        mFragmentList.add(new TabFragment(titles[3], colors[3]));
        ViewPager viewPager = findViewById(R.id.viewPager);
        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }
        };
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabBubbleAnimator = new TabBubbleAnimator(tabLayout);
        tabBubbleAnimator.addTabItem(titles[0], R.drawable.ic_grid, colors[0]);
        tabBubbleAnimator.addTabItem(titles[1], R.drawable.ic_clock,colors[1]);
        tabBubbleAnimator.addTabItem(titles[2], R.drawable.ic_folder, colors[2]);
        tabBubbleAnimator.addTabItem(titles[3], R.drawable.ic_menu, colors[3]);
        tabBubbleAnimator.setUnselectedColorId(Color.BLACK);
        tabBubbleAnimator.highLightTab(0);
        viewPager.addOnPageChangeListener(tabBubbleAnimator);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabBubbleAnimator.onStart((TabLayout) findViewById(R.id.tabLayout));
    }

    @Override
    protected void onStop() {
        super.onStop();
        tabBubbleAnimator.onStop();
    }

}
