package finance.risk.financialriskanalysisservice.service;

import finance.risk.financialriskanalysisservice.dto.Data;
import finance.risk.financialriskanalysisservice.risk.ValueAtRisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialDataService {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://processing-app:8080/financial-records"; // Replace with actual base URL

    @Autowired
    public FinancialDataService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves financial records for a specific company from the external API.
     *
     * @param companyName the name of the company
     * @param startDate
     * @param endDate
     * @return a list of financial records for the company
     */
    public List<Data> getFinancialRecords(String companyName, String startDate, String endDate, double portfolioValue) {
        String url = baseUrl + "?companyName=" + companyName +
                "&startDate=" + startDate + "&endDate=" + endDate;
        ResponseEntity<List<Data>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody() != null ? response.getBody() : new ArrayList<>();
    }

    /**
     * Retrieves all financial records from the external API.
     *
     * @return a list of all financial records
     */
    public List<Record> getAllRecords() {
        ResponseEntity<List<Record>> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }
    public double calculateVaR(List<Data> records, double confidenceLevel, double portfolioValue) {
        return ValueAtRisk.calculate(records, confidenceLevel, portfolioValue);
    }
}
