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
import net.infobosccoma.romaarnau_activitat1.model.Especies;

public class DetailActivityEspecies extends ActionBarActivity {

    private Especies dades;
    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_especies);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView infTextView;
        dades = (Especies)getIntent().getExtras().getSerializable("especies");

        ImageView imagen = (ImageView) findViewById(R.id.imgCapcalera);
        imagen.setImageBitmap(Utilitats.getPhoto(dades.getImgCapcalera()));

        //Obtenir dades de la GUI
        infTextView = (TextView)findViewById(R.id.tvTitol);
        infTextView.setText(dades.getNomEspecie());

        infTextView = (TextView) findViewById(R.id.tvNomCientific);
        infTextView.setText(dades.getNomCientific());

        infTextView = (TextView) findViewById(R.id.tvNomFamilia);
        infTextView.setText(dades.getFamilia());

        infTextView = (TextView) findViewById(R.id.tvTextDescripcio);
        infTextView.setText(dades.getInfo());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compartir, menu);

        MenuItem item = menu.findItem(R.id.menu_item_compartir);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultIntent());
        // Return true per mostrar el menú
        return true;
    }

    private Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, "Hola! comparteixo amb tú informació sobre l'espècie " + dades.getNomEspecie() + "\n"
                + "Nom científic: " + dades.getNomCientific() + "\n"
                + "Família: " + dades.getFamilia() + "\n"
                + "Descripció: " + dades.getInfo() + "\n");
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

