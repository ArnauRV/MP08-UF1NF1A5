package net.infobosccoma.romaarnau_activitat1.model;

import java.io.Serializable;

/**
 * Created by Arnau on 06/02/2015.
 */
public class Societats implements Serializable {
    private int codi;
    private String nom;
    private byte[] imgID;
    private byte[] imgCapcalera;
    private String poblacio;
    private String direccio;
    private int numSocis;
    private String president;

    public Societats(int codi, String nom, byte[] imgID, byte[] imgCapcalera, String poblacio, String direccio, int numSocis, String president) {
        this.codi = codi;
        this.nom = nom;
        this.imgID = imgID;
        this.imgCapcalera = imgCapcalera;
        this.poblacio = poblacio;
        this.numSocis = numSocis;
        this.president = president;
        this.direccio = direccio;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public int getNumSocis() {
        return numSocis;
    }

    public void setNumSocis(int numSocis) {
        this.numSocis = numSocis;
    }

    public String getPresident() {
        return president;
    }

    public void setPresident(String president) {
        this.president = president;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }
}