package uk.ac.ncl.Z_Wu.Echo_Wave_Algorithm;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * This programme uses file as graph input. It uses InputStreamReader and BufferedReader to reads data
 * from .txt file from resources folder. At the beginning of this programme, users need to input a number
 * N to choose node numbers and decide which type of graph they want to run. Then the programme will start
 * iteration for N*10 times.
 */
public class UseAlgorithm {

    public static void main(String[] args) {
        System.out.println("Please press node numbers: ( 7 / 8 ).");
        Scanner scanner_1 = new Scanner(System.in);
        int Node_Number = scanner_1.nextInt();
        if (Node_Number != 7 && Node_Number != 8)
            throw new IllegalArgumentException("Node number can only be 7 or 8.");
        String path;
        System.out.println("This programme has " + Node_Number + " nodes.");
        System.out.println("Please choose graph type: ( 1 / 2 / 3 )");
        Scanner scanner_2 = new Scanner(System.in);
        int type = scanner_2.nextInt();
        if (type != 1 && type != 2 && type != 3)
            throw new IllegalArgumentException("Graph type can only be 1 or 2 or 3.");

        if (Node_Number == 7) {
            switch (type) {
                case 1:
                    path = "resources/7node_type1.txt";
                    break;
                case 2:
                    path = "resources/7node_type2.txt";
                    break;
                case 3:
                    path = "resources/7node_type3.txt";
                    break;
                default:
                    throw new IllegalArgumentException("Please press 1 or 2 or 3.");
            }
        } else {
            switch (type) {
                case 1:
                    path = "resources/8node_type1.txt";
                    break;
                case 2:
                    path = "resources/8node_type2.txt";
                    break;
                case 3:
                    path = "resources/8node_type3.txt";
                    break;
                default:
                    throw new IllegalArgumentException("Please press 1 or 2 or 3.");
            }
        }

        Map<Integer, List<Integer>> graph = getGraph(path, Node_Number);
        EW_Algorithm newGraph = new EW_Algorithm(Node_Number, graph);
        newGraph.print_nodeNeigh();
        newGraph.Echo_Wave_Algorithm();
    }

    /**
     * This programme uses HashMap to store graph. The key in HashMap is node name and each key's
     * value is an ArrayList which saves all neighbours of this node. So it is feasible to get node's
     * single neighbour by using method like Map.get(node_name).get(n);
     * <p>
     * In this class an instance of BufferedReader is used to read the whole content. In a Node Number times iteration,
     * when it equals current node's name, it starts reading current node's neighbour and saving them in an ArrayList.
     * When it equals "end", it will break.
     *
     * @param path        is the path of .txt file which saves graph.
     * @param Node_Number is the number of nodes.
     * @return a HashMap which saves the whole graph.
     * @throws IllegalFormatException if node number is not 7 or 8.
     */
    private static Map<Integer, List<Integer>> getGraph(String path, int Node_Number) {

        Map<Integer, List<Integer>> graph = new HashMap<>();
        List<Integer> myList_0;
        List<Integer> myList_1;
        List<Integer> myList_2;
        List<Integer> myList_3;
        List<Integer> myList_4;
        List<Integer> myList_5;
        List<Integer> myList_6;
        List<Integer> myList_7;

        for (int i = 0; i < Node_Number; i++) {

            List<Integer> list = new ArrayList<>();
            File file = new File(path);
            InputStreamReader read = null;
            BufferedReader reader = null;
            try {
                read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                reader = new BufferedReader(read);
                String line_1;
                String line_2;
                while ((line_1 = reader.readLine()) != null) {
                    if (line_1.equals("node" + i)) {
                        while ((line_2 = reader.readLine()) != null) {
                            if (line_2.equals("end")) {
                                break;
                            }
                            int neighbour = Integer.parseInt(line_2);
                            list.add(neighbour);
                        }
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (read != null) {
                    try {
                        read.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (Node_Number == 7) {
                switch (i) {
                    case 0:
                        myList_0 = list;
                        graph.put(0, myList_0);
                    case 1:
                        myList_1 = list;
                        graph.put(1, myList_1);
                    case 2:
                        myList_2 = list;
                        graph.put(2, myList_2);
                    case 3:
                        myList_3 = list;
                        graph.put(3, myList_3);
                    case 4:
                        myList_4 = list;
                        graph.put(4, myList_4);
                    case 5:
                        myList_5 = list;
                        graph.put(5, myList_5);
                    case 6:
                        myList_6 = list;
                        graph.put(6, myList_6);
                }
            } else if (Node_Number == 8) {
                switch (i) {
                    case 0:
                        myList_0 = list;
                        graph.put(0, myList_0);
                    case 1:
                        myList_1 = list;
                        graph.put(1, myList_1);
                    case 2:
                        myList_2 = list;
                        graph.put(2, myList_2);
                    case 3:
                        myList_3 = list;
                        graph.put(3, myList_3);
                    case 4:
                        myList_4 = list;
                        graph.put(4, myList_4);
                    case 5:
                        myList_5 = list;
                        graph.put(5, myList_5);
                    case 6:
                        myList_6 = list;
                        graph.put(6, myList_6);
                    case 7:
                        myList_7 = list;
                        graph.put(7, myList_7);
                }
            } else {
                throw new IllegalArgumentException("Node number can only be 7 or 8.");
            }

        }
        return graph;
    }

}
