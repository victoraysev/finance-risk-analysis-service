package finance.risk.financialriskanalysisservice.controller;

import finance.risk.financialriskanalysisservice.dto.Data;
import finance.risk.financialriskanalysisservice.service.FinancialDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/financial-analysis")
public class FinancialAnalysisController {


    @Autowired
    private FinancialDataService financialDataService;

    @GetMapping
    public double calculateVaR(@RequestParam String companyName, @RequestParam double confidenceLevel,
                               @RequestParam String startDate, @RequestParam String endDate,
                               @RequestParam double portfolioValue) {
        List<Data> records = financialDataService.getFinancialRecords(companyName, startDate, endDate, portfolioValue);
        return financialDataService.calculateVaR(records, confidenceLevel, portfolioValue);
    }
}
