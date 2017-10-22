package com.company;
import IA.Connectat.ES;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

import java.util.ArrayList;
import java.util.List;

public class DistributionSuccessorFunction implements SuccessorFunction {
    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        Estat board = (Estat) state;

        Estat new_state = new Estat(board.getDades_camio(), board.getPeticio_atesa());

        //c es index camió
        for (int c = 0; c < Estat.camions.length; ++c) {
            //p es index de peticions
            for (int p = 0; p < Estat.peticions.length; ++p) {
                new_state = new Estat(board.getDades_camio(), board.getPeticio_atesa());
                //si petició i compleix no atesa l'afegim
                if (new_state.check_and_add(c, p)) {
                    retval.add(new Successor(new String("add C:" + c + " P" + p), new_state));
                    for (int k = 0; k < board.peticio_atesa.length; ++k) {
                        if (board.peticio_atesa[k]) System.out.print(1);
                        else System.out.print(0);
                    }
                    System.out.println();
                }
                //sino mirem on la podem substituir
                //en totes les posicions dels viatges camio

                else {
                    for (int v = 0; v < 5; ++v) {
                        for (int i = 0; i < 2; ++i) {
                            Estat new_state2 = new Estat(board.getDades_camio(), board.getPeticio_atesa());
                            if (new_state2.swap_entre_viatges(c, v, i, p)) {
                                retval.add(new Successor(new String("swap C: " + c + " V: " + v + " I: " + i + " P: " + p), new_state2));
                            }
                        }
                    }
                }
            }
            /*
            //per cada viatge de camio
            for (int v = 0; v < 5; ++v) {
                //per cada petició de viatge
                //la primera o la segona
                for (int i = 0; i < 2; ++i) {

                    //si no esta buida (es diferent a -1) procedim:
                    if (board.CamioViatgePeticio(c, v, i)) {

                        //mirem amb qui podem fer swap
                        //per cada camio2
                        for (int c2 = 0; c2 < board.mida_camions(); ++c2) {
                            //per cada viatge de camio2
                            for (int v2 = 0; v2 < 5; ++v2) {
                                //per cada peticio de viatge
                                //la primera o la segona
                                for (int j = 0; j < 2; ++j) {
                                    //podem fer swap:
                                    if (board.check_swap(c, v, i, c2, v2, j)) {
                                        Estat new_state = new Estat(board.dades_camio, board.peticio_atesa);
                                        new_state.swap(c, v, i, c2, v2, j);
                                        retval.add(new Successor(new String("swap C: " + c + "  V: " + v + " " + i ";  C: " + c2 + "  V: " + v + " " + j), new_state));
                                    }
                                }
                            }
                        }

                    }
                }
            }
            */
        }
/*
        for(int p=0; p<Estat.peticions.length; p++){
            for (int c = 0; c < Estat.camions.length; ++c) {
                for(int v=0; v<5; v++){
                    Estat new_state = new Estat(board.getDades_camio(), board.getPeticio_atesa());
                    if(new_state.replace(c,v,p)){
                        retval.add(new Successor(new String("swap C: "), new_state));
                    }
                    Estat new_state2 = new Estat(board.getDades_camio(), board.getPeticio_atesa());
                    if(new_state2.swap_entre_viatges(c,v,1,p)){
                        retval.add(new Successor(new String("swap C: "), new_state2));
                    }
                    Estat new_state3 = new Estat(board.getDades_camio(), board.getPeticio_atesa());
                    if(new_state3.swap_entre_viatges(c,v,0,p)){
                        retval.add(new Successor(new String("swap C: "), new_state3));
                    }
                }
            }
        }
*/

        return retval;
    }
}

