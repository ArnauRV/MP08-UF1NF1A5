package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 07/02/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import net.infobosccoma.romaarnau_activitat1.R;
import net.infobosccoma.romaarnau_activitat1.model.Societats;

public class DetailActivitySocietats extends ActionBarActivity  {

    private Societats dades;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_societats);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView infTextView;
        dades = (Societats)getIntent().getExtras().getSerializable("titular");

        //Obtenir dades de la GUI
        infTextView = (TextView)findViewById(R.id.tvTitol);

        ImageView imagen = (ImageView) findViewById(R.id.imgCapcalera);
        imagen.setImageBitmap(Utilitats.getPhoto(dades.getImgCapcalera()));

        // Assignar dades a la GUI
        infTextView.setText(dades.getNom());

        infTextView = (TextView) findViewById(R.id.tvNomPoblacio);
        infTextView.setText(dades.getPoblacio());

        infTextView = (TextView) findViewById(R.id.tvDireccio);
        infTextView.setText(dades.getDireccio());

        infTextView = (TextView) findViewById(R.id.tvNumSocis);
        infTextView.setText(Integer.toString(dades.getNumSocis()));

        infTextView = (TextView) findViewById(R.id.tvNomPresident);
        infTextView.setText(dades.getPresident());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compartir, menu);

        MenuItem item = menu.findItem(R.id.menu_item_compartir);

        // Crida a les opcions de compartir
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultIntent());

        return true;
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hola! comparteixo amb tú informació sobre la " + dades.getNom() + "\n"
                + "Població: " + dades.getPoblacio() + "\n"
                + "Direcció: " + dades.getDireccio() + "\n"
                + "President: " + dades.getPresident() + "\n"
                + "Numero de socis: " + dades.getNumSocis() + "\n");
        return intent;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

