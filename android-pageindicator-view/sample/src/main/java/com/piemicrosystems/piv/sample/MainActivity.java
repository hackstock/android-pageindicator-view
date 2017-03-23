package com.piemicrosystems.piv.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.piemicrosystems.piv.PageIndicatorView;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout pageIndicatorContainer;
    PageIndicatorView pageIndicatorView;
    IntroPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.intro_view_pager);
        pageIndicatorContainer = (LinearLayout) findViewById(R.id.page_indicator_container);

        try {
            pageIndicatorView = PageIndicatorView.newInstance(this,5,35,"#CECECE","#000000");
            pageIndicatorContainer.addView(pageIndicatorView);
        } catch (PageIndicatorView.InvalidPagesCountException e) {
            e.printStackTrace();
        }

        setupViewPager();
    }

    private void setupViewPager(){
        pagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    pageIndicatorView.setCurrentPage(position);
                } catch (PageIndicatorView.PageOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private class IntroPagerAdapter extends FragmentStatePagerAdapter {
        public IntroPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new IntroPageFragment();
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
