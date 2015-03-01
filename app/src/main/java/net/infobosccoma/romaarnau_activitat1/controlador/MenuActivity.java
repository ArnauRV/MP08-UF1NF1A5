package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 6/02/2015.
 */

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.infobosccoma.romaarnau_activitat1.R;
import net.infobosccoma.romaarnau_activitat1.dao.PescaOsonaSQLiteHelper;

public class MenuActivity extends ActionBarActivity {

    private Button btnSocietat;
    private Button btnEspecies;
    private Button btnApren;
    private TextView menuPrincipalFont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        PescaOsonaSQLiteHelper db = new PescaOsonaSQLiteHelper(this);
        db.getWritableDatabase();

        btnSocietat = (Button) findViewById(R.id.btnsocietats);

        btnSocietat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obrir una activity
                Intent intent = new Intent(getBaseContext(), LlistaSocietatsActivity.class);
                startActivity(intent);
            }
        });

        btnEspecies = (Button) findViewById(R.id.btnespecies);

        btnEspecies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obrir una activity
                Intent intent = new Intent(getBaseContext(), LlistaEspeciesActivity.class);
                startActivity(intent);
            }
        });

        btnApren = (Button) findViewById(R.id.btnAprenPescar);

        btnApren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obrir una activity
                Intent intent = new Intent(getBaseContext(), LlistaCursosActivity.class);
                startActivity(intent);
            }
        });


        menuPrincipalFont = (TextView) findViewById(R.id.tvMenuPrincipal);
        Typeface font = Typeface.createFromAsset(getAssets(), "textMenu.ttf");
        menuPrincipalFont.setTypeface(font);

        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return true;
    }
}
