package com.company;


import java.util.ArrayList;

class Pair {
    int g1, g2;
}


public class Dada_Camio {

    private int id;
    private int benefici;
    private int km;
    private Pair[] viatges = new Pair[5];


    //Constructor
    public Dada_Camio(int id) {
        this.id = id;
        benefici = 0;
        km = 0;
        for (Pair v: viatges) {
            v.g1 = -1;
            v.g2 = -1;
        }
    }


    //Getters

    public int get_benefici() {
        return benefici;
    }


    public int get_km() {
        return km;
    }


    public Pair get_viatje(int i) {
        return viatges[i];
    }


    //afegeix peticio amb id == id_peticio al viatge v
    //retorna true si s'ha pogut afegir la peticio al viatge i false altrament
    public boolean afegir_peticio(int id_peticio, int v) {
        if (viatges[v].g1 == -1) {
            viatges[v].g1 = id_peticio;
            actualitzar_benefici(id_peticio);
            actualitzar_km(id_peticio);
            return true;
        }
        else if (viatges[v].g2 == -1) {
            viatges[v].g2 = id_peticio;
            actualitzar_benefici(id_peticio);
            actualitzar_km(id_peticio);
            return true;
        }
        return false;
    }


    //afegeix peticio amb id = id_peticio al primer viatge amb espai per a peticio
    //retorna true si s'ha pogut afegir i false altrament
    public boolean afegir_peticio(int id_peticio) {
        for (Pair v : viatges) {
            if (v.g1 == -1) {
                v.g1 = id_peticio;
                actualitzar_benefici(id_peticio);
                actualitzar_km(id_peticio);
                return true;
            }
            else if (v.g2 == -1) {
                v.g2 = id_peticio;
                actualitzar_benefici(id_peticio);
                actualitzar_km(id_peticio);
            }
        }
        return false;
    }



    //private functions


    private void actualitzar_benefici(int id_peticio) {
        //falta implementar
    }


    private void actualitzar_km(int id_peticio) {
        //falta implementar
    }





}
