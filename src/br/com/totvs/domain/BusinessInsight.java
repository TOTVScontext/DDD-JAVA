package br.com.totvs.domain;

public class BusinessInsight extends Insight {
    private double potentialValue;

    public BusinessInsight(){
    }
    public BusinessInsight(String message, int priority, double potentialValue){
        super(message, priority);
        this.potentialValue = potentialValue;
    }

    @Override
    public String getMessage() {
        return "[OPORTUNIDADE DE NEGOCIO] " + super.getMessage() + " | Valor Potencial: R$ " + this.potentialValue;
    }
}
