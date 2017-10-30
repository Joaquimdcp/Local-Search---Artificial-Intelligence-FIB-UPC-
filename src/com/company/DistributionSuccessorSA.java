package com.company;
import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DistributionSuccessorSA implements SuccessorFunction {
    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        Estat board = (Estat) state;
        Random randomGenerator = new Random();
        ArrayList retval1 = new ArrayList();

        int c = randomGenerator.nextInt(Estat.camions.length);
        int v = randomGenerator.nextInt(5);
        int i = randomGenerator.nextInt(2);

        int add_or_swap = randomGenerator.nextInt(10);
        Estat new_state = new Estat(board.getDades_camio(), board.getPeticio_atesa());
        if (add_or_swap%2 == 0) {
            int p = randomGenerator.nextInt(Estat.peticions.length);
            if (new_state.swap_entre_viatges(c, v, i, p)) {
                retval.add(new Successor(new String("swap C: " + c + " V: " + v + " I: " + i + " P: " + p), new_state));
            }
            else {
                new_state = new Estat(board.getDades_camio(), board.getPeticio_atesa());
                retval.add(new Successor(new String("not swap"), new_state));
            }
        }
        else {
            int c2 = randomGenerator.nextInt(Estat.camions.length);
            int v2 = randomGenerator.nextInt(5);
            int j = randomGenerator.nextInt(2);
            if (new_state.swap_entre_camions(c, v, i, c2, v2, j)) {
                retval.add(new Successor(new String("swap_camions C: " + c + "  V: " + v + " " + i + ";  C: " + c2 + "  V: " + v + " " + j), new_state));
            }
            else {
                new_state = new Estat(board.getDades_camio(), board.getPeticio_atesa());
                retval.add(new Successor(new String("not swap_camions: "), new_state));
            }
        }

        retval1.add(retval.get(0));
        return retval1;

    }
}

