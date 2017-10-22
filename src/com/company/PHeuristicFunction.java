package com.company;

import aima.search.framework.HeuristicFunction;

public class PHeuristicFunction implements  HeuristicFunction {
    public double getHeuristicValue(Object n){
        //System.out.println(((Estat) n).heuristic());
        return -((Estat) n).heuristic();
    }
}
