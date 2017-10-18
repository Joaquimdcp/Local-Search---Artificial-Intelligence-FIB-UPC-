package com.company;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolineras;
import com.sun.org.apache.xpath.internal.operations.Bool;
import IA.Gasolina.Gasolinera;

import java.util.ArrayList;


public class Estat {
    static public ArrayList<Distribucion> camion = new ArrayList();
    static public ArrayList<Peticio> peticions = new ArrayList();

    Dada_Camio[] dades_camio;
    boolean[] peticio_atesa;

    //Operadors

    // Intercanvia les peticions entre camions
    public void swap(int c1, int c2, int viatge1, int viatge2, int peticio1, int peticio2){
        // peticio1 i peticio2 es refereixen a quina de les posicions és la que s'ha d'intercanviar la 0 o 1
        Dada_Camio camio1 = dades_camio[c1];
        Dada_Camio camio2 = dades_camio[c2];
        Pair p = dades_camio[c1].get_viatje(viatge1);
        Pair p2 = dades_camio[c1].get_viatje(viatge2);
        int pet = 0;
        if(peticio1==0) {
            pet = p.g1;
        }
        else{
            pet = p.g2;
        }
        int pet1 = 0;
        if(peticio2==0){
            pet1 = p2.g1;
        }
        else{
            pet1 = p2.g2;
        }

        dades_camio[c1].afegir_peticio(pet1,viatge1);
        dades_camio[c2].afegir_peticio(pet, viatge2);
    }

    // Afegeix peticio a un camió
    public void add(int c, int p){
        Dada_Camio camio = dades_camio[c];
        camio.afegir_peticio(p);
    }


}
