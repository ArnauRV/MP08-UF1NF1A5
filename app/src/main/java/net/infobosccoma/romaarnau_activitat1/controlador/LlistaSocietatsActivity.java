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
import net.infobosccoma.romaarnau_activitat1.dao.PescaOsonaSQLiteHelper;
import net.infobosccoma.romaarnau_activitat1.dao.SocietatsConversor;
import net.infobosccoma.romaarnau_activitat1.model.Societats;

import java.util.ArrayList;

public class LlistaSocietatsActivity extends ActionBarActivity {

    private ArrayList<Societats> societatsTaula;

    private ListView llistaSocietats;
    private TextView senseDades;

    private SocietatsAdapter adapter;

    private SearchView searchView;

    private PescaOsonaSQLiteHelper sosHelper;
    private SocietatsConversor sosConv;

    private Toast toastAvis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_societats);

        senseDades = (TextView) findViewById(R.id.tvSenseDades);
        llistaSocietats = (ListView) findViewById(R.id.lvSocietats);
        sosHelper = new PescaOsonaSQLiteHelper(this);
        // obtenir l'objecte BD
        SQLiteDatabase db = sosHelper.getWritableDatabase();
        sosConv = new SocietatsConversor(sosHelper);

        // Si s'ha obert correctament la BD
        if(db != null) {
            refreshData();
            llistaSocietats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Obrir una activity
                    Intent intent = new Intent(getBaseContext(), DetailActivitySocietats.class);
                    Bundle b = new Bundle();
                    b.putSerializable("titular", adapter.getItem(position));
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
            // Tancar la base de dades
            db.close();
        }
        registerForContextMenu(llistaSocietats);
    }

    /**
     * Mètode que recarrega les dades a la llista
     */
    private void refreshData(){
        societatsTaula = sosConv.getAll();
        if (societatsTaula != null) {
            llistaSocietats.setVisibility(llistaSocietats.VISIBLE);
            senseDades.setVisibility(senseDades.INVISIBLE);
            adapter = new SocietatsAdapter(this, societatsTaula);
            llistaSocietats.setAdapter(adapter);
        }else{
            llistaSocietats.setVisibility(llistaSocietats.INVISIBLE);
            senseDades.setVisibility(senseDades.VISIBLE);
            senseDades.setText("No hi ha cap societat");
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
            public boolean onQueryTextChange(String text)
            {
                if (societatsTaula != null) {
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
            Intent intent = new  Intent(this, NovaSocietatActivity.class);
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
        if(requestCode == 1 && resultCode == RESULT_OK) {
            Societats t = (Societats)data.getExtras().getSerializable("dades");
            if (sosConv.save(t)){
                toastAvis = Toast.makeText(getApplicationContext(),
                        "La nova societat s'ha creat correctament", Toast.LENGTH_SHORT);
                toastAvis.show();
            }

            refreshData();
        }
        if(requestCode == 2 && resultCode == RESULT_OK) {
            Societats t = (Societats)data.getExtras().getSerializable("dades");
            if (sosConv.update(t)){
                toastAvis = Toast.makeText(getApplicationContext(),
                        "La societat s'ha modificat correctament", Toast.LENGTH_SHORT);
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
            Intent intent = new Intent(getBaseContext(), NovaSocietatActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("societat", societatsTaula.get(info.position));
            intent.putExtras(b);
            startActivityForResult(intent, 2);
        } else {
            return false;
        }
        return true;
    }

    /**
     * Mètode que demana si s'està segur d'esborrar una societat
     * @param info
     */
    private void dialegAprovacio(final int info) {
        final int POS = info;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Estàs segur que vols esborrar aquesta societat?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(sosConv.remove(societatsTaula.get(POS))){
                    Toast.makeText(getApplicationContext(), "La societat s'ha esborrat correctament", Toast.LENGTH_LONG).show();
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

