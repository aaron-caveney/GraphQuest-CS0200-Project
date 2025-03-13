package sol;

import src.NodeNameExistsException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class for EdgeArrayGrapg that implemets the IGraph InterFace
 */
public class EdgeArrayGraph implements IGraph {
    String name;
    HashMap<String, Integer> place;
    HashMap<Integer, String> value;
    ArrayList<ArrayList<Boolean>> table = new ArrayList<>();



    /**
     * constructor for EdgeArrayGraph
     * @param name
     */
    public EdgeArrayGraph(String name) {
        this.name = name;
        this.table = new ArrayList<>();
        this.place = new HashMap<>();
        this.value = new HashMap<>();
    }

    /**
     * Method to add a new node with the given description. An exception will
     * be thrown if the description already names a node in the graph
     *
     * @param descr the text description or label to associate with the node
     * @throws NodeNameExistsException if that description is already
     * associated with a node in the graph
     */
    public void addNode(String descr) throws NodeNameExistsException {
        if (this.place.containsKey(descr)){
            throw new NodeNameExistsException();
        }
        this.addNodeUnchecked(descr);
    }

    /**
     * adds the node unchecked to the graph
     * @param descr
     */
    public void addNodeUnchecked(String descr) {
        for (ArrayList<Boolean> row : this.table){
            row.add(false);
        }

        ArrayList<Boolean> newRow = new ArrayList<>();
        for (int i = 0; i < this.table.size() + 1; i++){
            newRow.add(false);
        }
        this.place.put(descr, this.table.size());
        this.value.put(this.table.size(), descr);
        this.table.add(newRow);
    }

    /**
     * Method to add a directed edge between the nodes associated with the given
     * descriptions. If descr1 and descr2 are not already
     * valid node labels in the graph, those nodes are also created.
     * If the edge already exists, no changes are made
     * (and no exceptions or warnings are raised)
     *
     * @param descr1 the source node for the edge
     * @param descr2 the target node for the edge
     */
    public void addDirectedEdge(String descr1, String descr2){
            if (this.place.get(descr1) == null){
                this.addNodeUnchecked(descr1);
            }
            if(this.place.get(descr2) == null){
                this.addNodeUnchecked(descr2);
            }
            int des1 = this.place.get(descr1);
            int des2 = this.place.get(descr2);
            if (!this.table.get(des1).get(des2)){
                this.table.get(des1).set(des2,true);
            }
    }

    /**
     * Method to add an undirected edge between the nodes associated with the given
     * descriptions. This is equivalent to adding two directed edges, one from
     * descr1 to descr2, and another from descr2 to descr1.
     * If descr1 and descr2 are not already valid node labels in the graph,
     * those nodes are also created.
     *
     * @param descr1 the source node for the edge
     * @param descr2 the target node for the edge
     */
    public void addUndirectedEdge(String descr1, String descr2){
        this.addDirectedEdge(descr1, descr2);
        this.addDirectedEdge(descr2, descr1);
    }

    /**
     * Method to count how many nodes have edges to themselves
     *
     * @return the number of nodes that have edges to themselves
     */
    public int countSelfEdges() {
        int count = 0;
        for (int i = 0; i < this.table.size(); i++) {
            if (this.table.get(i).get(i)) {
                count ++;
            }
        }
        return count;
    }
    /*
    The main iteration is through the size of the table. so the big O runtime is at least
    O(n). However, the get() method for an arrayList can be assumed to be O(1) or constant runtime
    therefore, the big O runtime of this loop is O(3n + 3) because we call this get method twice
    and the worst case runtime would assume that we call count++ n times.
     */

    /**
     * Method to check whether a given node has edges to every other node (with or without an edge to itself).
     * Assumes that fromNodeLabel is a valid node label in the graph.
     *
     * @param fromNodeLabel the node to check
     * @return true if fromNodeLabel has an edge to every other node, otherwise false
     */
    public boolean reachesAllOthers(String fromNodeLabel) {
        for (int i = 0; i < this.table.size(); i++){
            if (!this.table.get(this.place.get(fromNodeLabel)).get(i) && !(this.place.get(fromNodeLabel) == i)){
                return false;
            }
        }
        return true;
    }
    /*
    the big o runtime of this method is O(n * m) where m is the constant runtime of all parts of the method
    and n is the size of the table
     */

    /**
     * returns all of the neighboring nodes to a given node
     * @param description
     * @return
     */
    @Override
    public LinkedList<String> getNeighbors(String description) {
        int des = this.place.get(description);
        LinkedList<String> list = new LinkedList<String>();
        for (int i = 0; i< this.table.size(); i++){
            if (this.table.get(i).get(des) == true && i != des){
                list.add(this.value.get(i));
            }
            if (this.table.get(des).get(i) == true){
                list.add(this.value.get(i));
            }
        }
        return list;
    }

    /**
     * returns the amount of nodes in the graph
     * @return
     */
    @Override
    public int getSize(){
        return this.place.size();
    }

    /**
     * returns a list of all of the nodes
     * @return
     */
    @Override
    public LinkedList<String> getAllNodes(){
        LinkedList<String> all = new LinkedList<String>();
        all.addAll(this.place.keySet());
        return all;
    }
}
