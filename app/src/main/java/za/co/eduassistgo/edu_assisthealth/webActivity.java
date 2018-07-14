package za.co.eduassistgo.edu_assisthealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Arrays;
import java.util.Random;

public class webActivity extends AppCompatActivity {
    AdView adView;
    class dontSend implements DialogInterface.OnClickListener {
        dontSend() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String url = intent.getStringExtra("url");


        WebView WebViewWithCSS = (WebView) findViewById(R.id.healthWebView2);

        WebViewWithCSS.getSettings().setJavaScriptEnabled(true);
        WebViewWithCSS.getSettings().setAllowFileAccess(true);
        WebViewWithCSS.getSettings().setPluginState(WebSettings.PluginState.ON);
        WebViewWithCSS.getSettings().setDomStorageEnabled(true);
        WebViewWithCSS.getSettings().setAllowContentAccess(true);
        WebViewWithCSS.getSettings().setAllowFileAccessFromFileURLs(true);
        WebViewWithCSS.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //WebViewWithCSS.setWebViewClient(new Main.main.TabElement.WebClientInterceptor());

        WebViewWithCSS.loadUrl(url);
        WebViewWithCSS.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                //Toast.makeText(webActivity.this,"Page loading started",Toast.LENGTH_SHORT).show();
                //String mUrl = view.getUrl();
                //getSupportActionBar().setTitle(mUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                String title = view.getTitle();
                webActivity.this.setTitle(title);
                //Toast.makeText(webActivity.this,mUrl,Toast.LENGTH_LONG).show();
            }
        });
        //setTitle(WebViewWithCSS.getTitle());
        if (Build.VERSION.SDK_INT >= 19) {
            WebViewWithCSS.setLayerType(2, null);
        } else {
            WebViewWithCSS.setLayerType(1, null);
        }


        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adViewContainer2);
        adView = new AdView(this);
        adView.setAdUnitId(Main.main.adIDs.get((new Random()).nextInt(3)));
        adView.setAdSize(AdSize.SMART_BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.setAdListener(new AdListener() {
            private void showToast(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                //showToast("Ad loaded.");
                //if (adView.getVisibility() == View.GONE) {
                //  adView.setVisibility(View.VISIBLE);
                //}
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                //showToast("Ad error: code "+errorCode);
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
                //adView.destroy();
                //adView.loadAd(new AdRequest.Builder().build());
            }
        });
        adViewContainer.addView(adView);
        adView.loadAd(adRequest);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.webmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.share:
                Main.main.shareit();
                return true;
            case R.id.feedback:
                feedback();
                return true;
            case R.id.contact_us:
                startActivity(new Intent(getApplicationContext(), contactUs.class));
                return true;
            case android.R.id.home:
                //Toast.makeText(getApplicationContext(), "Back Pressed", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void feedback() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle((CharSequence) "Feedback");
        alertDialog.setMessage((CharSequence) "Your feedback is valuable to us.");
        final EditText input = new EditText(this);
        input.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        alertDialog.setView(input);
        alertDialog.setIcon((int) R.drawable.feedback);
        alertDialog.setPositiveButton((CharSequence) "YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String feedback = input.getText().toString();
                Intent email = new Intent("android.intent.action.SEND");
                email.putExtra("android.intent.extra.EMAIL", new String[]{"nkosingphiless@gmail.com"});
                email.putExtra("android.intent.extra.TEXT", feedback);
                email.setType("message/rfc822");
                Main.main.startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });
        alertDialog.setNegativeButton((CharSequence) "NO", new dontSend());
        alertDialog.show();
    }

}
