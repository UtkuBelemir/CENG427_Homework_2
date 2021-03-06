package com.ubelemir.homework2;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private ViewPager fragmentPager;
    private TabLayout controlTabs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentPager = findViewById(R.id.fragmentPager);
        fragmentPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));
        fragmentPager.setOffscreenPageLimit(1);
        controlTabs = findViewById(R.id.controlTabs);
        controlTabs.setupWithViewPager(fragmentPager);
    }
}
