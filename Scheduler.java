package sol;

import java.util.*;

import src.NoScheduleException;

/**
 * Class Scheduler that holds the checkValidity, findSchedule, and allocate method
 */
public class Scheduler {


    /**
     * Method which checks if a given allocation of labs adheres to
     *     the scheduling constraints of the graph. Assumes that
     *     all lab names in proposedAlloc are valid labels in theGraph.
     *
     * @param theGraph the graph to try to schedule
     * @param proposedAlloc the proposed allocation of labs between Kathi and Elijah
     * @return boolean indicating whether the proposed allocation is valid
     */
    public static boolean checkValidity(IGraph theGraph, ArrayList<HashSet<String>> proposedAlloc) {
        if (theGraph.getAllNodes().isEmpty()) {
            return true;
        }
        if (proposedAlloc.size() != 2){
            return false;
        }
        HashSet<String> tA1 = proposedAlloc.get(0);
        HashSet<String> tA2 = proposedAlloc.get(1);

        HashSet<String> allItems = new HashSet<>(tA1);
        allItems.addAll(tA2);


        //check for no double booking
        
        if (allItems.size() != tA1.size() + tA2.size()){
            return false;
        }

        //check for any conflicts between labs

        if (!validityHelper(theGraph, tA1) || !validityHelper(theGraph, tA2)){
            return false;
        }

        //check that all labs are the same between the graph and the proposed allocation

        LinkedList<String> allNodes = theGraph.getAllNodes();
        if (!allNodes.containsAll(allItems) || !allItems.containsAll(allNodes)){
            return false;
        }


        return true;
    }

    /**
     * helper for checkValidity
     * @param graph
     * @param proposed
     * @return
     */
    public static boolean validityHelper(IGraph graph, HashSet<String> proposed){
        for(String lab : proposed){
            for (String neighbor : graph.getNeighbors(lab)){
                if (proposed.contains(neighbor) && !lab.equals(neighbor)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Method to compute a valid split of the graph nodes
     *     without violating scheduling constraints,
     *     if such a split exists
     * Throws a NoScheduleException if no such split exists
     *
     * @param theGraph the graph to try to schedule
     * @return an ArrayList of HashSets of node labels that constitute a
     *         valid split of the graph
     * @throws NoScheduleException if no such split exists
     */
    public static ArrayList<HashSet<String>> findSchedule(IGraph theGraph)
            throws NoScheduleException {
        //Instantiate all of the hashsets and linked list that we will need for the return
        HashSet<String> tA1 = new HashSet<String>();
        HashSet<String> tA2 = new HashSet<String>();
        ArrayList<HashSet<String>> proposed = new ArrayList<HashSet<String>>();
        HashSet<String> visited = new HashSet<String>();

        for (String node : theGraph.getAllNodes()){
            if (!visited.contains(node)){
                allocate(node, tA1, tA2, visited, theGraph);
            }
        }

        proposed.add(tA1);
        proposed.add(tA2);
        return proposed;
    }

    /**
     * allocate divides up the labs to each TA
     * @param node
     * @param tA1
     * @param tA2
     * @param visited
     * @param graph
     * @throws NoScheduleException
     */
    public static void allocate(String node, HashSet<String> tA1, HashSet<String> tA2, HashSet<String> visited, IGraph graph)
    throws NoScheduleException{
        if (visited.contains(node)){
            return;
        }

        visited.add(node);
        tA1.add(node);

        for (String neighbor : graph.getNeighbors(node)){
            if (tA1.contains(neighbor)){
                throw new NoScheduleException();
            }
            if (!visited.contains(neighbor)){
                tA2.add(neighbor);
                allocate(neighbor, tA2, tA1, visited, graph);
            }
        }
    }
}
