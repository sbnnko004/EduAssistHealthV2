package za.co.eduassistgo.edu_assisthealth;



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
//import com.google.android.gms.analytics.HitBuilders.EventBuilder;
//import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder;
//import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

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








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        //boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer);
        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-9189472653918970/7167825305");
        adView.setAdSize(AdSize.BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewContainer.addView(adView);
        adView.loadAd(adRequest);



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
        } else if (id == R.id.ContactUs) {
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
        intent.putExtra("android.intent.extra.TEXT", "http://play.google.com/store/apps/details?id=za.co.eduassistgo.edu_assisthealth");
        startActivity(Intent.createChooser(intent, "Share with"));
    }


}
