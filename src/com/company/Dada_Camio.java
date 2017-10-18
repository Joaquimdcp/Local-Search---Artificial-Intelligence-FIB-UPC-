package com.company;


import IA.Gasolina.CentrosDistribucion;

import java.util.ArrayList;


import static java.lang.Math.abs;

class Pair {
    public int g1, g2;
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

    public int get_peticio(int viatge, int posicio) {
        if return viatges[viatge].g1;
        return
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
                actualitzar_km(id_peticio);
                actualitzar_benefici(id_peticio);
                return true;
            }
            else if (v.g2 == -1) {
                v.g2 = id_peticio;
                int id_pet_prev = v.g1;
                actualitzar_km(id_peticio, id_pet_prev);
                actualitzar_benefici(id_peticio);
            }
        }
        return false;
    }



    //private functions


    private void actualitzar_benefici(int id_peticio) {
        //
    }


    private void actualitzar_km(int id_peticio) {
        int x_gas = Estat.peticions[id_peticio].getX();
        int y_gas = Estat.peticions[id_peticio].getY();
        int x_dis = Estat.camions[id].getCoordX();
        int y_dis = Estat.camions[id].getCoordX();

        km += abs(x_dis - x_gas) + abs(y_dis - y_gas);
    }

    private void actualitzar_km(int id_peticio, int id_pet_prev) {
        int x_gas1 = Estat.peticions[id_peticio].getX();
        int y_gas1 = Estat.peticions[id_peticio].getY();
        int x_gas2 = Estat.peticions[id_pet_prev].getX();
        int y_gas2 = Estat.peticions[id_pet_prev].getY();
        int x_dis = Estat.camions[id].getCoordX();
        int y_dis = Estat.camions[id].getCoordX();

        km += abs(x_gas1 - x_gas2) + abs(y_gas1 - y_gas2) + abs(x_gas2 - x_dis) + abs(y_gas2 - y_dis);
    }





}
