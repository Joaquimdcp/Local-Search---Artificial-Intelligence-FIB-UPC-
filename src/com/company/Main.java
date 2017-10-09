package com.company;


import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;

public class Main {

    public static void main(String[] args) {
        int seed = 0;
        int n_gas = 5;
        Gasolineras g = new Gasolineras(n_gas,seed);

        // Printem les peticions que tenim pendents
        int id = 0;
        for(Gasolinera d: g){
            System.out.print("Gasolinera " + id + ": " + d.getPeticiones() +  "\n");
            id ++;
        }
    }
}
