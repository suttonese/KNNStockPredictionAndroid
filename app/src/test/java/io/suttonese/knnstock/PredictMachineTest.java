package io.suttonese.knnstock;


import junit.framework.TestCase;

import org.junit.Test;

import java.io.IOException;

public class PredictMachineTest extends TestCase {

    PredictMachine predictMachine = new PredictMachine();

    @Test
    public void testDownLoadData() throws IOException {
        predictMachine.downLoadData("AAPL");
    }

    @Test
    public void testPredict() throws KNNException {
        assertTrue(predictMachine.predict("AAPL").contains("the predicted price is"));
    }

}