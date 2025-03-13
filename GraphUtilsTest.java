package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.w3c.dom.Node;
import sol.EdgeArrayGraph;
import sol.GraphUtils;
import sol.IGraph;
import sol.NodeEdgeGraph;
import src.NoRouteException;
import src.NodeNameExistsException;

import java.util.LinkedList;

public class GraphUtilsTest {
    // Assumes that graph will be empty, modifies it in-place
    private void addSimpleGraphNodes(IGraph graph) throws NodeNameExistsException {
        graph.addNode("node 1");
        graph.addNode("node 2");
        graph.addNode("node 3");
        graph.addNode("node 4");
    }

    // Assumes that graph will have nodes from `addSimpleGraphNodes`,
    //     modifies it in-place
    private void addSimpleGraphEdges(IGraph graph) throws NodeNameExistsException {
        graph.addDirectedEdge("node 1", "node 2");
        graph.addDirectedEdge("node 2", "node 3");
    }

    // Assumes that graph will be empty, modifies it in-place
    private void makeSimpleGraph(IGraph graph) throws NodeNameExistsException {
        addSimpleGraphNodes(graph);
        addSimpleGraphEdges(graph);
    }

    private void ourMadeGraphNodes1(IGraph graph1) throws NodeNameExistsException {
        graph1.addNode("node 1");
        graph1.addNode("node 2");
        graph1.addNode("node 3");
    }

    private void ourMadeGraphEdges1(IGraph graph1) throws NodeNameExistsException {
        graph1.addDirectedEdge("node 1", "node 2");
        graph1.addDirectedEdge("node 2", "node 1");
        graph1.addDirectedEdge("node 1", "node 3");
        graph1.addDirectedEdge("node 3", "node 1");
    }

    private void ourMadeGraph1(IGraph graph) throws NodeNameExistsException {
        ourMadeGraphNodes1(graph);
        ourMadeGraphEdges1(graph);
    }

    private void ourMadeGraphNodes2(IGraph graph2) throws NodeNameExistsException {
        graph2.addNode("node 1");
        graph2.addNode("node 2");
        graph2.addNode("node 3");
        graph2.addNode("node 4");
    }

    private void ourMadeGraphEdges2(IGraph graph2) throws NodeNameExistsException {
    }

    private void ourMadeGraph2(IGraph graph2) throws NodeNameExistsException {
        ourMadeGraphNodes2(graph2);
        ourMadeGraphEdges2(graph2);
    }

    private void ourMadeGraphNodes3(IGraph graph3) throws NodeNameExistsException {
        graph3.addNode("node 1");
        graph3.addNode("node 2");
    }

    private void ourMadeGraphEdges3(IGraph graph3) throws NodeNameExistsException {
        graph3.addDirectedEdge("node 1", "node 1");
        graph3.addDirectedEdge("node 2", "node 2");
    }
    private void ourMadeGraph3(IGraph graph3) throws NodeNameExistsException {
        ourMadeGraphNodes3(graph3);
        ourMadeGraphEdges3(graph3);
    }

    private void ourMadeGraph4(IGraph graph4) throws NodeNameExistsException {
        graph4.addNode("node 1");
        graph4.addNode("node 2");
        graph4.addNode("node 3");
        graph4.addNode("node 4");

        graph4.addDirectedEdge("node 1", "node 2");
        graph4.addDirectedEdge("node 1", "node 3");
        graph4.addDirectedEdge("node 1", "node 4");
        graph4.addDirectedEdge("node 2", "node 1");
        graph4.addDirectedEdge("node 2", "node 3");
        graph4.addDirectedEdge("node 2", "node 4");
        graph4.addDirectedEdge("node 3", "node 1");
        graph4.addDirectedEdge("node 3", "node 2");
        graph4.addDirectedEdge("node 3", "node 4");
        graph4.addDirectedEdge("node 4", "node 1");
        graph4.addDirectedEdge("node 4", "node 2");
        graph4.addDirectedEdge("node 4", "node 3");
    }

    private void ourMadeGraph5(IGraph graph5) throws NodeNameExistsException {
        graph5.addNode("node 1");
        graph5.addNode("node 2");
        graph5.addNode("node 3");
        graph5.addNode("node 4");
        graph5.addNode("node 5");

        graph5.addDirectedEdge("node 1", "node 4");
        graph5.addDirectedEdge("node 4", "node 1");

        graph5.addDirectedEdge("node 3", "node 5");
        graph5.addDirectedEdge("node 5", "node 3");

    }

    private void ourMadeGraphNodes6(IGraph graph6) throws NodeNameExistsException {
        graph6.addNode("node 1");
        graph6.addNode("node 2");
        graph6.addNode("node 3");
        graph6.addNode("node 4");
        graph6.addNode("node 5");
        graph6.addNode("node 6");
        graph6.addNode("node 7");
        graph6.addNode("node 8");
    }

    private void ourMadeGraphEdges6(IGraph graph6) throws NodeNameExistsException{

        graph6.addDirectedEdge("node 1", "node 6");
        graph6.addDirectedEdge("node 6", "node 1");
        graph6.addDirectedEdge("node 6", "node 2");
        graph6.addDirectedEdge("node 2", "node 6");
        graph6.addDirectedEdge("node 2", "node 5");
        graph6.addDirectedEdge("node 5", "node 2");
        graph6.addDirectedEdge("node 5", "node 3");
        graph6.addDirectedEdge("node 3", "node 5");
        graph6.addDirectedEdge("node 3", "node 7");
        graph6.addDirectedEdge("node 7", "node 3");
        graph6.addDirectedEdge("node 7", "node 8");
        graph6.addDirectedEdge("node 8", "node 7");
        graph6.addDirectedEdge("node 8", "node 4");
        graph6.addDirectedEdge("node 4", "node 8");
        graph6.addDirectedEdge("node 4", "node 1");
        graph6.addDirectedEdge("node 1", "node 4");
    }

    private void ourMadeGraph6(IGraph graph) throws NodeNameExistsException {
        ourMadeGraphNodes6(graph);
        ourMadeGraphEdges6(graph);
    }

    private void ourMadeGraphNodesDirected6(IGraph graph6) throws NodeNameExistsException {
        graph6.addNode("node 1");
        graph6.addNode("node 2");
        graph6.addNode("node 3");
        graph6.addNode("node 4");
        graph6.addNode("node 5");
        graph6.addNode("node 6");
        graph6.addNode("node 7");
        graph6.addNode("node 8");
    }

    private void ourMadeGraphEdgesDirected6(IGraph graph6) throws NodeNameExistsException{

        graph6.addDirectedEdge("node 1", "node 6");
        //graph6.addDirectedEdge("node 6", "node 1");
        graph6.addDirectedEdge("node 6", "node 2");
        //graph6.addDirectedEdge("node 2", "node 6");
        graph6.addDirectedEdge("node 2", "node 5");
        //graph6.addDirectedEdge("node 5", "node 2");
        graph6.addDirectedEdge("node 5", "node 3");
        //graph6.addDirectedEdge("node 3", "node 5");
        graph6.addDirectedEdge("node 3", "node 7");
        //graph6.addDirectedEdge("node 7", "node 3");
        graph6.addDirectedEdge("node 7", "node 8");
        //graph6.addDirectedEdge("node 8", "node 7");
        graph6.addDirectedEdge("node 8", "node 4");
        //graph6.addDirectedEdge("node 4", "node 8");
        //graph6.addDirectedEdge("node 4", "node 1");
        //graph6.addDirectedEdge("node 1", "node 4");
    }

    private void ourMadeGraphDirected6(IGraph graph) throws NodeNameExistsException {
        ourMadeGraphNodesDirected6(graph);
        ourMadeGraphEdgesDirected6(graph);
    }



    @Test
    public void testGetRouteSimple(){

        try{
            IGraph simpleGraph = new NodeEdgeGraph("yeah");
            makeSimpleGraph(simpleGraph);

            String fromNode = "node 1";
            String toNode = "node 3";
            LinkedList<String> route = GraphUtils.getRoute(simpleGraph, fromNode, toNode);

            LinkedList<String> expectedRoute = new LinkedList<>();
            expectedRoute.add("node 1");
            expectedRoute.add("node 2");
            expectedRoute.add("node 3");

            Assert.assertEquals(expectedRoute, route);
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }

    @Test
    public void testGetRouteOurMade1(){

        try{
            IGraph ourMade1 = new NodeEdgeGraph("ourMade1");
            ourMadeGraph1(ourMade1);

            String fromNode = "node 1";
            String toNode = "node 3";
            LinkedList<String> route = GraphUtils.getRoute(ourMade1, fromNode, toNode);

            LinkedList<String> expectedRoute = new LinkedList<>();
            expectedRoute.add("node 1");
            //expectedRoute.add("node 2");
            expectedRoute.add("node 3");

            System.out.println("got here");
            Assert.assertEquals(expectedRoute, route);
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }


    @Test
    public void testGetRouteOurMade6Edge(){

        try{
            IGraph ourMade6 = new EdgeArrayGraph("ourMade6");
            ourMadeGraph6(ourMade6);

            String fromNode = "node 1";
            String toNode = "node 8";
            LinkedList<String> route = GraphUtils.getRoute(ourMade6, fromNode, toNode);

            LinkedList<String> expectedRoute = new LinkedList<>();
//            expectedRoute.add("node 1");
//            expectedRoute.add("node 6");
//            expectedRoute.add("node 2");
//            expectedRoute.add("node 5");
//            expectedRoute.add("node 3");
//            expectedRoute.add("node 7");
//            expectedRoute.add("node 8");
            expectedRoute.add("node 1");
            expectedRoute.add("node 4");
            expectedRoute.add("node 8");

            Assert.assertEquals(expectedRoute, route);
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }

    @Test
    public void testGetRouteOurMade6(){

        try{
            IGraph ourMade6 = new NodeEdgeGraph("ourMade6");
            ourMadeGraph6(ourMade6);

            String fromNode = "node 1";
            String toNode = "node 8";
            LinkedList<String> route = GraphUtils.getRoute(ourMade6, fromNode, toNode);

            LinkedList<String> expectedRoute = new LinkedList<>();
//            expectedRoute.add("node 1");
//            expectedRoute.add("node 6");
//            expectedRoute.add("node 2");
//            expectedRoute.add("node 5");
//            expectedRoute.add("node 3");
//            expectedRoute.add("node 7");
//            expectedRoute.add("node 8");
            expectedRoute.add("node 1");
            expectedRoute.add("node 4");
            expectedRoute.add("node 8");

            Assert.assertEquals(expectedRoute, route);
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }

    @Test
    public void testGetRouteOurMadeDirected6(){

        try{
            IGraph ourMade6 = new EdgeArrayGraph("ourMade6");
            ourMadeGraphDirected6(ourMade6);
            ourMade6.addUndirectedEdge("node 5", "node 1");
            ourMade6.addDirectedEdge("node 1", "node 4");
            ourMade6.addDirectedEdge("node 4", "node 8");
            ourMade6.addDirectedEdge("node 4", "node 4");
            ourMade6.addDirectedEdge("node4", "node 8");

            String fromNode = "node 1";
            String toNode = "node 8";
            LinkedList<String> route = GraphUtils.getRoute(ourMade6, fromNode, toNode);

            LinkedList<String> expectedRoute = new LinkedList<>();
//            expectedRoute.add("node 1");
//            expectedRoute.add("node 6");
//            expectedRoute.add("node 2");
//            expectedRoute.add("node 5");
//            expectedRoute.add("node 3");
//            expectedRoute.add("node 7");
//            expectedRoute.add("node 8");
            expectedRoute.add("node 1");
            expectedRoute.add("node 4");
            expectedRoute.add("node 8");

            Assert.assertEquals(expectedRoute, route);
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoRouteException e) {
            Assert.fail("getRoute did not find a route");
        }
    }

    @Test
    public void testGetRouteSimpleNoRoute(){
        try {
            NodeEdgeGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);
            String fromNode = "node 1";
            String toNode = "node 4";
            Assert.assertThrows(
                    NoRouteException.class,
                    () -> GraphUtils.getRoute(simpleGraph, fromNode, toNode));
        }
        catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }


    @Test
    public void testCountSelfEdgesSimple() {
        try {
            IGraph basicGraph = new NodeEdgeGraph("a graph");
            basicGraph.addNode("node 1");
            basicGraph.addDirectedEdge("node 1", "node 1");
            Assert.assertEquals(1, basicGraph.countSelfEdges());
        } catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }


    @Test
    public void testOurMadeCountEdges2() {
        try {
            IGraph ourMade2 = new NodeEdgeGraph("graph2");
            ourMadeGraph2(ourMade2);
            Assert.assertEquals(0, ourMade2.countSelfEdges());
        } catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }


    @Test
    public void testOurMadeCountEdges3() {
        try {
            IGraph ourMade3 = new NodeEdgeGraph("graph3");
            ourMadeGraph3(ourMade3);
            Assert.assertEquals(2, ourMade3.countSelfEdges());
        } catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }


    @Test
    public void testReachesAllOthersSimple(){
            IGraph basicGraph = new NodeEdgeGraph("b graph");
            basicGraph.addUndirectedEdge("node 1", "node 2");
            Assert.assertTrue(basicGraph.reachesAllOthers("node 1"));
            Assert.assertTrue(basicGraph.reachesAllOthers("node 2"));
    }


    @Test
    public void testReachesAllOthersOurMade6(){
        try {
            IGraph ourmade6 = new NodeEdgeGraph("ourMade6");
            ourMadeGraph6(ourmade6);
            Assert.assertFalse(ourmade6.reachesAllOthers("node 1"));
            Assert.assertFalse(ourmade6.reachesAllOthers("node 7"));
        } catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }
}
