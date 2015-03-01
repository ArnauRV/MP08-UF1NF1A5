package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 06/02/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import net.infobosccoma.romaarnau_activitat1.R;
import net.infobosccoma.romaarnau_activitat1.model.Cursos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LlistaCursosActivity extends ActionBarActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;

    List<String> listDataHeader;
    HashMap<String, List<Cursos>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_cursos);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getBaseContext(), DetailActivityVideo.class);
                Bundle b = new Bundle();
                b.putSerializable("cursos", listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition));
                intent.putExtras(b);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_compartir, menu);
        return true;
    }

    /*
     * Creació de la llista de dades
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Cursos>>();

        // Afegim un fill
        listDataHeader.add("Montatges");
        listDataHeader.add("General");

        // Afegim un fill
        List<Cursos> montatjes = new ArrayList<Cursos>();
        montatjes.add(new Cursos("Montatge de l'elàstic", "En aquest vídeo es pot veure el montantge d'un elàstic a una canya.", "_a3yvMiG1tU"));
        montatjes.add(new Cursos("Com mesurar la profunditat", "En aquest vídeo es pot veure com es mesura la profunditat del riu o pantano amb la modalitat de pesca al Coup", "-YZP2zbnW2Y"));

        List<Cursos> general = new ArrayList<Cursos>();
        general.add(new Cursos("Pesca d'aigua dolça al coup", "En aquest vídeo es pot veure la preparació del material de pesca i l'acció de pesca a la modalitat del Coup. Es poden veure captures de gardons i bremes.", "NQ304J7hERo"));
        general.add(new Cursos("Pesca d'aigua dolça al feeder", "En aquest vídeo es pot veure l'acció de pesca a la modalitat del feeder.", "5exgQR8quvY"));

        listDataChild.put(listDataHeader.get(0), montatjes);
        listDataChild.put(listDataHeader.get(1), general);
    }
}
