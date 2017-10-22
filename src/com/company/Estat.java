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

    public Dada_Camio[] dades_camio;
    public boolean[] peticio_atesa;

    public Estat(Dada_Camio[] dades, boolean[] peticions) {
        this.dades_camio = new Dada_Camio[dades.length];
        for (int i = 0; i < dades.length; ++i) {
           dades_camio[i] = new Dada_Camio(dades[i]);
        }
        this.peticio_atesa = new boolean[peticions.length];
        for (int i = 0; i < peticions.length; ++i) {
            peticio_atesa[i] = peticions[i];
        }
    }

    public Dada_Camio[] getDades_camio() {
        return dades_camio;
    }

    public boolean[] getPeticio_atesa() {return peticio_atesa;}

    // Afegeix peticio fantasma
    public boolean replace(int c, int v, int p){
        if (peticio_atesa[p]) return false;
<<<<<<< HEAD
        Pair viatge = dades_camio[c].get_viatje(v);
        if(viatge.g2 != -1) peticio_atesa[viatge.g2] = false;
        if(viatge.g1 != -1) peticio_atesa[viatge.g1] = false;
        if(p!=-1) peticio_atesa[p] = true;
        return (dades_camio[c].replace_viatge(p,v));
=======
        boolean can = dades_camio[c].replace_viatge(p,v);
        if(can) {
            Pair viatge = dades_camio[c].get_viatje(v);
            if (viatge.g2 != -1) peticio_atesa[viatge.g2] = false;
            if (viatge.g1 != -1) peticio_atesa[viatge.g1] = false;
            if (p != -1) peticio_atesa[p] = true;
            return true;
        }
        return false;
>>>>>>> 8b8684b886fddc91c2b13899d0b05432c5cca06b
    }

    public boolean swap_entre_viatges(int c, int v, int i, int p){
        if (peticio_atesa[p]) return false;
        Pair viatge = dades_camio[c].get_viatje(v);
        boolean can = dades_camio[c].swap_peticions(v,i,p);
        if(can) {
            if (i == 1 && viatge.g2 != -1) peticio_atesa[viatge.g2] = false;
            else if (viatge.g1 != -1) peticio_atesa[viatge.g1] = false;
            peticio_atesa[p] = true;
            return true;
        }
        return false;
    }

    public double heuristic(){
        double total = 0;
        for(int i=0; i<dades_camio.length; ++i){
            total += dades_camio[i].get_benefici();
        }
        return total;
    }

    public boolean check_and_add(int c, int p) {
        if (peticio_atesa[p]) return false;
        else {
            boolean afegida = dades_camio[c].afegir_peticio(p);
            peticio_atesa[p] = true;
            return afegida;
        }
    }

    public boolean CamioViatgePeticioBuida(int c, int v, int i) {
        return dades_camio[c].ViatgePeticioBuida(v, i);
    }

    //Canvia 1 gasolinera atesa del viatge per una altra d'un altre camio
    public boolean swap_entre_camions(int c, int v, int i, int c2, int v2, int i2){
        int p1 = dades_camio[c].getPeticio(v, i);
        int p2 = dades_camio[c2].getPeticio(v2, i2);

        boolean can;

        can = dades_camio[c].swap_peticions(v, i, p2);
        if (can) can = dades_camio[c2].swap_peticions(v2, i2, p1);

        return can;
    }

    public void imprimeix_estat() {
        System.out.print("Peticions_ateses: ");
        for (int k = 0; k < peticio_atesa.length; ++k) {
            System.out.print((k%10));
        }
        System.out.println();
        System.out.print("Peticions_ateses: ");
        for (int k = 0; k < peticio_atesa.length; ++k) {
            if (peticio_atesa[k]) System.out.print(1);
            else System.out.print(0);
        }
        System.out.println();
        for (int c = 0; c < dades_camio.length; ++c) {
            System.out.println("CAMIO:::  " + c);
            dades_camio[c].imprimeix_camio();
            System.out.println();
        }

    }

}
