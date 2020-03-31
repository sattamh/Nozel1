package Sa.Nozel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class splash_screen extends AppCompatActivity {
    private int sleep_timer = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // for hiding the android default tool bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //  mobing the load layout after full screen feature applied

        setContentView(R.layout.splash_screen_lt);

        // to hide actionBar

        getSupportActionBar().hide();

        // to call LogoLauncher class in Oncreate

        SplashScreenLauncher splashScreenLauncher = new SplashScreenLauncher();
        splashScreenLauncher.start();

    }
    private class SplashScreenLauncher extends Thread{
        // to display the screen for couple of seconds
        public void run (){
            try {
                // lets it run for 3 Second
                sleep(1000 * sleep_timer);

            }
            // we will put exception if in case if it doesn't work
            catch(InterruptedException e) {
                e.printStackTrace();


        }
            startActivity(new Intent(splash_screen.this,main_activity.class));
            splash_screen.this.finish();
        }

    }
}
