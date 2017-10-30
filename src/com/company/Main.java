package com.company;


import IA.Gasolina.CentrosDistribucion;
import IA.Gasolina.Distribucion;
import IA.Gasolina.Gasolinera;
import IA.Gasolina.Gasolineras;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("Escriu H per a Hill Climbing o S per a Simulated Annealing: ");
        String tipus = sc.nextLine();
        if (tipus.equals("H") || tipus.equals("h")) {
            System.out.println("Quantes iteracions?");
            int it = sc.nextInt();
            System.out.println("Indica seed, n_gas, n_trucks, n_centros: ");
            int seed = sc.nextInt();
            int n_gas = sc.nextInt();
            int n_trucks = sc.nextInt();
            int n_centros = sc.nextInt();
            if (it == 1) executarHillClimbing(seed, n_gas, n_trucks, n_centros, true);
            else {
                for (int i = 0; i < it; ++i) {
                    executarHillClimbing(i + seed, n_gas, n_trucks, n_centros, false);
                }
            }
        }
        else if (tipus.equals("S") || tipus.equals("s")) {
            System.out.println("Quantes iteracions?");
            int iteracions = sc.nextInt();
            System.out.println("Indica seed, n_gas, n_trucks, n_centros, n_it, it_temp, k, l: ");
            int seed = sc.nextInt();
            int n_gas = sc.nextInt();
            int n_trucks = sc.nextInt();
            int n_centros = sc.nextInt();
            int n_it = sc.nextInt();
            int it_temp = sc.nextInt();
            int k = sc.nextInt();
            double l = sc.nextDouble();
            if (iteracions == 1) executarSA(seed, n_gas, n_trucks, n_centros, n_it, it_temp, k, l, true);
            else {
                for (int i = 0; i < iteracions; ++i) {
                    executarSA(i + seed, n_gas, n_trucks, n_centros, n_it, it_temp, k, l, false);
                }
            }
        }
        else System.out.println("Incorrecte, torna a executar el programa i escriu H o S");

    }

    private static void executarHillClimbing(int seed, int n_gas, int n_trucks, int n_centros, boolean debug) throws Exception {
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
            if (debug) System.out.println(p.getX() + " " + p.getY() + " "+ p.getDies());
            peticions[j] = p;
            peticions_ates[j] = false;
            j += 1;
        }


        CentrosDistribucion centros_dist = new CentrosDistribucion(n_centros,n_trucks,seed);
        Dada_Camio[] dada_camios = new Dada_Camio[centros_dist.size()];
        Distribucion[] camions = new Distribucion[centros_dist.size()];
        int ide = 0;
        for(Distribucion dist: centros_dist){
            if (debug)System.out.println("Camio: " + ide + " X:" + dist.getCoordX() + " Y:" + dist.getCoordY());
            dada_camios[ide] = new Dada_Camio(id);
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

        if (debug) {
            e.imprimeix_estat();
            System.out.println();
            printActions(search_agent.getActions());
            printInstrumentation(search_agent.getInstrumentation());
            if (e.HiHaRepetits()) System.out.println("Es repeteixen peticions    :(");
            else System.out.println("NO HI HA REPETITS!!!   :D");
        }


        System.out.println(e.heuristic());


    }



    private static void executarSA(int seed, int n_gas, int n_trucks, int n_centros, int n_it, int it_temp, int k, double l, boolean debug) throws Exception {
        //it_temp -= (n_it)%it_temp;
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
            if (debug) System.out.println(p.getX() + " " + p.getY() + " "+ p.getDies());
            peticions[j] = p;
            peticions_ates[j] = false;
            j += 1;
        }


        CentrosDistribucion centros_dist = new CentrosDistribucion(n_centros,n_trucks,seed);
        Dada_Camio[] dada_camios = new Dada_Camio[centros_dist.size()];
        Distribucion[] camions = new Distribucion[centros_dist.size()];
        int ide = 0;
        for(Distribucion dist: centros_dist){
            if (debug)System.out.println("Camio: " + ide + " X:" + dist.getCoordX() + " Y:" + dist.getCoordY());
            dada_camios[ide] = new Dada_Camio(id);
            camions[ide] = dist;
            ide += 1;
        }
        Estat.camions = camions;
        Estat.peticions = peticions;

        Estat mapa = new Estat(dada_camios,peticions_ates);

        Problem p = new Problem(mapa, new DistributionSuccessorSA(), new PGoalTest(), new PHeuristicFunction());;
        Search search = new SimulatedAnnealingSearch(n_it, it_temp, k, l);
        SearchAgent search_agent = new SearchAgent(p, search);

        Estat e = (Estat) search.getGoalState();

        if (debug) {
            e.imprimeix_estat();
            System.out.println();
            //printActions(search_agent.getActions());
            printInstrumentation(search_agent.getInstrumentation());
            if (e.HiHaRepetits()) System.out.println("Es repeteixen peticions    :(");
            else System.out.println("NO HI HA REPETITS!!!   :D");
        }


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
