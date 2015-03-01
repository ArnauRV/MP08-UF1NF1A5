package net.infobosccoma.romaarnau_activitat1.dao;

/**
 * Created by Arnau on 19/02/2015.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.infobosccoma.romaarnau_activitat1.model.Societats;

import java.util.ArrayList;

public class SocietatsConversor {

    private PescaOsonaSQLiteHelper helper;

    /**
     * Consructor per defecte
     */
    public SocietatsConversor() {
    }

    /**
     * Constructor amb paràmetres
     * @param helper l'ajudant de la BD de Societats
     */
    public SocietatsConversor(PescaOsonaSQLiteHelper helper) {
        this.helper = helper;
    }

    /**
     * Desa una nova Societat a la taula
     * @param societats la societat que s'ha de guardar
     * @return true si s'ha emmagatzemat correctament
     */
    public boolean save(Societats societats) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues dades = new ContentValues();

        dades.put("nom", societats.getNom());
        dades.put("imgID", societats.getImgID());
        dades.put("imgCapcalera", societats.getImgCapcalera());
        dades.put("poblacio", societats.getPoblacio());
        dades.put("numSocis", societats.getNumSocis());
        dades.put("president", societats.getPresident());
        dades.put("direccio", societats.getDireccio());

        try {
            db.insertOrThrow("Societats", null, dades);
            db.close();
            return true;
        }
        catch(Exception e) {
            Log.e("Societats", e.getMessage());
        }
        db.close();
        return false;
    }

    /**
     * Mètode que selecciona totes les dades de la taula Societats
     * @return retorna una llista amb totes les dades de la taula
     */
    public ArrayList<Societats> getAll() {
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(true, "Societats",
                new String[]{"id","nom","imgID","imgCapcalera","poblacio","numSocis","president","direccio"},
                null, null, null, null, null, null);
        ArrayList<Societats> llista = null;

        if (c.moveToFirst()) {
            llista = new ArrayList<Societats>();
            do {
                llista.add(new Societats(c.getInt(0), c.getString(1),
                        c.getBlob(2), c.getBlob(3), c.getString(4),
                        c.getString(7), c.getInt(5), c.getString(6)));
            } while (c.moveToNext());
        }
        db.close();
        return llista;
    }

    /**
     * Fa l'update d'una Societat a partir de l'id d'aquesta
     * @param societat la societat que s'ha de modificar
     * @return true si l'update s'ha efectuat correctament
     */
    public boolean update(Societats societat){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues soc = new ContentValues();
        soc.put("nom",societat.getNom());
        soc.put("imgID",societat.getImgID());
        soc.put("imgCapcalera",societat.getImgCapcalera());
        soc.put("direccio",societat.getDireccio());
        soc.put("poblacio",societat.getPoblacio());
        soc.put("numSocis",societat.getNumSocis());
        soc.put("president",societat.getPresident());

        try {
            db.update("Societats", soc, "id " + "=" + societat.getCodi(), null);
            db.close();
            return true;
        }catch (Exception e){
            Log.i("Societats", e.getMessage());
        }
        db.close();
        return false;
    }

    /**
     * Esborra una societat passada per paràmetre
     * @param societat la societat a esborrar
     * @return true si s'ha esborrat correctament
     */
    public boolean remove(Societats societat) {
        // obtenir l'objecte BD en mode esriptura
        SQLiteDatabase db = helper.getWritableDatabase();

        return db.delete("Societats", "id=" + societat.getCodi(),null ) > 0;  // s'hauria de possar un interrogant per evitar sqlInjection. Això tornarà true si s'han esborrat més de 0 files
    }
}