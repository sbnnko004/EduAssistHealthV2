package za.co.eduassistgo.edu_assisthealth;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders.EventBuilder;
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
import com.google.android.gms.analytics.Tracker;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import org.json.JSONObject;

public class Main extends AppCompatActivity {
    public static Main main;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    SharedPreferences sharedPreferences;
    public AdView adView;
    TabItem tabHome;
    TabItem tabHealth;
    TabItem tabWellbeing;
    TabItem tabParents;
    TabItem tabChallenges;
    TabItem tabHealthcare;
    TabItem tabMentalIllness;
    TabItem tabContest;
    TabItem tabSexualViolence;
    TabItem tabHelp;
    TabItem tabEmergency;



    class Close implements OnClickListener {
        Close() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Main.this.finish();
        }
    }

    class KeepOpen implements OnClickListener {
        KeepOpen() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class dontSend implements OnClickListener {
        dontSend() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    class Update implements OnClickListener {
        Update() {
        }

        public void onClick(DialogInterface dialog, int which) {
            //MainActivity.this.mtracker.send(new EventBuilder().setCategory("Button pressed").setAction("Update").setLabel("Update button pressed").build());
            Main.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=za.co.eduassisthealth")));
        }
    }

    class Cancel implements OnClickListener {
        Cancel() {
        }

        public void onClick(DialogInterface dialog, int which) {
            //MainActivity.this.mtracker.send(new EventBuilder().setCategory("Button pressed").setAction("cancel").setLabel("cancel Update button pressed").build());
            dialog.cancel();
        }
    }

    class check extends AsyncTask<String, Void, String> {
        check() {
        }

        protected String doInBackground(String... urls) {
            String result = "";
            try {
                InputStreamReader reader = new InputStreamReader(((HttpURLConnection) new URL(urls[0]).openConnection()).getInputStream());
                for (int data = reader.read(); data != -1; data = reader.read()) {
                    result = result + ((char) data);
                }
                return result;
            } catch (Exception e) {
                return null;
            }
        }

        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            try {
                if (!new JSONObject(response).getBoolean("success")) {
                    Main.this.updatefound();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        main = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
        adView = new AdView(this);
        //adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.setAdUnitId("ca-app-pub-9189472653918970/7167825305");
        adView.setAdSize(AdSize.SMART_BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setVisibility(View.GONE);
        adView.setAdListener(new AdListener() {
            private void showToast(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                //showToast("Ad loaded.");
                if (adView.getVisibility() == View.GONE) {
                    adView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //showToast(String.format("Ad failed to load with error code %d.", errorCode)
            }

            @Override
            public void onAdOpened() {
                //showToast("Ad opened.");
            }

            @Override
            public void onAdClosed() {
                //showToast("Ad closed.");
                adView.destroy();
                adView.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdLeftApplication() {
                //showToast("Ad left application.");
                adView.destroy();
                adView.loadAd(new AdRequest.Builder().build());
            }
        });
        adViewContainer.addView(adView);
        adView.loadAd(adRequest);

    }


    @Override
    public void onResume() {
        super.onResume();

        // Resume the AdView.
        adView.resume();
    }

    @Override
    public void onPause() {
        // Pause the AdView.
        adView.pause();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.
        adView.destroy();

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share) {
            shareit();
        } else if (id == R.id.feedback) {
            feedback();
        } else if (id == R.id.contact_us) {
            startActivity(new Intent(getApplicationContext(), contactUs.class));
        }
        return super.onOptionsItemSelected(item);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            Builder alertDialog = new Builder(this);
            alertDialog.setTitle((CharSequence) "EXIT");
            alertDialog.setMessage((CharSequence) "Are you sure you want to exit");
            alertDialog.setIcon((int) R.drawable.exit);
            alertDialog.setPositiveButton((CharSequence) "YES", new Close());
            alertDialog.setNegativeButton((CharSequence) "NO", new KeepOpen());
            alertDialog.show();
        }
        return super.onKeyDown(keyCode, event);
    }
    public void feedback() {
        Builder alertDialog = new Builder(this);
        alertDialog.setTitle((CharSequence) "Feedback");
        alertDialog.setMessage((CharSequence) "Your feedback is valuable to us.");
        final EditText input = new EditText(this);
        input.setLayoutParams(new LayoutParams(-1, -1));
        alertDialog.setView(input);
        alertDialog.setIcon((int) R.drawable.feedback);
        alertDialog.setPositiveButton((CharSequence) "YES", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String feedback = input.getText().toString();
                Intent email = new Intent("android.intent.action.SEND");
                email.putExtra("android.intent.extra.EMAIL", new String[]{"nkosingphiless@gmail.com"});
                email.putExtra("android.intent.extra.TEXT", feedback);
                email.setType("message/rfc822");
                Main.this.startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        alertDialog.setNegativeButton((CharSequence) "NO", new dontSend());
        alertDialog.show();
    }
    public void shareit() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", "http://play.google.com/store/apps/details?id=za.co.eduassisthealth");
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    public void updatefound() {
        Builder alertDialog = new Builder(this);
        alertDialog.setTitle((CharSequence) "Update Available");
        alertDialog.setMessage((CharSequence) "Your app is not upto date. Please update the app to get all the latest features.");
        alertDialog.setIcon((int) R.drawable.update);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton((CharSequence) "Update", new Update());
        alertDialog.setNegativeButton((CharSequence) "Not now", new Cancel());
        alertDialog.show();
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.tab01_home, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }


    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0:
                    return new Tab01Home();
                case 1:
                    return new Tab02Health();
                case 2:
                    return new Tab03Wellbeing();
                case 3:
                    return new Tab04Parents();
                case 4:
                    return new Tab05Challenges();
                case 5:
                    return new Tab06Healthcare();
                case 6:
                    return new Tab07MentalIllness();
                case 7:
                    return new Tab08Contest();
                case 8:
                    return new Tab09SexualViolence();
                case 9:
                    return new Tab10Help();
                case 10:
                    return new Tab11Emergency();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 11;
        }
    }
}
