package io.suttonese.knnstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import lombok.SneakyThrows;

public class DisplayPriceActivity extends AppCompatActivity {

    PredictMachine predictMachine = new PredictMachine();
    @SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_price);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String prediction = predictMachine.predict(message);

        TextView textView = findViewById(R.id.pricePrediction);
        textView.setText(prediction);
    }
}