package za.co.eduassistgo.edu_assisthealth;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sbnnko004 on 2018/07/12.
 */

public class TabElement extends Fragment {
    int tabNumber;
    WebView WebViewWithCSS;
    ArrayList<String> urls;
    public TabElement() {
        tabNumber = 0;
        urls = new ArrayList<>(Arrays.asList("file:///android_asset/webViews/health.html", "file:///android_asset/webViews/wellbeing.html", "file:///android_asset/webViews/parents.html","file:///android_asset/webViews/challenges.html","file:///android_asset/webViews/healthcare.html","file:///android_asset/webViews/mentalillness.html","file:///android_asset/webViews/contest.html","file:///android_asset/webViews/sexualviolence.html","file:///android_asset/webViews/help.html","file:///android_asset/webViews/emergency.html"));

        // Required empty public constructor
    }

    public void setNumber(int tabNumber){
        this.tabNumber=tabNumber;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView;
        if(tabNumber==0)
            rootView = inflater.inflate(R.layout.tab01_home, container, false);
        else{
            rootView = inflater.inflate(R.layout.tabs_default, container, false);

            WebViewWithCSS = (WebView) rootView.findViewById(R.id.healthWebView1);
            //rootView.setTitle("Health and Wellbeing");
            WebViewWithCSS.getSettings().setJavaScriptEnabled(true);
            WebViewWithCSS.getSettings().setAllowFileAccess(true);
            WebViewWithCSS.getSettings().setPluginState(WebSettings.PluginState.ON);
            WebViewWithCSS.getSettings().setDomStorageEnabled(true);
            WebViewWithCSS.getSettings().setAllowContentAccess(true);
            WebViewWithCSS.getSettings().setAllowFileAccessFromFileURLs(true);
            WebViewWithCSS.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            WebViewWithCSS.setWebViewClient(new WebClientInterceptor());

            WebViewWithCSS.loadUrl(urls.get(tabNumber-1));
            //WebViewWithCSS.loadUrl(url);
            if (Build.VERSION.SDK_INT >= 19) {
                WebViewWithCSS.setLayerType(2, null);
            } else {
                WebViewWithCSS.setLayerType(1, null);
            }
            //WebViewWithCSS.getSettings().setCacheMode(2);
        }

        return rootView;
    }

    public class WebClientInterceptor extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            if(url.startsWith("https://")){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url ));
                startActivity(browserIntent);
                return true;
            }
            else if(url.startsWith("file:///"))
            {
                WebViewWithCSS.loadUrl(url);
                return true;
            }
            else{
                Intent intent = new Intent(Main.main, webActivity.class);
                //Toast.makeText(Main.main.getApplicationContext(), url.substring(11), Toast.LENGTH_SHORT).show();
                intent.putExtra("url", url.substring(11));
                Main.main.startActivity(intent);
                return true;
            }
        }
    }
}
