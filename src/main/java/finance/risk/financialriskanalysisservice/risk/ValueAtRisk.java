package finance.risk.financialriskanalysisservice.risk;

import finance.risk.financialriskanalysisservice.dto.Data;

import java.util.ArrayList;
import java.util.List;

public class ValueAtRisk {
    public static double calculate(List<Data> records, double confidenceLevel, double portfolioValue) {
        if (records == null || records.size() == 0) {
            return 0.0;
        }
        var sortedChanges = calculateDailyChanges(records);
        // Calculate the index for the VaR based on the confidence level
        int varIndex = (int) Math.ceil((1 - confidenceLevel) * sortedChanges.size());
        var valueAtRisk = sortedChanges.get(varIndex - 1) * portfolioValue; // VaR is the price at this index
        return Math.abs(valueAtRisk);
    }
    // Method to calculate daily changes in closing prices
    public static List<Double> calculateDailyChanges(List<Data> dataList) {
        List<Double> dailyChanges = new ArrayList<>();

        for (int i = 1; i < dataList.size(); i++) {
            // Calculate the change from the previous day
            double change = (dataList.get(i).getClosingPrice() - dataList.get(i - 1).getClosingPrice())
                    / dataList.get(i - 1).getClosingPrice();
            dailyChanges.add(change);
        }

        return dailyChanges.stream().sorted().toList();
    }
}
