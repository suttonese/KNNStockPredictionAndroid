package io.suttonese.knnstock.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Price {
    private double closePrice;
    private double nextDayClosePrice;
}