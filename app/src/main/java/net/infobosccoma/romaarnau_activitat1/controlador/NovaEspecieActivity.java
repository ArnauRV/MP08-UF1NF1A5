package net.infobosccoma.romaarnau_activitat1.controlador;

/**
 * Created by Arnau on 23/02/2015.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.infobosccoma.romaarnau_activitat1.R;
import net.infobosccoma.romaarnau_activitat1.model.Especies;

import java.io.File;

public class NovaEspecieActivity extends ActionBarActivity {

    private Especies dades;

    private Button btnNovaImatge;
    private Button btnAfegirImatgeCapcalera;

    private ImageView ivVistaImatge;
    private ImageView ivCapcalera;

    private EditText etNomEspecie;
    private EditText etCientific;
    private EditText etFamilia;
    private EditText etDescripcio;

    private boolean imatgeLlist;
    private byte imatgeLlista[];
    private byte imatgeCapcalera[];
    private String selectedImagePath;
    private int idEspecie;

    private Toast toastAvis;

    private static final int CAMERA_REQUEST = 1;
    private static final int PICK_FROM_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_nova_especie);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.selectedImagePath = null;
        this.idEspecie = -1;

        btnNovaImatge = (Button) this.findViewById(R.id.btnAfegirImatge);
        btnAfegirImatgeCapcalera = (Button) this.findViewById(R.id.btnAfegirImatgeCapcalera);
        ivVistaImatge = (ImageView) this.findViewById(R.id.ivVistaImatge);
        ivCapcalera = (ImageView) this.findViewById(R.id.ivCapcalera);
        etNomEspecie = (EditText) this.findViewById(R.id.etNomEspecieNova);
        etCientific = (EditText) this.findViewById(R.id.etNomCientific);
        etFamilia = (EditText) this.findViewById(R.id.etFamilia);
        etDescripcio = (EditText) this.findViewById(R.id.etDescripcio);

        // Si hem fet clic a editar, carreguem les dades de l'espècie
        // al seu lloc.
        if (getIntent() != null && getIntent().getExtras() != null) {
            dades = (Especies) getIntent().getExtras().getSerializable("especie");

            setTitle("Modificar espècie");
            idEspecie = dades.getId();
            imatgeLlista = dades.getImgID();
            imatgeCapcalera = dades.getImgCapcalera();
            etNomEspecie.setText(dades.getNomEspecie());
            ivVistaImatge.setImageBitmap(Utilitats.getPhoto(imatgeLlista));
            ivCapcalera.setImageBitmap(Utilitats.getPhoto(imatgeCapcalera));
            etFamilia.setText(dades.getFamilia());
            etCientific.setText(dades.getNomCientific());
            etDescripcio.setText((String.valueOf(dades.getInfo())));
        }

        // Creació del diàleg per seleccionar una imatge
        final String[] opcions = new String[]{"Obtenir des de càmara",
                "Seleccionar de la galeria"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, opcions);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escull el mètode de selecció");

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int opcio) {
                if (opcio == 0) {
                    camera();
                }
                if (opcio == 1) {
                    galeria();
                }

            }
        });

        final AlertDialog dialog = builder.create();

        btnNovaImatge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imatgeLlist = true;
                dialog.show();
            }
        });

        btnAfegirImatgeCapcalera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imatgeLlist = false;
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_noves_soc_es, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // Si tots els camps estan omplerts, retornem l'objecte societat, sinó es mostra
        // un missatge avís.
        if (id == R.id.action_acceptar) {
            Intent i = new Intent();
            Bundle b = new Bundle();
            if (etNomEspecie.getText().toString().equals("") || etCientific.getText().toString().equals("") ||
                    etFamilia.getText().toString().equals("") || etDescripcio.getText().toString().equals("") ||
                    imatgeLlista == null || imatgeCapcalera == null) {
                toastAvis = Toast.makeText(getApplicationContext(),
                        "S'han d'omplir tots els camps", Toast.LENGTH_SHORT);
                toastAvis.show();
            } else {
                Especies soc = new Especies(idEspecie, etNomEspecie.getText().toString(), etFamilia.getText().toString(),
                        etCientific.getText().toString(), imatgeLlista, imatgeCapcalera,
                        etDescripcio.getText().toString());

                b.putSerializable("dades", soc);
                i.putExtras(b);
                setResult(RESULT_OK, i);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Mètode que recull el resultat de l'activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CAMERA_REQUEST:

                // Creació de l'arxiu de foto per poder agafar el seu path i poder treballa amb aquesta
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");

                if (imatgeLlist) {
                    Bitmap bitmap = Utilitats.decodeSampledBitmapFromFile(file.getAbsolutePath(), 200, 450);
                    imatgeLlista = Utilitats.getBytes(bitmap);
                    ivVistaImatge.setImageBitmap(bitmap);

                } else {
                    Bitmap bitmap = Utilitats.decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 600);
                    imatgeCapcalera = Utilitats.getBytes(bitmap);
                    ivCapcalera.setImageBitmap(bitmap);
                }
                // Finalment, un cop tenim la imatge a bytes, l'eliminem.
                file.delete();
                break;
            case PICK_FROM_GALLERY:

                selectedImagePath = Utilitats.getAbsolutePath(getContentResolver(),data.getData());

                if (imatgeLlist) {
                    Bitmap bitmap = Utilitats.decodeSampledBitmapFromFile(selectedImagePath, 200, 450);
                    imatgeLlista = Utilitats.getBytes(bitmap);
                    ivVistaImatge.setImageBitmap(bitmap);
                } else {
                    Bitmap bitmap = Utilitats.decodeSampledBitmapFromFile(selectedImagePath, 800, 550);
                    imatgeCapcalera = Utilitats.getBytes(bitmap);
                    ivCapcalera.setImageBitmap(bitmap);
                }
                break;
        }
    }

    /**
     * Mètode per obrir la càmera
     */
    public void camera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    /**
     * Mètode per obrir la galeria
     */
    public void galeria() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_GALLERY);

    }
}
