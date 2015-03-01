package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 08/02/2015.
 */

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import net.infobosccoma.romaarnau_activitat1.R;
import net.infobosccoma.romaarnau_activitat1.dao.EspeciesConversor;
import net.infobosccoma.romaarnau_activitat1.dao.PescaOsonaSQLiteHelper;
import net.infobosccoma.romaarnau_activitat1.model.Especies;

import java.util.ArrayList;

public class LlistaEspeciesActivity extends ActionBarActivity {

    private ArrayList<Especies> especiesTaula;

    private TextView senseDadesEspecies;
    private ListView llistaEspecies;

    private EspeciesAdapter adapter;

    private SearchView searchView;

    private PescaOsonaSQLiteHelper esHelper;
    private EspeciesConversor esConv;

    private Toast toastAvis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_especies);

        llistaEspecies = (ListView) findViewById(R.id.lvEspecies);
        senseDadesEspecies = (TextView) findViewById(R.id.tvSenseDadesEspecie);
        esHelper = new PescaOsonaSQLiteHelper(this);

        // obtenir l'objecte BD
        SQLiteDatabase db = esHelper.getWritableDatabase();
        esConv = new EspeciesConversor(esHelper);

        // Si s'ha obert correctament la BD
        if (db != null) {
            refreshData();

            llistaEspecies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Obrir una activity
                    Intent intent = new Intent(getBaseContext(), DetailActivityEspecies.class);
                    Bundle b = new Bundle();
                    b.putSerializable("especies", adapter.getItem(position));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
            // Tancar la base de dades
            db.close();
        }

        // Assignem a la llista un menú contextual
        registerForContextMenu(llistaEspecies);
    }

    /**
     * Mètode que recarrega les dades de la llista
     */
    private void refreshData() {
        especiesTaula = esConv.getAll();
        if (especiesTaula != null) {
            llistaEspecies.setVisibility(llistaEspecies.VISIBLE);
            senseDadesEspecies.setVisibility(senseDadesEspecies.INVISIBLE);
            adapter = new EspeciesAdapter(this, especiesTaula);
            llistaEspecies.setAdapter(adapter);
        } else {
            llistaEspecies.setVisibility(llistaEspecies.INVISIBLE);
            senseDadesEspecies.setVisibility(senseDadesEspecies.VISIBLE);
            senseDadesEspecies.setText("No hi ha cap espècie");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscador, menu);

        // Implementació del buscador
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String text) {
                if (especiesTaula != null){
                    adapter.getFilter().filter(text);
                }
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                adapter.getFilter().filter(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(this, NovaEspecieActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Mètode que recull el resultat de l'activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Especies t = (Especies) data.getExtras().getSerializable("dades");
            if (esConv.save(t)) {
                toastAvis = Toast.makeText(getApplicationContext(),
                        "La nova espècie s'ha creat correctament", Toast.LENGTH_SHORT);
                toastAvis.show();
            }
            refreshData();
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Especies t = (Especies) data.getExtras().getSerializable("dades");
            if (esConv.update(t)) {
                toastAvis = Toast.makeText(getApplicationContext(),
                        "L'espècie s'ha modificat correctament", Toast.LENGTH_SHORT);
                toastAvis.show();
            }
            refreshData();
        }
    }

    /**
     * Mètode que crea el menú contextual amb les seves opcions
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Opcions");
        menu.add(0, 1, 0, "Esborrar");
        menu.add(0, 2, 1, "Editar");
    }

    /**
     * Mètode que controla quin item es selecciona del menú contextual
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle() == "Esborrar") {
            dialegAprovacio(info.position);
        } else if (item.getTitle() == "Editar") {
            Intent intent = new Intent(getBaseContext(), NovaEspecieActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("especie", especiesTaula.get(info.position));
            intent.putExtras(b);
            startActivityForResult(intent, 2);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Mètode que demana si s'està segur d'esborrar una espècie
     * @param info
     */
    private void dialegAprovacio(final int info) {
        final int POS = info;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estàs segur que vols esborrar aquesta espècie?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (esConv.remove(especiesTaula.get(POS))) {
                    Toast.makeText(getApplicationContext(), "L'espècie s'ha esborrat correctament", Toast.LENGTH_LONG).show();
                    refreshData();
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
