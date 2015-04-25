package eden.com.br.clubecomunidade;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Locale;

import eden.com.br.clubecomunidade.fragment.HelloAppFragment;
import eden.com.br.clubecomunidade.fragment.MainFragment;
import eden.com.br.clubecomunidade.fragment.NewsDetailFragment;
import eden.com.br.clubecomunidade.interfaces.OnFragmentInteractionListener;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener, OnFragmentInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    private Fragment contentFragment;
    MainFragment mainFragment;
    NewsDetailFragment newsDetailFragment;

    Fragment fragmentToDisplay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }


        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(newsDetailFragment.FRAGMENT_TAG)) {
                    if (fragmentManager
                            .findFragmentByTag(newsDetailFragment.FRAGMENT_TAG) != null) {
                        contentFragment = fragmentManager
                                .findFragmentByTag(newsDetailFragment.FRAGMENT_TAG);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(MainFragment.FRAGMENT_TAG) != null) {
                mainFragment = (MainFragment) fragmentManager
                        .findFragmentByTag(MainFragment.FRAGMENT_TAG);
                contentFragment = mainFragment;
            }
        } else {
            mainFragment = new MainFragment();
            switchContent(mainFragment, MainFragment.FRAGMENT_TAG);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof MainFragment) {
            outState.putString("content", MainFragment.FRAGMENT_TAG);
        } else {
            outState.putString("content", NewsDetailFragment.FRAGMENT_TAG);
        }
        super.onSaveInstanceState(outState);
    }

    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate());

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.pager, fragment, tag);
            // Only ProductDetailFragment is added to the back stack.
            if (!(fragment instanceof MainFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 0 && this.fragmentToDisplay instanceof NewsDetailFragment) {

            mViewPager.getAdapter().notifyDataSetChanged();

        }else if(fm.getBackStackEntryCount() <= 0){

            finish();

        }else {

            super.onBackPressed();

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onFragmentInteraction(Fragment fragment) {

        this.fragmentToDisplay = fragment;
        mViewPager.getAdapter().notifyDataSetChanged();

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        boolean notifyDataChanged = false;
        int incrementalDataChanged = 900;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch(position) {
                case 0:

                    if (fragmentToDisplay instanceof NewsDetailFragment) {

                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.addToBackStack( ((NewsDetailFragment) fragmentToDisplay).FRAGMENT_TAG );
                        ft.commit();

                        return fragmentToDisplay;

                    } else {
                        return new MainFragment();
                    }

                case 1:
                    return new HelloAppFragment();

                case 101:
                    // NewsDetailFragment com Bundle arguments
                    //return fragmentToDisplay;

                default:
                    return new NewsDetailFragment();

            }

        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public int getItemPosition(Object object){

            if(object instanceof MainFragment && fragmentToDisplay instanceof NewsDetailFragment) {
                this.notifyDataChanged = true;
                return POSITION_NONE;
            }else if(object instanceof NewsDetailFragment && fragmentToDisplay instanceof NewsDetailFragment) {
                this.notifyDataChanged = true;
                fragmentToDisplay = null;
                return POSITION_NONE;
            }else {
                return super.getItemPosition(object);
            }

        }

        @Override
        public long getItemId(int position) {

            if(notifyDataChanged && ((fragmentToDisplay == null) || (position == 0 && fragmentToDisplay instanceof NewsDetailFragment))){
                notifyDataChanged = false;
                return ++incrementalDataChanged;
            }

            return super.getItemId(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
                case 3:
                    return getString(R.string.title_section4).toUpperCase(l);
            }
            return null;
        }
    }
}
