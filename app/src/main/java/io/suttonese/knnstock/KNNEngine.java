package io.suttonese.knnstock;

import java.util.ArrayList;
import java.util.Arrays;

import io.suttonese.knnstock.model.Distance;
import io.suttonese.knnstock.model.Neighbour;
import io.suttonese.knnstock.model.Price;

public class KNNEngine {

    public Price classify(Price test, ArrayList<Price> train, int K){
        Neighbour[] neighbours = new Neighbour[train.size()];
        for (int i = 0; i < neighbours.length; i++){
            double distance = Distance.getDistance(train.get(i), test);
            double neighbourNextDayPrice = train.get(i).getNextDayClosePrice();
            neighbours[i] = new Neighbour(distance, neighbourNextDayPrice);
        }

        Arrays.sort(neighbours);

        double sumOfNeighbours = 0.0;

        for (int i = 0; i < K; i++) {
            sumOfNeighbours += neighbours[i].getPrice();
        }
        return new Price(test.getClosePrice(), sumOfNeighbours / K);
    }

    public double leastSquare(ArrayList<Price> test, ArrayList<Price> train) throws KNNException{
        double leastSquare = 0.0;
        if (!(test.size() == train.size())) {
            throw new KNNException("Test and Training set must have same size");
        }
        for (int i = 0; i < test.size(); i++) {
            double priceDifference = test.get(i).getNextDayClosePrice() - train.get(i).getNextDayClosePrice();
            leastSquare += Math.pow(priceDifference, 2);
        }
        return leastSquare;
    }
}