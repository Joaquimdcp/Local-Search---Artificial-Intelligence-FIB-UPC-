package com.company;


import IA.Gasolina.CentrosDistribucion;

import java.util.ArrayList;


import static java.lang.Math.abs;

class Pair {
    public int g1, g2;
}


public class Dada_Camio {

    private int id;
    private double benefici;
    private int km;
    private Pair[] viatges = new Pair[5];


    //Constructor
    public Dada_Camio(int id) {
        this.id = id;
        benefici = 0;
        km = 0;
        int i = 0;
        for (Pair v: viatges) {
            v = new Pair();
            v.g1 = -1;
            v.g2 = -1;
            viatges[i] = v;
            i++;
        }
    }


    //Getters

    public double get_benefici() {
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

    //reemplacem el viatge complet per un que només serveix una petició concreta
    public boolean replace_viatge(int id_peticio, int viatge){
        Pair antic_viatge = viatges[viatge];
        int antic_km = calculate_km(antic_viatge);
        double antic_benefici = calculate_benefici(antic_viatge, antic_km);
        Pair nou_viatge = new Pair();
        nou_viatge.g1 = id_peticio;
        nou_viatge.g2 = -1;
        int now_km = calculate_km(nou_viatge);
        int result_km = km + now_km - antic_km;
        double now_benefici = calculate_benefici(nou_viatge, result_km);
        double result_benefici = now_benefici- antic_benefici;

        if(result_km<=640) {
            km = result_km;
            viatges[viatge].g1 = id_peticio;
            viatges[viatge].g2 = -1;
            benefici += result_benefici;
            return true;
        }
        return false;


    }


    public boolean swap_peticions(int v, int pos, int peticio){
        Pair antic_viatge = viatges[v];
        int antic_km = calculate_km(antic_viatge);
        double antic_benefici = benefici;

        Pair nou_viatge = new Pair();
        if(pos==0) {
            nou_viatge.g1 = antic_viatge.g1;
            nou_viatge.g2 = peticio;
        }
        else{
            nou_viatge.g1 = peticio;
            nou_viatge.g2 = antic_viatge.g2;
        }
        int nou_km = calculate_km(nou_viatge);
        float now_benefici = calculate_benefici(nou_viatge, nou_km);

        int result_km = km + nou_km - antic_km;
        double result_benefici = now_benefici-antic_benefici;


        if(result_km<=640 && result_benefici>0) {
            km = result_km;
            benefici += result_benefici;
            viatges[v] = nou_viatge;
            return true;
        }
        return false;
    }



    /**
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
     **/


    private int calculate_km(Pair viatge){
        int x_gas,y_gas,x_dis,y_dis;
        int km_viatge = 0;
        if (viatge.g1 != -1){
            x_gas = Estat.peticions[viatge.g1].getX();
            y_gas = Estat.peticions[viatge.g1].getY();
            x_dis = Estat.camions[id].getCoordX();
            y_dis = Estat.camions[id].getCoordY();
            km += 2*(abs(x_dis - x_gas) + abs(y_dis - y_gas));
        }
        if (viatge.g2 != -1){
            x_gas = Estat.peticions[viatge.g2].getX();
            y_gas = Estat.peticions[viatge.g2].getY();
            x_dis = Estat.camions[id].getCoordX();
            y_dis = Estat.camions[id].getCoordY();
            km += 2*(abs(x_dis - x_gas) + abs(y_dis - y_gas));
        }
        if (viatge.g1 != -1 && viatge.g2 != -1 ){
            x_gas = Estat.peticions[viatge.g2].getX();
            y_gas = Estat.peticions[viatge.g2].getY();
            x_dis = Estat.peticions[viatge.g1].getX();
            y_dis = Estat.peticions[viatge.g1].getY();
            km += (abs(x_dis - x_gas) + abs(y_dis - y_gas));
        }

        return km_viatge;
    }

    private float calculate_benefici(Pair viatge, int km){
        int peticions_ateses = 0;
        float acomultat = 0;
        if(viatge.g1!=-1) {
            int peticio_dies = Estat.peticions[viatge.g1].dies;
            int penalitza = 100 - (int) Math.pow(2, peticio_dies);
            double percentatge = (double)penalitza/100.00;
            double resultat = (float) 1000*percentatge;
            ;
            acomultat += resultat;

        }
        if(viatge.g2!=-1){
            int peticio_dies = Estat.peticions[viatge.g2].dies;
            int penalitza = 100 - (int) Math.pow(2, peticio_dies);
            double percentatge = (double)penalitza/100.00;
            double resultat = (float) 1000*percentatge;
            acomultat += resultat;
        }
        //acomultat -= km*2;
        return (acomultat);
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
