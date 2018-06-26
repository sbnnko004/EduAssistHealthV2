package za.co.eduassistgo.edu_assisthealth;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class contactUs extends AppCompatActivity
{
    String id ="nkosingphiless@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        //NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        //ActionBar bar = getActionBar();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2F4F4F")));
        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);
        TextView fb = (TextView) findViewById(R.id.facebook);
        TextView web = (TextView) findViewById(R.id.web);
        TextView ver = (TextView) findViewById(R.id.version);
        TextView twitter = (TextView) findViewById(R.id.twitter);
        TextView insta = (TextView) findViewById(R.id.insta);
        name.setTypeface(null, 1);
        email.setTypeface(null, 3);
        insta.setTypeface(null, 3);
        twitter.setTypeface(null, 3);
        fb.setTypeface(null, 3);
        web.setTypeface(null, 3);
        ver.setTypeface(null, 1);
        PackageInfo pInfo = null;
        try
        {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch (NameNotFoundException e)
        {
            e.printStackTrace();
        }
        ver.setText("EduAssistGo Health V" + pInfo.versionName);
        name.setText("Nkosingiphile Sibandze :\n +26879857364");
        email.setText("nkosingphiless@gmail.com");
        fb.setText("https://www.facebook.com/nkosingiphiless");
        twitter.setText("http://twitter.com/thugzbunny");
        insta.setText("http://instagram.com/n_sibandze");
        web.setText("http://www.eduassistgo.co.za/health");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");

        // seting action bar color


    }
    public void onClick(View v)
    {
        if(v.getId()==R.id.email)
        {
            Intent email = new Intent("android.intent.action.SEND");
            email.putExtra("android.intent.extra.EMAIL", new String[]{contactUs.this.id});
            email.setType("message/rfc822");
            contactUs.this.startActivity(Intent.createChooser(email, "Choose an Email client :"));
        }
        else if(v.getId()==R.id.name)
        {
            Intent intent = new Intent("android.intent.action.DIAL");
            intent.setData(Uri.parse("tel:+26879857364"));
            contactUs.this.startActivity(intent);
        }
        else if(v.getId()==R.id.facebook)
        {
            contactUs.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/nkosingiphiless/")));
        }
        else if(v.getId()==R.id.web)
        {
            contactUs.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.eduassistgo.co.za/health")));
        }
        else if(v.getId()==R.id.twitter)
        {
            contactUs.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.twitter.com/thugzbunny")));
        }
        else if(v.getId()==R.id.insta)
        {
            contactUs.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://www.instagram.com/n_sibandze")));
        }
    }
}
