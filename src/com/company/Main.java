package com.company;


import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

public class Main {

    public static void main(String[] args) {
        int seed = 0; // Seed per a generar num aleatori
        int n_gas = 5; //Numero de gasolineres
        int n_trucks = 3; // Numero de camions
        int n_centros = 2;


        // Init les Gasolineres
        Gasolineras gasolineras = new Gasolineras(n_gas,seed);
        int id = 0;
        for(Gasolinera d: gasolineras){
            System.out.print("Benzinera G" + id + ": " + d.getPeticiones() +  "\n"); // Printem les peticions que tenim pendents
            id ++;
        }


        // Init els centres de distribució
        CentrosDistribucion centros_dist = new CentrosDistribucion(n_centros,n_trucks,seed);
        id = 0;
        for(Distribucion dist: centros_dist){
            System.out.print("Camió C" + id + " situat a: X=" + dist.getCoordX() + " Y=" + dist.getCoordY() + " \n");
            id ++;
        }

    }
}
