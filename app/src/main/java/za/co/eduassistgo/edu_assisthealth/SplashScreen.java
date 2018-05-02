package za.co.eduassistgo.edu_assisthealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

    class SplashScreenThread extends Thread {
        int wait = 0;

        SplashScreenThread() {
        }

        public void run() {
            try {
                super.run();
                sleep(3000);

            } catch (Exception e) {
                System.out.println("EXc=" + e);
            } finally {
                //SplashScreen.this.startActivity(new Intent(SplashScreen.this, Main.class));
                SplashScreen.this.startActivity(new Intent(SplashScreen.this, Main.class));
                SplashScreen.this.finish();
            }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new SplashScreenThread().start();
    }
}
