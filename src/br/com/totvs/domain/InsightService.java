package br.com.totvs.domain;

import java.util.ArrayList;
import java.util.List;

public class InsightService {
    private double alertThreshold;

    public InsightService(double insightService){
        this.alertThreshold = alertThreshold;
    }

    public List<Insight> generate(Analysis a) {
        List<Insight> insights = new ArrayList<>();

        if (a.getSentiment() < this.alertThreshold) {
            insights.add(new RiskInsight("Risco de Churn detectado!", 1, 80.0));
        }

        if (a.getProductivity() >= 8.0) {
            insights.add(new BusinessInsight("Oportunidade de Upsell!", 2, 50000.0));
        }

        return insights;
    }
}
