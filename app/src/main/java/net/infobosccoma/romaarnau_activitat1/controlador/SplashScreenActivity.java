package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 06/02/2015.
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import net.infobosccoma.romaarnau_activitat1.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends Activity {

    // Variable que emmagatzema el temps que es mostrarà la pantalla
    private static final long SPLASH_SCREEN_DELAY = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_screen);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Comença el següent layout
                Intent mainIntent = new Intent().setClass(
                        SplashScreenActivity.this, MenuActivity.class);
                startActivity(mainIntent);

                // Tanco l'activitat
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
