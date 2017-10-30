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
            System.out.println("Indica seed");
            int seed = sc.nextInt();
            System.out.println("Indica n_gas");
            int n_gas = sc.nextInt();
            System.out.println("Indica n_trucks");
            int n_trucks = sc.nextInt();
            System.out.println("Indica n_centros");
            int n_centros = sc.nextInt();
            System.out.println("Indica solu_ini(0->buit, 1->swaps a viatge.g1, 2->swaps a tot, 3->add a tot)");
            int solu_ini = sc.nextInt();
            double mitjana = 0;
            if (it == 1) executarHillClimbing(seed, n_gas, n_trucks, n_centros, solu_ini, true);
            else {
                for (int i = 0; i < it; ++i) {
                    mitjana += executarHillClimbing(i + seed, n_gas, n_trucks, n_centros,solu_ini, false);
                }
                mitjana /= it;
                System.out.println("Mitjana execucio::: " + mitjana);
            }
        }
        else if (tipus.equals("S") || tipus.equals("s")) {
            System.out.println("Quantes iteracions?");
            int iteracions = sc.nextInt();
            System.out.println("Indica seed");
            int seed = sc.nextInt();
            System.out.println("Indica n_gas");
            int n_gas = sc.nextInt();
            System.out.println("Indica n_trucks");
            int n_trucks = sc.nextInt();
            System.out.println("Indica n_centros");
            int n_centros = sc.nextInt();
            System.out.println("Indica solu_ini(0->buit, 1->swaps a viatge.g1, 2->swaps a tot, 3->add a tot)");
            int solu_ini = sc.nextInt();
            System.out.println("Indica n_it");
            int n_it = sc.nextInt();
            System.out.println("Indica it_temp");
            int it_temp = sc.nextInt();
            System.out.println("Indica k");
            int k = sc.nextInt();
            System.out.println("Indica lambda");
            double l = sc.nextDouble();
            double mitjana = 0;
            if (iteracions == 1) executarSA(seed, n_gas, n_trucks, n_centros, n_it, it_temp, k, l, solu_ini, true);
            else {
                for (int i = 0; i < iteracions; ++i) {
                    //executarSA(i + seed, n_gas, n_trucks, n_centros, n_it, it_temp, k, l, false);
                    mitjana += executarSA(i + seed, n_gas, n_trucks, n_centros, n_it, it_temp, k, l, solu_ini, false);
                }
                mitjana /= iteracions
                ;
                System.out.println("Mitjana execucio:::  k " + k + " lamda: " + l + " es "+ mitjana);
            }
        }
        else System.out.println("Incorrecte, torna a executar el programa i escriu H o S");

    }

    private static double executarHillClimbing(int seed, int n_gas, int n_trucks, int n_centros, int solu_ini, boolean debug) throws Exception {
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

        inicialitzar_estat(mapa, solu_ini);

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
        return (e.heuristic());


    }



    private static double executarSA(int seed, int n_gas, int n_trucks, int n_centros, int n_it, int it_temp, int k, double l, int solu_ini, boolean debug) throws Exception {
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

        inicialitzar_estat(mapa, solu_ini);

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
        return e.heuristic();


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

    private static void inicialitzar_estat(Estat e, int cas) {
        if (cas == 0) return;                               //buit
        if (cas == 1) {                                     //swaps a posicio 1 de cada viatge
            Random randomGenerator = new Random();
            for (int c = 0; c < e.camions.length; ++c) {
                for (int v = 0; v < 5; ++v) {
                    Estat new_state = new Estat(e.getDades_camio(), e.getPeticio_atesa());
                    int p = randomGenerator.nextInt(e.peticio_atesa.length);
                    if (!e.peticio_atesa[p] && new_state.swap_entre_viatges(c, v, 0, p)) {
                            e.swap_entre_viatges(c, v, 0, p);
                    }
                }
            }
            return;
        }

        else if (cas == 2) {                                //swaps a totes posicions
            Random randomGenerator = new Random();
            for (int c = 0; c < e.camions.length; ++c) {
                for (int v = 0; v < 5; ++v) {
                    for (int i = 0; i < 2; ++i) {
                        Estat new_state = new Estat(e.getDades_camio(), e.getPeticio_atesa());
                        int p = randomGenerator.nextInt(e.peticio_atesa.length);
                        if (!e.peticio_atesa[p] && new_state.swap_entre_viatges(c, v, 0, p)) {
                            e.swap_entre_viatges(c, v, 0, p);
                        }
                    }
                }
            }
            return;
        }

        else if (cas == 3) {                                    //adds random a tota posicio
            Random randomGenerator = new Random();
            for (int c = 0; c < e.camions.length; ++c) {
                Estat new_state = new Estat(e.getDades_camio(), e.getPeticio_atesa());
                int p = randomGenerator.nextInt(e.peticio_atesa.length);
                if (!e.peticio_atesa[p] && new_state.check_and_add(c, p)) {
                    e.check_and_add(c, p);
                }
            }
            return;
        }
    }
}
