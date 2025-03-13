
package sol;

import src.NoRouteException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * class graph Utils that holds the getRoute and hasRoute Methods
 */
public class GraphUtils {

    //    /**
//     * Method to use breadth-first-search to check whether there is a path
//     *     from one node to another in a graph. Assumes that both fromNodeLabel
//     *     and toNodeLabel are valid node labels in theGraph.
//     *
//     * @param theGraph the graph to traverse
//     * @param fromNodeLabel name of the node from which to start searching
//     * @param toNodeLabel   name of the node we want to reach
//     * @return boolean indicating whether such a route exists
//     */

    /**
     * returns true if there is a route and false otherwise
     * @param theGraph
     * @param fromNodeLabel
     * @param toNodeLabel
     * @return
     */
    public static boolean hasRoute(NodeEdgeGraph theGraph, String fromNodeLabel, String toNodeLabel) {
        NodeEdgeGraph.Node fromNode = theGraph.getNode(fromNodeLabel);
        NodeEdgeGraph.Node toNode = theGraph.getNode(toNodeLabel);
        // set up and initialize data structures
        HashSet<NodeEdgeGraph.Node> visited = new HashSet<>();
        LinkedList<NodeEdgeGraph.Node> toCheck = new LinkedList<>();
        toCheck.add(fromNode);

        // process nodes to search for toNode
        while (!toCheck.isEmpty()) {
            NodeEdgeGraph.Node checkNode = toCheck.pop();
            if (checkNode.equals(toNode)) {
                return true;
            } else if (!visited.contains(checkNode)) {
                visited.add(checkNode);
                toCheck.addAll(checkNode.nextNodes);
            }
        }
        return false;
    }

    /**
     * Method to produce a sequence of nodes that constitutes a shortest path
     * from fromNodeLabel to toNodeLabel. Assumes that both fromNodeLabel
     * and toNodeLabel are valid node labels in theGraph.
     * Throws a NoRouteException if no such path exists
     *
     * @param theGraph      the graph to traverse
     * @param fromNodeLabel the node from which to start searching
     * @param toNodeLabel   the node we want to reach
     * @return List of nodes in order of the path
     * @throws NoRouteException if no such path exists
     */
    public static LinkedList<String> getRoute(IGraph theGraph, String fromNodeLabel, String toNodeLabel)
            throws NoRouteException {
        // set up and initialize data structures
        HashSet<String> visited = new HashSet<>();
        Queue<String> toCheck = new LinkedList<>();
        HashMap<String,String> cameFrom = new HashMap<>();
        toCheck.add(fromNodeLabel);

        // process nodes to search for toNode
        while (!toCheck.isEmpty()) {
            String checkNode = toCheck.remove();
            visited.add(checkNode);
            if (checkNode.equals(toNodeLabel)) {
                System.out.println("got here 1");
                return reconstructRoute(toNodeLabel, cameFrom);
            } else {
                for (String neighbor : theGraph.getNeighbors(checkNode)) {
                    if (!visited.contains(neighbor)) {
                        if (!cameFrom.containsKey(neighbor)){
                            cameFrom.put(neighbor, checkNode);
                            System.out.println("got here 2");
                        }
                        toCheck.add(neighbor);
                        System.out.println("got here 3");
                    }
                }
                System.out.println("outside ");
            }
        }
        System.out.println("got here 4");
        throw new NoRouteException();
    }

    /**
     * helper function for getRoute that is works backwards to make the route
     * @param dest
     * @param cameFrom
     * @return
     */
    private static LinkedList<String> reconstructRoute(String dest, HashMap<String, String> cameFrom) { /**
        LinkedList<String> list = new LinkedList<String>();
        String here = dest;
        System.out.println(cameFrom.toString());
        HashSet<String> visited = new HashSet<String>();
        while (!visited.contains(fromNode) || !visited.contains(here)){
            System.out.println("running");
            list.addFirst(here);
            visited.add(here);
            here = cameFrom.get(here);
        }
        if(list.get(0) == null || !list.get(0).equals(fromNode)){
            list.removeFirst();
        }
        return list;
        */


        LinkedList<String> list = new LinkedList<>();
        //String copy = new String(dest);

        while(cameFrom.containsKey(dest)){
            System.out.println("Running");
            list.addFirst(dest);
            dest = cameFrom.get(dest);
            //cameFrom.remove(copy);
        }
        list.addFirst(dest);
        return list;

    }

}
