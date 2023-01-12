package com.fansysoft.testbottomnavigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;


// https://hackmd.io/@fd-javaAndroid/ryJOV5mut 改顏色
//
public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation;
    private OrdersFragment ordersFragment = new OrdersFragment();
    private UserProfileFragment userProfileFragment = new UserProfileFragment();
    private AnnouncementFragment announcementFragment = new AnnouncementFragment();
    private ChatBotFragment chatBotFragment = new ChatBotFragment();
    private FragmentTransaction trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        BadgeDrawable badgeDrawable = bottom_navigation.getOrCreateBadge(R.id.orders);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(8);
        badgeDrawable.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.light_blue));

        trans = getSupportFragmentManager().beginTransaction();
        trans.replace(R.id.frameLayout, ordersFragment).commit();
        bottom_navigation.setOnItemSelectedListener( item -> {
            switch (item.getItemId()) {
                case R.id.orders:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, ordersFragment).commit();
                    badgeDrawable.setVisible(false);
                    return true;
                case R.id.user_profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, userProfileFragment).commit();
                    return true;
                case R.id.announcement:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, announcementFragment).commit();
                    return true;
                case R.id.chat_bot:
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, chatBotFragment).commit();
                    return true;
            }
            return false;
        });

    }
}