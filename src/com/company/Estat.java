package com.company;

import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import com.sun.org.apache.xpath.internal.operations.Bool;
import IA.Gasolina.Gasolinera;

import java.util.ArrayList;
import com.company.Peticio;


public class Estat {
    static public Distribucion[] camions;
    static public Peticio[] peticions;

    Dada_Camio[] dades_camio;
    boolean[] peticio_atesa;

    public Estat(Dada_Camio[] dades_camio, boolean[] peticio_atesa) {
        this.dades_camio = dades_camio;
        this.peticio_atesa = peticio_atesa;
    }

    // Afegeix peticio fantasma
    public boolean replace(int c, int v, int p){
        Pair viatge = dades_camio[c].get_viatje(v);
        if(viatge.g2 != -1) peticio_atesa[viatge.g2] = false;
        if(viatge.g1 != -1) peticio_atesa[viatge.g1] = false;
        if(p!=-1) peticio_atesa[p] = true;
        return (dades_camio[c].replace_viatge(p,v));
    }

    public boolean swap_entre_viatges(int c, int v, int p, int n){
        Pair viatge = dades_camio[c].get_viatje(v);
        if(p==1 && viatge.g2!=-1) peticio_atesa[viatge.g2] = false;
        else if(viatge.g1!=-1) peticio_atesa[viatge.g1] = false;
        peticio_atesa[n] = true;
        return (dades_camio[c].swap_peticions(v,p,n));
    }

    public double heuristic(){
        int total = 0;
        for(int i=0; i<dades_camio.length; ++i){
            total += dades_camio[i].get_benefici();
        }
        return total;
    }

}
