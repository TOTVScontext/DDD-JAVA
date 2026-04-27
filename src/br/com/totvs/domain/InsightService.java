package br.com.totvs.domain;

import java.util.ArrayList;
import java.util.List;

public class InsightService {
    private double alertThreshold;

    public InsightService(double alertThreshold) {
        this.alertThreshold = alertThreshold;
    }

    public List<Insight> generate(Analysis a) {
        List<Insight> insights = new ArrayList<>();

        // Entregas mínimas
        if (a.getSentiment() < this.alertThreshold) {
            insights.add(new RiskInsight("Risco de Churn detectado!", 1, 80.0));
        }
        if (a.getProductivity() >= 8.0) {
            insights.add(new BusinessInsight("Oportunidade de Upsell!", 2, 50000.0));
        }

        // Diferenciais
        if (a.isHasComplaint()) {
            insights.add(new Insight("Reclamacao de produto identificada. Acionar suporte proativamente.", 3));
        }
        if (a.isHasBudget()) {
            insights.add(new Insight("Budget mencionado na conversa. Registrar para proposta comercial.", 2));
        }
        if (a.isHasPersona()) {
            insights.add(new Insight("Decisor identificado (CFO/Diretor/Gestor). Personalizar abordagem de ROI.", 2));
        }
        if (a.isHasMixedSentiment()) {
            insights.add(new Insight("Sentimento misto detectado. Cliente satisfeito parcialmente, risco latente.", 3));
        }
        if (a.isHasTrust()) {
            insights.add(new Insight("Cliente expressou confianca no vendedor. Momento ideal para fechar proposta.", 1));
        }

        return insights;
    }
}