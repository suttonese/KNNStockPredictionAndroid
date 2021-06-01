package io.suttonese.knnstock;

import junit.framework.TestCase;

import io.suttonese.knnstock.model.Neighbour;

public class NeighbourTest extends TestCase {

    public void testLessThan() {
        Neighbour neighbour1 = new Neighbour(0.2, 5.5);
        Neighbour neighbour2 = new Neighbour(0.3, 6);
        assert neighbour1.compareTo(neighbour2) == -1;
    }

    public void testEquals() {
        Neighbour neighbour1 = new Neighbour(0.2, 5.5);
        Neighbour neighbour2 = new Neighbour(0.2, 6);
        assert neighbour1.compareTo(neighbour2) == 0;
    }

    public void testGreaterThan() {
        Neighbour neighbour1 = new Neighbour(0.3, 5.5);
        Neighbour neighbour2 = new Neighbour(0.2, 6);
        assert neighbour1.compareTo(neighbour2) == 1;
    }
}