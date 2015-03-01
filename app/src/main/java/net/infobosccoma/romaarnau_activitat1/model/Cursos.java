package net.infobosccoma.romaarnau_activitat1.model;

import java.io.Serializable;

/**
 * Created by Arnau on 13/02/2015.
 */
public class Cursos implements Serializable{
    private String nom;
    private String descripcio;
    private String video;

    public Cursos(String nom, String descripcio, String video) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.video = video;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getNom() {
        return nom;

    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
