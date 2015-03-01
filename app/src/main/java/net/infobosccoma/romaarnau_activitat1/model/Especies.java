package net.infobosccoma.romaarnau_activitat1.model;

import java.io.Serializable;

/**
 * Created by Arnau on 12/02/2015.
 */
public class Especies implements Serializable {
    private int id;
    private byte[] imgID;
    private byte[] imgCapcalera;
    private String nomEspecie;
    private String familia;
    private String nomCientific;
    private String info;

    public Especies(int id, String nomEspecie, String familia, String nomCientific, byte[] imgID, byte[] imgCapcalera, String info) {
        this.id = id;
        this.nomEspecie = nomEspecie;
        this.familia = familia;
        this.nomCientific = nomCientific;
        this.imgID = imgID;
        this.imgCapcalera = imgCapcalera;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getImgID() {
        return imgID;
    }

    public void setImgID(byte[] imgID) {
        this.imgID = imgID;
    }

    public byte[] getImgCapcalera() {
        return imgCapcalera;
    }

    public void setImgCapcalera(byte[] imgCapcalera) {
        this.imgCapcalera = imgCapcalera;
    }

    public String getNomEspecie() {
        return nomEspecie;
    }

    public void setNomEspecie(String nomEspecie) {
        this.nomEspecie = nomEspecie;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNomCientific() {
        return nomCientific;
    }

    public void setNomCientific(String nomCientific) {
        this.nomCientific = nomCientific;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }
}
