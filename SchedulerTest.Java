package test;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashSet;

import sol.EdgeArrayGraph;
import sol.IGraph;
import sol.NodeEdgeGraph;
import sol.Scheduler;
import src.NoScheduleException;
import src.NodeNameExistsException;

public class SchedulerTest {
    // Assumes that graph will be empty, modifies it in-place
    private void addSimpleGraphNodes(IGraph graph) throws NodeNameExistsException {
        graph.addNode("lab 1");
        graph.addNode("lab 2");
        graph.addNode("lab 3");
        graph.addNode("lab 4");
    }

    // Assumes that graph will have nodes from `addSimpleGraphNodes`,
    //     modifies it in-place
    private void addSimpleGraphEdges(IGraph graph) throws NodeNameExistsException {
        graph.addUndirectedEdge("lab 1", "lab 2");
        graph.addUndirectedEdge("lab 2", "lab 3");
    }

    // Assumes that graph will be empty, modifies it in-place
    private void makeSimpleGraph(IGraph graph) throws NodeNameExistsException {
        addSimpleGraphNodes(graph);
        addSimpleGraphEdges(graph);
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

    private void ourMadeGraphEdges6(IGraph graph6) throws NodeNameExistsException {
        graph6.addUndirectedEdge("node 1", "node 2");
        graph6.addUndirectedEdge("node 2", "node 3");
        graph6.addUndirectedEdge("node 3", "node 4");
        graph6.addUndirectedEdge("node 1", "node 5");
        graph6.addUndirectedEdge("node 2", "node 6");
        graph6.addUndirectedEdge("node 3", "node 7");
        graph6.addUndirectedEdge("node 4", "node 8");
    }

    private void ourMadeGraph6(IGraph graph) throws NodeNameExistsException {
        ourMadeGraphNodes6(graph);
        ourMadeGraphEdges6(graph);
    }


    @Test
    public void testCheckValidityTrue() {
        try{
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);

            ArrayList<HashSet<String>> proposedSchedule = new ArrayList<>();
            HashSet<String> set1 = new HashSet<>();
            set1.add("lab 1");
            set1.add("lab 3");
            set1.add("lab 4");
            proposedSchedule.add(set1);
            HashSet<String> set2 = new HashSet<>();
            set2.add("lab 2");
            proposedSchedule.add(set2);

            Assert.assertTrue(Scheduler.checkValidity(simpleGraph, proposedSchedule));
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        }
    }

    @Test
    public void testCheckValidityFalse() {
        try{
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);

            ArrayList<HashSet<String>> proposedSchedule = new ArrayList<>();
            HashSet<String> set1 = new HashSet<>();
            set1.add("lab 3");
            set1.add("lab 4");
            proposedSchedule.add(set1);
            HashSet<String> set2 = new HashSet<>();
            set2.add("lab 1");
            set2.add("lab 2");
            proposedSchedule.add(set2);

            Assert.assertFalse(Scheduler.checkValidity(simpleGraph, proposedSchedule));
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        }

    }
    @Test
    public void testingtesting(){
        NodeEdgeGraph simpleGraph = new NodeEdgeGraph("yeah");
        simpleGraph.addUndirectedEdge("node 1", "node 2");
        Assert.assertEquals(simpleGraph.getSize(), 2);
    }

    @Test
    public void testing1(){
        EdgeArrayGraph notSimple = new EdgeArrayGraph("yeah");
        notSimple.addUndirectedEdge("node 1", "node 2");
        Assert.assertEquals(2, notSimple.getSize());
    }

    @Test
    public void testFindScheduleValid(){
        try {
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);
            ArrayList<HashSet<String>> schedule = Scheduler.findSchedule(simpleGraph);
            Assert.assertTrue(Scheduler.checkValidity(simpleGraph, schedule));
        }
        catch (NodeNameExistsException e) {
            // fail() automatically stops and fails the current test with an error message
            Assert.fail("Could not create graph to test");
        } catch (NoScheduleException e) {
            Assert.fail("findSchedule did not find a schedule");
        }
    }

    @Test
    public void testFindScheduleInvalid(){
        try {
            IGraph simpleGraph = new NodeEdgeGraph("a graph");
            makeSimpleGraph(simpleGraph);
            simpleGraph.addUndirectedEdge("lab 3", "lab 1"); // This edge should make it impossible to schedule
            Assert.assertThrows(
                    NoScheduleException.class,
                    () -> Scheduler.findSchedule(simpleGraph));
        }
        catch (NodeNameExistsException e) {
            Assert.fail("Could not create graph to test");
        }
    }

    @Test
    public void testAllocate() {
        try {
            IGraph test = new NodeEdgeGraph("a graph");
            makeSimpleGraph(test);
            ArrayList<HashSet<String>> allocate = Scheduler.findSchedule(test);
            Assert.assertTrue(Scheduler.checkValidity(test, allocate));
        } catch (NoScheduleException | NodeNameExistsException e) {
            Assert.fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testAllocateWithComplexGraph() {
        try {
            IGraph ourMade6 = new EdgeArrayGraph("ourMade6");
            ourMadeGraph6(ourMade6);
            ArrayList<HashSet<String>> allocate = Scheduler.findSchedule(ourMade6);

            Assert.assertTrue(Scheduler.checkValidity(ourMade6, allocate));

        } catch (NoScheduleException | NodeNameExistsException e) {
            Assert.fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testFindSchedule() {
        try {
            IGraph ourMade6 = new EdgeArrayGraph("ourMade6");
            ourMadeGraph6(ourMade6);

            ArrayList<HashSet<String>> schedule = Scheduler.findSchedule(ourMade6);
            Assert.assertNotNull( schedule);
            Assert.assertEquals( 2, schedule.size());

            Assert.assertTrue(Scheduler.checkValidity(ourMade6, schedule));
        } catch (NoScheduleException | NodeNameExistsException e) {
            Assert.fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testCheckValidityWithEmptyGraph() {
        IGraph emptyGraph = new NodeEdgeGraph("empty graph");
        ArrayList<HashSet<String>> proposedSchedule = new ArrayList<>();
        Assert.assertTrue(Scheduler.checkValidity(emptyGraph, proposedSchedule));
    }

    
}
