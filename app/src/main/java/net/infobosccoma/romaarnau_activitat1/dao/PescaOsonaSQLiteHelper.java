package net.infobosccoma.romaarnau_activitat1.dao;

/**
 * Created by Arnau on 19/02/2015.
 */

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import net.infobosccoma.romaarnau_activitat1.controlador.Utilitats;

import java.io.IOException;
import java.io.InputStream;


public class PescaOsonaSQLiteHelper extends SQLiteOpenHelper {

    // Sentència SQL per crear la taula societats
    private final String SQL_CREATE_SOCIETATS = "CREATE TABLE Societats (" +
            "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
            "nom VARCHAR, " +
            "imgID BLOB, " +
            "imgCapcalera BLOB, " +
            "poblacio VARCHAR, " +
            "numSocis INTEGER, " +
            "president VARCHAR, " +
            "direccio VARCHAR)";

    // Sentència SQL per crear la taula societats
    private final String SQL_CREATE_ESPECIES = "CREATE TABLE Especies (" +
            "id INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL , " +
            "nomEspecie VARCHAR, " +
            "familia VARCHAR, " +
            "nomCientific VARCHAR, " +
            "imgID BLOB, " +
            "imgCapcalera BLOB, " +
            "info TEXT)";

               /*"CREATE TABLE Titulars(" +
            "	codi INTEGER PRIMARY KEY, " +
            "	titol TEXT, " +
            "	subtitol TEXT)";*/

    /**
     * Constructor amb paràmetres
     * @param context el context de l'aplicació
     * @param nom el nom de la base de dades a crear
     * @param factory s'utilitza per crear objectes cursor, o null per defecte
     * @param versio número de versió de la BD. Si és més gran que la versió actual, es farà un
     * 				 Upgrade; si és menor es farà un Downgrade
     */
    Context context;
    public PescaOsonaSQLiteHelper(Context context) {
        super(context, BDDadesConnexio.NOM_BD, null, BDDadesConnexio.VERSIO);
        this.context = context;
    }

    /**
     * Event que es produeix quan s'ha de crear la BD
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // S'executen les sentències SQL de creació de la BD
        db.execSQL(SQL_CREATE_SOCIETATS);
        executaInsertsSocietats(db);
        db.execSQL(SQL_CREATE_ESPECIES);
        executaInsertsEspecies(db);
    }

    private void executaInsertsSocietats(SQLiteDatabase db){
        String sqlSocietats = "Insert into Societats (nom,imgID,imgCapcalera,poblacio,numSocis,president,direccio) values (?,?,?,?,?,?,?)";
        SQLiteStatement insertSocietats      =   db.compileStatement(sqlSocietats);

        // Inserts Societats
        insertSocietats.clearBindings();
        insertSocietats.bindString(1, "Societat de Pescadors de Roda de Ter");
        insertSocietats.bindBlob(2, Utilitats.getBytes(convertImage("icona_rodater.jpg")));
        insertSocietats.bindBlob(3, Utilitats.getBytes(convertImage("capcalera_roda.jpg")));
        insertSocietats.bindString(4, "Roda de Ter");
        insertSocietats.bindLong(5, 150);
        insertSocietats.bindString(6, "Xavier Carreras");
        insertSocietats.bindString(7,"Ctra. Barcelona, 12");
        insertSocietats.executeInsert();

        insertSocietats.clearBindings();
        insertSocietats.bindString(1, "Societat de Pescadors Esportius de Manlleu");
        insertSocietats.bindBlob(2, Utilitats.getBytes(convertImage("icona_manlleu.jpg")));
        insertSocietats.bindBlob(3, Utilitats.getBytes(convertImage("capcalera_manlleu.jpg")));
        insertSocietats.bindString(4, "Manlleu");
        insertSocietats.bindLong(5, 130);
        insertSocietats.bindString(6, "Sr. Francesc Garcia Balmes");
        insertSocietats.bindString(7,"Avda. Puigmal, 40");
        insertSocietats.executeInsert();

        insertSocietats.clearBindings();
        insertSocietats.bindString(1, "Associació de Pescadors Esportius Torelló");
        insertSocietats.bindBlob(2, Utilitats.getBytes(convertImage("icona_torello.jpg")));
        insertSocietats.bindBlob(3, Utilitats.getBytes(convertImage("capcalera_torello.jpg")));
        insertSocietats.bindString(4, "Torelló");
        insertSocietats.bindLong(5, 94);
        insertSocietats.bindString(6, "Javier Expósito Vallejo");
        insertSocietats.bindString(7,"Passatge dels Esports");
        insertSocietats.executeInsert();

        insertSocietats.clearBindings();
        insertSocietats.bindString(1, "Societat de Pescadors Esportius l'Esquirol");
        insertSocietats.bindBlob(2, Utilitats.getBytes(convertImage("icona_esquirol.jpg")));
        insertSocietats.bindBlob(3, Utilitats.getBytes(convertImage("capcalera_esquirol.jpg")));
        insertSocietats.bindString(4, "Santa Maria de Corcó");
        insertSocietats.bindLong(5, 88);
        insertSocietats.bindString(6, "Sr. Albert Marcé Riera");
        insertSocietats.bindString(7,"C/ Major, 61");
        insertSocietats.executeInsert();

        insertSocietats.close();
    }

    private void executaInsertsEspecies(SQLiteDatabase db){
        String sqlEspecies = "Insert into Especies (nomEspecie, familia, nomCientific, imgID, imgCapcalera, info) values (?,?,?,?,?,?)";
        SQLiteStatement insertEspecies      =   db.compileStatement(sqlEspecies);

        // Inserts Especies
        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Carpa comú");
        insertEspecies.bindString(2, "Ciprínids");
        insertEspecies.bindString(3, "Cyprinus carpio");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_carpa.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_carpa.jpg")));
        insertEspecies.bindString(6, "És un peix robust i de mida gran, que pot arribar a fer més d'1 m de longitud i sobrepassar els 30 kg de pes.\n" +
                "\n" + "La forma del cos és molt variable, en funció parcialment del tipus d'ambient aquàtic ocupat, tot i que " +
                "en general tendeix a ésser alt i comprimit. Les escates són grosses i la pell té tons daurats. L'aleta dorsal " +
                "és molt llarga, i ocupa la meitat del cos, té un perfil còncau i un primer radi molt dur i serrat. La seva " +
                "corpulència i la longitud de l'aleta dorsal permeten identificar fàcilment la carpa des de fora de l'aigua. " +
                "Presenta dos parells de barbes al voltant de la boca, el primer d'ells molt curt. Els individus juvenils tenen " +
                "una taca fosca a la base de l'aleta caudal, que es perd als pocs mesos de vida. Presenta una espina dorsal " +
                "serrada característica i les seves escames són llargues i fines. Els mascles tenen l'aleta ventral més llarga " +
                "que les femelles. El color i la mida és molt variable, especialment en els exemplars domèstics.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Barb de muntanya");
        insertEspecies.bindString(2, "Ciprínids");
        insertEspecies.bindString(3, "Barbus meridionalis");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_barb_muntanya.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_barb.jpg")));
        insertEspecies.bindString(6, "Barb de mida petita, que assoleix mides màximes de fins a 230 mm de longitud\n" +
                "forcal en el cas de les femelles i fins a 170 mm en els mascles. El cap és\n" +
                "relativament petit i la boca presenta uns llavis generalment amples, amb un lòbul\n" +
                "medial patent. Els bigotis, típics dels ciprínids, són curts i fins. L’últim radi de\n" +
                "l’aleta dorsal no presenta denticulacions. Les aletes pectorals estan en una\n" +
                "posició baixa i la dorsal té un perfil convexe. La coloració de fons del cos en els\n" +
                "adults és marronosa o grisenca i tant els joves com els adults estan profusament\n" +
                "recoberts de taques de color negre. Existeixen tubercles nupcials en els\n" +
                "mascles d’aquesta espècie.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Ablet");
        insertEspecies.bindString(2, "Ciprínids");
        insertEspecies.bindString(3, "Alburnus alburnus");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_ablet.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_ablet.jpg")));
        insertEspecies.bindString(6, "Espècie de mida petita que sol assolir 15 cm de longitud, amb una talla màxima de 25 cm. " +
                "Pot arribar a pesar un màxim de 100g.\n" + "\n" + "El cos és allargat i comprimit lateralment. Té una boca súpera. " +
                "El peduncle caudal és llarg i estret. L'aleta dorsal és curta amb 8 radis ramificats i l'aleta anal és llarga " +
                "amb 16-19 radis ramificats. Les escates són grans. Tot el cos és de color platejat mentre que les escates són de " +
                "color transparent. Sota les escates dominen els colors verdosos.\n" + "\n" + "La maduresa sexual arriba als " +
                "2 anys de vida tant de mascles com de femelles. El mascle i la femella es poden diferenciar principalment en " +
                "l’època d’aparellament ja que el mascle presenta uns petits grans al cap que li desapareixen dies després de " +
                "la reproducció.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Bagra");
        insertEspecies.bindString(2, "Ciprínids");
        insertEspecies.bindString(3, "Squalius laietanus");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_bagra.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_bagra.jpg")));
        insertEspecies.bindString(6, "La bagra és un peix de cos fusiforme i robust, amb exemplars adults de fins a 60cm de longitud " +
                "total i 8 kg de pes. De color platejat uniforme, les vores fosques de les escates li confereixen un aspecte reticulat. " +
                "El cap és gran i s'obre en una boca terminal. L'aleta dorsal es troba retardada pel que fa a la meitat del cos, i igual " +
                "que l'aleta cabal, és més fosca que la resta del cos. En alguns exemplars les aletes pelvianes i anal són vermelloses. " +
                "El peduncle caudal és llarg i estret i durant el zel els mascles presenten tubercles nupcials.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Gardó");
        insertEspecies.bindString(2, "Ciprínids");
        insertEspecies.bindString(3, "Scardinius erythrophthalmus");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_gardo.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_gardo.jpg")));
        insertEspecies.bindString(6, "El gardó és una espècie de mida petita que sol assolir 20-25 cm de longitud. Pot arribar a pesar " +
                "un màxim de 200g. És un peix amb el cos comprimit dorsalateralment, gairebé en forma de disc en els exemplars més grossos," +
                " i de tons platejats o daurats. Les aletes, sobretot les pèlviques, anal i caudal, són d'un brillant color vermell, caràcter " +
                "que permet identificar aquesta espècie des de fora de l'aigua amb certa facilitat. Tot el cos és de color daurat brillant, " +
                "les aletes en canvi són de color vermell fort. Els mascles maduren sexualment entre el primer i el quart anys de vida mentre" +
                " que les femelles ho fan entre els dos i els cinc anys de vida.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Gat");
        insertEspecies.bindString(2, "Ictalúrids");
        insertEspecies.bindString(3, "Ameiurus melas");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_gat.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_gat.jpg")));
        insertEspecies.bindString(6, "El peix gat és un peix petit, que pot arribar a mesurar 40 cm de llargada. El pes mitjà de " +
                "l’espècie és de 400g. El cos del peix gat no té escates, però en el se lloc presenta mucositats en tot el cos. El " +
                "que més destaca del gat és la gran diferència de mida entre el cap i la boca. Al voltant de la boca i té quatre parells " +
                "de barbes que li permeten alimentar-se, ja que amb ells i detecta el menjar. Té dues aletes pectorals (una per banda) les" +
                " quals tenen el primer radi fort, que els permet defensar-se injectant una petita dosis de toxines a l’enemic. Just darrere" +
                " de les brànquies hi ha les aletes pectorals. No té escates i la pell presenta un mucus protector. Tot el cos és de color" +
                " marronós negre excepte el ventre que és de color blanquinós groguenc. Els mascles són més prims i allargats, en canvi les" +
                " femelles són més amples i més grans. La maduresa sexual arriba als dos anys d’edat.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Gobi");
        insertEspecies.bindString(2, "Ciprínids");
        insertEspecies.bindString(3, "Gobio lozanoi");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_gobi.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_gobio.jpg")));
        insertEspecies.bindString(6, "El gobi ibèric (Gobio lozanoi) és un peix de la família dels ciprínids que habita en els rius d'Espanya, Portugal i l'extrem " +
                "sud-occidental de França.\n" + "\n" + "Adult registra una longitud entre 8 i 20 cm; cos i aletes de fons clar brillant amb taques i punts foscos. Té un " +
                "parell de barbillones bucals.\n" + "\n" + "Captura invertebrats en el fons de les lleres. Completa la seva dieta amb matèria orgànica d'origen divers. " +
                "Viu en el curs mitjà dels rius. Per a la reproducció requereix aigües corrents sobre fons de sorra o grava sense sediments. Cada estiu la femella fresa entre " +
                "mil i 20 mil ous que eclosionen 10 a 30 dies després, segons la temperatura de l'aigua.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Perca sol");
        insertEspecies.bindString(2, "Pércidos");
        insertEspecies.bindString(3, "Lepomis gibbosus");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_perca_sol.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_sol.jpg")));
        insertEspecies.bindString(6, "És un peix de cos alt però comprimit lateralment, amb una petita gepa situada a prop de la cua. " +
                "Mesura d’uns 20cm a uns 40cm de llargada. El pes màxim al qual pot arribar és de 630g. Té una única aleta dorsal, amb " +
                "radis espinosos a la part anterior i de tous ramificats en la posterior, amb una gran depressió en el centre. Les " +
                "aletes pectorals són llargues i punxegudes. L’aleta dorsal està formada per 10 o 12 espines, i la segona està formada " +
                "per un nombre igual de radis tous. Compta amb 3 espines a l'aleta anal seguides de 8 a 11 radis tous. Té una boca " +
                "petita. Destaca molt a la vista la forma de l'opercle (tapa branquial), que té un extrem sortint arrodonit de grans " +
                "dimensions sobre les aletes pectorals, que li dóna una aparença de \"peix amb orelles\". En els laterals presenta " +
                "dibuixos sinuosos formant fileres de color verd turquesa i taronges, el ventre és també de tons ataronjats. La " +
                "coloració a la zona ventral és taronja o groga. Té uns tons de verd blaus i taronges, el ventre és de color ataronjat." +
                " Un dels tons més destacats és una taca negra que està envoltada de vermell. Els mascles tenen més visible les " +
                "“orelles” que les femelles, ja que les dels mascles són més fosques. Per darrere dels opercles presenten una evident " +
                "taca negra envoltada de vermell en els mascles i de taronja en les femelles. Els mascles són de colors més vistosos " +
                "que les femelles. Arriben a la maduresa sexual als tres anys de vida.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Silur");
        insertEspecies.bindString(2, "Siluridae");
        insertEspecies.bindString(3, "Silurus glanis");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_silur.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_silur.jpg")));
        insertEspecies.bindString(6, "Presenten el cos allargat i aixafat típic de l'ordre Siluriformes. Poden arribar a tenir enorme grandària," +
                " amb una longitud màxima descrita per l'anomenat silur de 5 metres i 330 kg de pes corporal.\n" + "\n" + "A més, " +
                "l'aleta dorsal pot no tenir ràdios o tenir menys de set i sempre sense espines; no tenen aleta adiposa, les " + "aletes " +
                "pectorals són petites o absents, la base de l'aleta anal és molt llarga amb entre 40 i 110 ràdios; no presenten barbes" +
                " nasals, però tenen un o dos parells d'elles a la mandíbula inferior que solen ser extremadament llargues.");
        insertEspecies.executeInsert();

        insertEspecies.clearBindings();
        insertEspecies.bindString(1, "Tenca");
        insertEspecies.bindString(2, "Ciprínids");
        insertEspecies.bindString(3, "Tinca tinca");
        insertEspecies.bindBlob(4, Utilitats.getBytes(convertImage("peix_tenca.jpg")));
        insertEspecies.bindBlob(5, Utilitats.getBytes(convertImage("capcalera_tenca.jpg")));
        insertEspecies.bindString(6, "Presenten el cos allargat i aixafat típic de l'ordre Siluriformes. Poden arribar a tenir enorme grandària," +
                " amb una longitud màxima descrita per l'anomenat silur de 5 metres i 330 kg de pes corporal.\n" + "\n" + "A més, " +
                "l'aleta dorsal pot no tenir ràdios o tenir menys de set i sempre sense espines; no tenen aleta adiposa, les " + "aletes " +
                "pectorals són petites o absents, la base de l'aleta anal és molt llarga amb entre 40 i 110 ràdios; no presenten barbes" +
                " nasals, però tenen un o dos parells d'elles a la mandíbula inferior que solen ser extremadament llargues.");

        insertEspecies.executeInsert();
        insertEspecies.close();
    }

    private Bitmap convertImage(String path){
        Bitmap image = null;
        AssetManager assetManager = this.context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(path);
            image = BitmapFactory.decodeStream(istr);
            System.out.println(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    /**
     * Event que es produeix quan s'ha d'actualitzar la BD a una versió superior
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int versioAnterior, int versioNova) {

    }
}