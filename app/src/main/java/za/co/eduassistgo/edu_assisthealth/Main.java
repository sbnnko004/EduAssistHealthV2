package za.co.eduassistgo.edu_assisthealth;

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
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    SharedPreferences sharedPreferences;
    public AdView adView;

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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9189472653918970/7167825305");
        adView.setAdSize(AdSize.BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewContainer.addView(adView);
        adView.loadAd(adRequest);
        /*List<Fragment> fragments = new Vector();

        fragments.add(Fragment.instantiate(this, Home.class.getName()));
        fragments.add(Fragment.instantiate(this, Wellness.class.getName()));
        fragments.add(Fragment.instantiate(this, Parents.class.getName()));
        fragments.add(Fragment.instantiate(this, Challenges.class.getName()));
        fragments.add(Fragment.instantiate(this, Healthcare.class.getName()));
        fragments.add(Fragment.instantiate(this, MentalIllness.class.getName()));
        fragments.add(Fragment.instantiate(this, Consent.class.getName()));
        fragments.add(Fragment.instantiate(this, SexualViolence.class.getName()));
        fragments.add(Fragment.instantiate(this, Help.class.getName()));
        fragments.add(Fragment.instantiate(this, Emergency.class.getName()));

        final ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(new PageAdaptor(getSupportFragmentManager(), fragments));
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(2);
        TabListener tabListener = new TabListener() {
            public void onTabSelected(Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            }

            public void onTabReselected(Tab tab, FragmentTransaction ft) {
            }
        };
        actionBar.addTab(actionBar.newTab().setText((CharSequence) " HOME").setIcon((int) R.drawable.home).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText((CharSequence) " PDF").setIcon((int) R.drawable.pdf).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText((CharSequence) " Videos").setIcon((int) R.drawable.video).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText((CharSequence) "  QUIZ").setIcon((int) R.drawable.quiz_icon).setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText((CharSequence) " Ask Us").setIcon((int) R.drawable.ques).setTabListener(tabListener));

        pager.addOnPageChangeListener(new SimpleOnPageChangeListener() {
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                actionBar.setElevation(0.5f);
            }
        });*/


    }

    public boolean onCreateOptionsMenu(Menu menu) {
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


}
