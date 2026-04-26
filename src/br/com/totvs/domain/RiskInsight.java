package br.com.totvs.domain;

public class RiskInsight extends Insight{
    private double churnProb;

    public RiskInsight(String message, int priority, double churnProb){
        super(message, priority);
        this.churnProb = churnProb;
    }

    @Override
    public String getMessage() {
        return "[ALERTA DE RISCO - Nível " + super.getPriority() + "] " + super.getMessage() + " | Risco de Churn: " + this.churnProb + "%";
    }
}
