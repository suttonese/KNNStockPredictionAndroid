package io.suttonese.knnstock.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Neighbour implements Comparable<Neighbour> {
    public double distance;
    public double price;

    @Override
    public int compareTo(Neighbour other) {
        return Double.compare(this.distance, other.distance);
    }

    public String toString() {
        return "Distance: " + distance + ", price: " + price;
    }
}