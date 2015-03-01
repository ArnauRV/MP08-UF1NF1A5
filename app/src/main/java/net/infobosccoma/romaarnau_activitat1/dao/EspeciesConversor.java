package net.infobosccoma.romaarnau_activitat1.dao;

/**
 * Created by Arnau on 19/02/2015.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.infobosccoma.romaarnau_activitat1.model.Especies;

import java.util.ArrayList;

public class EspeciesConversor {

    private PescaOsonaSQLiteHelper helper;

    /**
     * Consructor per defecte
     */
    public EspeciesConversor() {
    }

    /**
     * Constructor amb paràmetres
     * @param helper l'ajudant de la BD de Especies
     */
    public EspeciesConversor(PescaOsonaSQLiteHelper helper) {
        this.helper = helper;
    }

    /**
     * Desa una nova espècie a la taula
     * @param especie espècie a desar
     * @return true si s'ha desat correctament
     */
    public boolean save(Especies especie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dades = new ContentValues();

        dades.put("nomEspecie", especie.getNomEspecie());
        dades.put("familia", especie.getFamilia());
        dades.put("nomCientific", especie.getNomCientific());
        dades.put("imgID", especie.getImgID());
        dades.put("imgCapcalera", especie.getImgCapcalera());
        dades.put("info", especie.getInfo());

        try {
            db.insertOrThrow("Especies", null, dades);
            db.close();
            return true;
        }
        catch(Exception e) {
            Log.e("Especie", e.getMessage());
        }
        db.close();
        return false;
    }

    /**
     * Mètode per fer un select de tota la taula Especies
     * @return una llista amb totes les dades de la taula Especies
     */
    public ArrayList<Especies> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true, "Especies",
                new String[]{"id","nomEspecie","familia","nomCientific","imgID","imgCapcalera","info"},
                null, null, null, null, null, null);
        ArrayList<Especies> llista = null;

        if (c.moveToFirst()) {
            llista = new ArrayList<Especies>();
            do {
                llista.add(new Especies(c.getInt(0), c.getString(1),
                        c.getString(2), c.getString(3), c.getBlob(4),
                        c.getBlob(5), c.getString(6)));
            } while (c.moveToNext());
        }
        db.close();
        return llista;
    }

    /**
     * Fa l'update d'una espècie a partir de l'id d'aquesta
     * @param especie espècie que s'ha de modificar
     * @return true si l'update s'ha efectuat correctament
     */
    public boolean update(Especies especie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dades = new ContentValues();  // Emmagatzema objectes en forma clau valor

        dades.put("nomEspecie", especie.getNomEspecie());
        dades.put("familia", especie.getFamilia());
        dades.put("nomCientific", especie.getNomCientific());
        dades.put("imgID", especie.getImgID());
        dades.put("imgCapcalera", especie.getImgCapcalera());
        dades.put("info", especie.getInfo());

        try {
            db.update("Especies", dades, "id " + "=" + especie.getId(), null);;
            db.close();
            return true;
        }
        catch(Exception e) {
            Log.e("Especie", e.getMessage());
        }
        db.close();
        return false;
    }

    /**
     * Esborra l'espècie passada per paràmetre
     * @param especie l'espècie a esborrar
     * @return true si l'espècie s'ha eliminat correctament
     */
    public boolean remove(Especies especie) {
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete("Especies", "id=" + especie.getId(),null ) > 0;
    }
}