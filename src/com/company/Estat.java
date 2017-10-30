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

    public boolean swap_entre_viatges(int c, int v, int i, int p){
            if (peticio_atesa[p]) return false;
            int p_antiga = dades_camio[c].getPeticio(v, i);
            if (p_antiga != -1) peticio_atesa[p_antiga] = false;
            peticio_atesa[p] = true;
            return (dades_camio[c].swap_peticions(v, i, p));

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

    public boolean HiHaRepetits() {
        boolean[] check_peticions = new boolean[peticio_atesa.length];
        for (int c = 0; c < dades_camio.length; ++c) {
            for (int v = 0; v < 5; ++v) {
                for (int i = 0; i < 2; ++i) {
                    if (dades_camio[c].getPeticio(v, i) != -1) {
                        if (check_peticions[dades_camio[c].getPeticio(v, i)]) return true;
                        check_peticions[dades_camio[c].getPeticio(v, i)] = true;
                    }
                }
            }
        }
        return false;
    }

}
