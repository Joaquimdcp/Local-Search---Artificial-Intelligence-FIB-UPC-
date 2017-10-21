package com.company;

import aima.search.framework.HeuristicFunction;

public class PHeuristicFunction implements  HeuristicFunction {
    public double getHeuristicValue(Object n){
        return ((Estat) n).heuristic();
    }
}
