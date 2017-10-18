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



    //Setters

    public void set_peticio(int id_peticio, int viatge, int pos) {
        act_km_setter(viatge, id_peticio, pos);
        if (pos == 0) { viatges[viatge].g1 = id_peticio; }
        else { viatges[viatge].g2 = id_peticio; }
    }


    //Altres


    //afegeix peticio amb id == id_peticio al viatge v
    //retorna true si s'ha pogut afegir la peticio al viatge i false altrament
    public boolean afegir_peticio(int id_peticio, int v) {
        if (viatges[v].g1 == -1) {
            viatges[v].g1 = id_peticio;
            actualitzar_km(id_peticio);
            return true;
        }
        else if (viatges[v].g2 == -1) {
            viatges[v].g2 = id_peticio;
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
                return true;
            }
            else if (v.g2 == -1) {
                v.g2 = id_peticio;
                int id_pet_prev = v.g1;
                actualitzar_km(id_peticio, id_pet_prev);
            }
        }
        return false;
    }



    //private functions


    private void actualitzar_benefici(int id_peticio) {
        int dies = Estat.peticions[id_peticio].dies;
        int guanys = 0;
        //falta

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


    private void act_km_setter(int viatge, int id_peticio, int pos) {
        int id_peticio1 = viatges[viatge].g1;
        int id_peticio2 = viatges[viatge].g2;

        int x_gas1 = Estat.peticions[id_peticio1].getX();
        int y_gas1 = Estat.peticions[id_peticio1].getY();
        int x_gas2 = Estat.peticions[id_peticio2].getX();
        int y_gas2 = Estat.peticions[id_peticio2].getY();
        int x_dis = Estat.camions[id].getCoordX();
        int y_dis = Estat.camions[id].getCoordX();

        int km_prev = abs(x_dis - x_gas1) + abs(y_dis - y_gas1) + abs(x_gas1 - x_gas2) + abs(y_gas1 - y_gas2) + abs(x_gas2 - x_dis) + abs(y_gas2 - y_dis);

        if (pos == 0) {
            x_gas1 = Estat.peticions[id_peticio].getX();
            y_gas1 = Estat.peticions[id_peticio].getY();
        }
        else {
            x_gas2 = Estat.peticions[id_peticio].getX();
            y_gas2 = Estat.peticions[id_peticio].getY();
        }

        int km_new = abs(x_dis - x_gas1) + abs(y_dis - y_gas1) + abs(x_gas1 - x_gas2) + abs(y_gas1 - y_gas2) + abs(x_gas2 - x_dis) + abs(y_gas2 - y_dis);

        km = km - km_prev + km_new;
    }





}
