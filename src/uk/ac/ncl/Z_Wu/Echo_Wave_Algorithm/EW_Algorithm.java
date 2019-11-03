package uk.ac.ncl.Z_Wu.Echo_Wave_Algorithm;

import java.util.*;

class EW_Algorithm {

    private int node_Amount;
    private Map<Integer, List<Integer>> graph;

    /**
     * Receiving user's input and graph from file.
     *
     * @param nodes is node amount from user's input.
     * @param map   is a HashMap read from .txt file.
     */
    EW_Algorithm(int nodes, Map<Integer, List<Integer>> map) {
        this.node_Amount = nodes;
        this.graph = map;
    }

    /**
     * Print current chosen graph before run the algorithm
     * User has to press "y" to continue.
     */
    void print_nodeNeigh() {
        Scanner scanner = new Scanner(System.in);
        String str;
        for (int i = 0; i < node_Amount; i++) {
            System.out.println("Node " + i + " 's Neighbours are:" + graph.get(i));
        }
        while (true) {
            System.out.println("Press y to continue.");
            str = scanner.nextLine();
            if (str.equals("y"))
                break;
        }
        System.out.println("----------------------------------------------------");
    }

    /**
     * This programme chooses nodes randomly so each node may has 9 different situations when they are chosen.
     * Situation 1: It’s the first time choose nodes, matrix is still a zero-matrix.
     * (send message to all his neighbours.)
     * <p>
     * Situation 2: Chosen node is the initiator and his number of messages received < neigh number.
     * (waiting his neighbours’ response, do nothing)
     * <p>
     * Situation 3: Chosen node is the initiator and his number of messages received = neigh number.
     * (Algorithm is over.)
     * <p>
     * Situation 4: Chosen node is not the initiator while the algorithm is over.
     * (Do nothing.)
     * <p>
     * Situation 5: Chosen node is not the initiator and his number of messages received = 0.
     * (Do nothing , waiting to be wake up).
     * <p>
     * Situation 6: Chosen node is not the initiator and ( message received > 0 )&&( message sent == 0 ).
     * (Note his father and send messages to all neighbours except his father.)
     * <p>
     * Situation 7: Chosen node is not the initiator and ( msg sent > 0 )&&( msg received < #neigh ).
     * (Already send msg to neigh, do nothing &wait)
     * <p>
     * Situation 8: Chosen node is not the initiator and ( msg received == #neigh )&&( msg sent ==(#neigh-1) )
     * (Send msg to his father node.)
     * <p>
     * Situation 9: Chosen node is not the initiator and ( msg received == #neigh ), (msg sent == #neigh)
     * (Already send msg to his father.)
     */
    void Echo_Wave_Algorithm() {
        int execution_node;
        int rec;
        int recp = 0;
        int initial_node = 0;
        boolean isAlgorithmOver = false;
        int statistic = 0;

        //Save node's father in a HashMap
        Map<Integer, Integer> tree = new HashMap<>();

        Integer[][] execution_matrix = new Integer[node_Amount][node_Amount];
        Execution_Matrix EM = new Execution_Matrix(node_Amount, node_Amount, execution_matrix);


        for (int i = 0; i < node_Amount * 10; i++) {
            int iteration = i + 1;

            //choose R <=N nodes randomly and save them in an Array, only allow chosen nodes to execute algorithm.
            Random random_number = new Random();
            int number = random_number.nextInt(node_Amount);
            int[] all_node_set;
            if (node_Amount == 7) {
                all_node_set = new int[]{0, 1, 2, 3, 4, 5, 6};
            } else if (node_Amount == 8) {
                all_node_set = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
            } else {
                throw new IllegalArgumentException("Node number can only be 7 or 8.");
            }
            Random r = new Random();
            int[] chosen_nodes = new int[number + 1];
            int index;
            for (int a = 0; a < number + 1; a++) {
                index = r.nextInt(all_node_set.length - a);
                chosen_nodes[a] = all_node_set[index];
                for (int j = index; j < all_node_set.length - a - 1; j++) {
                    all_node_set[j] = all_node_set[j + 1];
                }
            }


            System.out.println("This is the " + iteration + " time iteration");
            System.out.println("These are chosen nodes in this iteration: ");
            for (int a = 0; a < chosen_nodes.length; a++) {
                System.out.print(chosen_nodes[a] + " ");
            }
            System.out.println("\n");


            for (int b = 0; b < chosen_nodes.length; b++) {

                if (!isAlgorithmOver) {
                    statistic = statistic + 1;
                }

                execution_node = chosen_nodes[b];
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Node " + execution_node + " has been chosen.");
                System.out.println("Node " + execution_node + " receives " + EM.getNodeReceive(execution_node) + " message(s).");

                if (EM.hasInitiator()) {
                    initial_node = execution_node;
                    System.out.println("Node " + initial_node + " is the initiators");
                    for (int j = 0; j < graph.get(initial_node).size(); j++) {                                          //situation 1
                        EM.setExecution_matrix(initial_node, graph.get(initial_node).get(j), 1);
                        recp = recp + 1;
                        System.out.println("Initiator sends message to his neighbour: " + graph.get(initial_node).get(j));//Print out message.
                    }
                } else {
                    System.out.println("This algorithm's initiator is : Node " + initial_node);
                    if (execution_node == initial_node) {     //situation (2&3)
                        rec = EM.getNodeReceive(initial_node);
                        if (rec < graph.get(initial_node).size()) {                                                     //situation 2
                            System.out.println("The initiator Node is waiting response.");                              //
                        } else {                                                                                        //
                            isAlgorithmOver = true;                                                                     //situation 3
                            System.out.println("The Algorithm is Over. The initiator decides!");
                        }
                    } else {  //choose non-initial_node

                        if (isAlgorithmOver) {                                                                          //situation 4
                            System.out.println("The Algorithm is Over.The initiator decides!");
                        } else {
                            if (EM.getNodeReceive(execution_node) <= 0) {                                               //situation 5
                                System.out.println("This Node is waiting wake up message.");
                            } else if (EM.getNodeReceive(execution_node) > 0 && EM.getNodeSend(execution_node) <= 0) {  //situation 6
                                tree.put(execution_node, EM.getFirstSender(execution_node));
                                if (graph.get(execution_node).size() == 1) {
                                    EM.setExecution_matrix(execution_node, tree.get(execution_node), 1);         //if this node has no neighbour except his father
                                    recp = recp + 1;                                                                    //just send msg back to his father
                                    System.out.println("Node " + execution_node + " has no neighbours except his father so sends message to his father "
                                            + tree.get(execution_node));
                                } else {
                                    for (int n = 0; n < graph.get(execution_node).size(); n++) {
                                        if (graph.get(execution_node).get(n) == tree.get(execution_node)) {
                                            EM.setExecution_matrix(execution_node, graph.get(execution_node).get(n), 0);
                                        } else {
                                            EM.setExecution_matrix(execution_node, graph.get(execution_node).get(n), 1);
                                            recp = recp + 1;
                                            System.out.println("Node " + execution_node + " sends message to his neighbour: "
                                                    + graph.get(execution_node).get(n));                                //Print out message
                                        }
                                    }
                                }
                            } else if (EM.getNodeReceive(execution_node) > 0
                                    && EM.getNodeReceive(execution_node) < graph.get(execution_node).size()) {          //situation 7
                                System.out.println("This Node is waiting responses.");
                            } else if (EM.getNodeReceive(execution_node) == graph.get(execution_node).size() &&
                                    EM.getNodeSend(execution_node) == graph.get(execution_node).size() - 1) {           //situation 8
                                EM.setExecution_matrix(execution_node, tree.get(execution_node), 1);
                                recp = recp + 1;
                                System.out.println("Node " + execution_node + " sends message to his father " + tree.get(execution_node));//Print out message
                            } else if (EM.getNodeReceive(execution_node) == graph.get(execution_node).size() &&
                                    EM.getNodeSend(execution_node) == graph.get(execution_node).size()) {               //situation 9
                                System.out.println("This Node has already sent message to his father.");
                            } else {
                                throw new IllegalArgumentException("Something goes wrong here.");
                            }
                        }
                    }
                }

                EM.print();

                //There're two method can get total message sent amount. Both of them can get correct value.
                //Method 1 uses method in Execution_Matrix Class.
                System.out.println("The total messages sent amount is(method 1): " + EM.node_sum());
                //Method 2 calculates the value when node sends message to another node.
                System.out.println("The total messages sent amount is(method 2): " + recp);
                for (int m = 0; m < node_Amount; m++) {
                    System.out.println("Node " + m + " 's father is: " + tree.get(m));
                }

                System.out.println("Already choose " + statistic + " nodes before algorithm over.");
            }
            System.out.println("----------------------------------------------------");
            System.out.println("                                     ");
            System.out.println("                                     ");

        }//for K*N Iteration


    }


}

