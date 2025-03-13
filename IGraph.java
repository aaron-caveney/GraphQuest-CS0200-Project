package sol;

import src.NodeNameExistsException;

import java.util.LinkedList;

/**
 * interface for IGraph
 */
public interface IGraph {
    /**
     * addNode adds a node to a graph
     * @param descr
     * @throws NodeNameExistsException
     */
    void addNode(String descr) throws NodeNameExistsException;

    /**
     * addDirectedEdge adds a DirectedEdge to two nodes
     * @param descr1
     * @param descr2
     */
    void addDirectedEdge(String descr1, String descr2);

    /**
     * addUndirectedEdge adds a UndirectedEdge to two nodes
     * @param descr1
     * @param descr2
     */
    void addUndirectedEdge (String descr1, String descr2);

    /**
     *returs an int of self edges
     * @return
     */
    int countSelfEdges();

    /**
     * retures true if a node reaches all other and false otherwise
     * @param fromNodeLabel
     * @return
     */
    boolean reachesAllOthers(String fromNodeLabel);

    /**
     * returns a linkedList of all the neighbors
     * @param description
     * @return
     */
    LinkedList<String> getNeighbors(String description);

    /**
     * returns the size
     * @return
     */
    int getSize();

    /**
     * returns all the nodes
     * @return
     */
    LinkedList<String> getAllNodes();
}
