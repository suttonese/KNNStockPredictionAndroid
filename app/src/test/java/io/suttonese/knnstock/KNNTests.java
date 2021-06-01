package io.suttonese.knnstock;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.suttonese.knnstock.model.Distance;
import io.suttonese.knnstock.model.Price;

public class KNNTests {
    ArrayList<Price> trainingData = new ArrayList<>();
    KNNEngine knnEngine = new KNNEngine();

    @Before
    public void init () {
        Price priceA = new Price(5, 6.5);
        Price priceB = new Price(4.5, 4);
        Price priceC = new Price(5.5, 5);
        Price priceD = new Price(5.2, 5);
        trainingData.add(priceA);
        trainingData.add(priceB);
        trainingData.add(priceC);
        trainingData.add(priceD);
    }

    @Test
    public void testDistance() {

        Price priceA = new Price(4.5, 5);
        Price priceB = new Price(5, 5.5);

        assert Double.valueOf(Distance.getDistance(priceA, priceB)).equals(0.5);
    }

    @Test
    public void testClassifyKOne() {
        Price test1 = new Price();
        test1.setClosePrice(5);

        Price prediction = knnEngine.classify(test1, trainingData, 1);
        assert Double.valueOf(prediction.getClosePrice()).equals(5.0);
        assert Double.valueOf(prediction.getNextDayClosePrice()).equals(6.5);
    }
    @Test
    public void testClassifyKTwo() {

        Price test2 = new Price();
        test2.setClosePrice(6);
        Price prediction = knnEngine.classify(test2, trainingData, 2);
        assert Double.valueOf(prediction.getClosePrice()).equals(6.0);
        assert Double.valueOf(prediction.getNextDayClosePrice()).equals(5.0);
    }

    @Test
    public void testClassifyKThree() {

        Price test3 = new Price();
        test3.setClosePrice(5.1);
        Price prediction = knnEngine.classify(test3, trainingData, 3);

        assert Double.valueOf(prediction.getClosePrice()).equals(5.1);
        assert Double.valueOf(prediction.getNextDayClosePrice()).equals(5.5);
    }

    @Test (expected = KNNException.class)
    public void testLeastSwquareException() throws KNNException {
        ArrayList<Price> testPrices = new ArrayList<>();
        Price testA = new Price(5, 5);
        testPrices.add(testA);
        ArrayList<Price> predictions = new ArrayList<>();
        Price predictionA = new Price(5, 5.5);
        Price predictionB = new Price(4.5, 5.5);
        predictions.add(predictionA);
        predictions.add(predictionB);
        knnEngine.leastSquare(testPrices, predictions);
    }

    @Test
    public void testLeastSwquare() throws KNNException {
        ArrayList<Price> testPrices = new ArrayList<>();
        Price testA = new Price(5, 5);
        Price testB = new Price(4.5, 5);
        Price testC = new Price(6, 5);
        testPrices.add(testA);
        testPrices.add(testB);
        testPrices.add(testC);
        ArrayList<Price> predictions = new ArrayList<>();
        Price predictionA = new Price(5, 5.5);
        Price predictionB = new Price(4.5, 5.5);
        Price predictionC = new Price(6, 5.5);
        predictions.add(predictionA);
        predictions.add(predictionB);
        predictions.add(predictionC);
        assert Double.valueOf(knnEngine.leastSquare(testPrices, predictions)).equals(0.75);
    }
}