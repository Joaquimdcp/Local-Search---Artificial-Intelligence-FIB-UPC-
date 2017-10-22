package com.company;


import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        int seed = 1234; // Seed per a generar num aleatori
        int n_gas = 100; //Numero de gasolineres
        int n_trucks = 1; // Numero de camions
        int n_centros = 10;


        // Init les Gasolineres
        Gasolineras gasolineras = new Gasolineras(n_gas,seed);
        int id = 0;
        ArrayList<Peticio> peticions_totals = new ArrayList();
        ArrayList<Integer> peticions_gas = new ArrayList();
        int cordX;
        int cordY;
        int i = 0;
        for(Gasolinera d: gasolineras){
            cordX = d.getCoordX();
            cordY = d.getCoordY();
            peticions_gas = d.getPeticiones();
            for(Integer p: peticions_gas) {
                peticions_totals.add(new Peticio(cordX,cordY,p));
                ++i;
            }
        }

        Peticio[] peticions = new Peticio[peticions_totals.size()];
        boolean[] peticions_ates = new boolean[peticions_totals.size()];
        int j = 0;
        for(Peticio p: peticions_totals){
            peticions[j] = p;
            peticions_ates[j] = false;
            j += 1;
        }


        CentrosDistribucion centros_dist = new CentrosDistribucion(n_centros,n_trucks,seed);
        Dada_Camio[] dada_camios = new Dada_Camio[centros_dist.size()];
        Distribucion[] camions = new Distribucion[centros_dist.size()];
        int ide = 0;
        for(Distribucion dist: centros_dist){
            System.out.println("Camio: " + ide + " X:" + dist.getCoordX() + " Y:" + dist.getCoordY());
            dada_camios[ide] = new Dada_Camio(id);;
            camions[ide] = dist;
            ide += 1;
        }
        Estat.camions = camions;
        Estat.peticions = peticions;

        Estat mapa = new Estat(dada_camios,peticions_ates);

        Problem p = new Problem(mapa, new DistributionSuccessorFunction(), new PGoalTest(), new PHeuristicFunction());;
        Search search = new HillClimbingSearch();
        SearchAgent search_agent = new SearchAgent(p, search);

        Estat e = (Estat) search.getGoalState();

        e.imprimeix_estat();

        System.out.println();
        printActions(search_agent.getActions());
        printInstrumentation(search_agent.getInstrumentation());


        System.out.println(e.heuristic());


    }

    private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }

    }

    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }


}
