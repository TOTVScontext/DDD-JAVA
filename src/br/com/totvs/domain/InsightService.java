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
           double probabilidadeCancelamento = (10.0 - a.getSentiment()) * 10;
           insights.add(new RiskInsight("Cliente insatisfeito identificado no audio", 1, probabilidadeCancelamento));
       }
        if (a.getProductivity() >= 8.0) {
            insights.add(new BusinessInsight("Abertura para venda do módulo TOTVS RM.", 2, 50000.00));
        }
        return insights;
    }
}
