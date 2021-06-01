package io.suttonese.knnstock;

import io.suttonese.knnstock.model.Price;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class PredictMachine {
    List<BigDecimal> historicalQuotes = new ArrayList<>();
    ArrayList<Price> prices = new ArrayList<>();
    ArrayList<Price> predictions = new ArrayList<>();
    KNNEngine knnEngine = new KNNEngine();

    public void downLoadData(String symbol) throws IOException {

        Stock stock = YahooFinance.get(symbol, true);
        if (null == stock) {
            System.out.println("No data available for symbol " + symbol);
            return;
        }
        List<HistoricalQuote> historicalQuotes = stock.getHistory(Interval.DAILY);

        for (HistoricalQuote quote : historicalQuotes) {
            this.historicalQuotes.add(quote.getClose());
        }
    }

    public String predict(String symbol) throws KNNException {
        try {
            prepareData(symbol);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        if (prices.isEmpty()) {
            return "Seems " + symbol + " is not a valid symbol. Try again...";
        }

        Map<Integer, Double> kAndLeastSquare = new HashMap<>();
        for (int k = 1; k <= 10; k++) {
            predictions.clear();
            for (int index = 0; index < prices.size(); index++) {
                Price test = prices.get(index);
                ArrayList<Price> training = new ArrayList<>(prices);
                training.remove(index);
                predictions.add(knnEngine.classify(test, training, k));
            }
/*            for (int i = 0; i < prices.size(); i++) {
                System.out.println(prices.get(i).toString() + "||" + predictions.get(i).toString());
            }*/
            kAndLeastSquare.put(k, knnEngine.leastSquare(prices, predictions));
        }

/*        for (int k = 1; k <= 10; k++) {
            System.out.println(k + " : " + kAndLeastSquare.get(k));
        }*/

        double smallest = kAndLeastSquare.get(1);
        int kToUse = 1;
        for (int k = 2; k <= 10; k++) {
            if (kAndLeastSquare.get(k) < smallest) {
                smallest = kAndLeastSquare.get(k);
                kToUse = k;
            }
        }
//        System.out.println(kToUse);
        Price lastPrice = new Price();
        lastPrice.setClosePrice(historicalQuotes.get(historicalQuotes.size() - 1).doubleValue());
        Price predictedPrice = knnEngine.classify(lastPrice, prices, kToUse);
        String prediction = "Based on " + symbol + "'s close price: " + predictedPrice.getClosePrice()
                + ", the predicted price is " + predictedPrice.getNextDayClosePrice();
        prices.clear();
        historicalQuotes.clear();
        return prediction;
    }

    public void prepareData(String symbol) throws IOException {
        downLoadData(symbol);
        for (int i = 0; i < historicalQuotes.size() - 1; i++) {
            Price price = new Price(historicalQuotes.get(i).doubleValue(), historicalQuotes.get(i + 1).doubleValue());
            prices.add(price);
        }
    }
}