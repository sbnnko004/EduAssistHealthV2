package za.co.eduassistgo.edu_assisthealth;

import android.content.Context;
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



public class Tab03Wellbeing extends Fragment {
    public Tab03Wellbeing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.tab02_health, container, false);

        WebView WebViewWithCSS = (WebView) rootView.findViewById(R.id.healthWebView1);
        //rootView.setTitle("Health and Wellbeing");
        WebViewWithCSS.getSettings().setJavaScriptEnabled(true);
        WebViewWithCSS.getSettings().setAllowFileAccess(true);
        WebViewWithCSS.getSettings().setPluginState(WebSettings.PluginState.ON);
        WebViewWithCSS.getSettings().setDomStorageEnabled(true);
        WebViewWithCSS.getSettings().setAllowContentAccess(true);
        WebViewWithCSS.getSettings().setAllowFileAccessFromFileURLs(true);
        WebViewWithCSS.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        WebViewWithCSS.setWebChromeClient(new WebChromeClient());

        WebViewWithCSS.loadUrl("file:///android_asset/webViews/wellbeing.html");
        //WebViewWithCSS.loadUrl(url);
        if (Build.VERSION.SDK_INT >= 19) {
            WebViewWithCSS.setLayerType(2, null);
        } else {
            WebViewWithCSS.setLayerType(1, null);
        }
        WebViewWithCSS.getSettings().setCacheMode(2);
        return rootView;
    }
}
