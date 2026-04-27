package br.com.totvs.domain;

public class Analysis {
    private double productivity;
    private double sentiment;
    private double resolution;
    private boolean hasComplaint;
    private boolean hasBudget;
    private boolean hasPersona;
    private boolean hasMixedSentiment;
    private boolean hasTrust;

    public Analysis(double productivity, double sentiment, double resolution,
                    boolean hasComplaint, boolean hasBudget, boolean hasPersona,
                    boolean hasMixedSentiment, boolean hasTrust) {
        this.productivity = productivity;
        this.sentiment = sentiment;
        this.resolution = resolution;
        this.hasComplaint = hasComplaint;
        this.hasBudget = hasBudget;
        this.hasPersona = hasPersona;
        this.hasMixedSentiment = hasMixedSentiment;
        this.hasTrust = hasTrust;
    }

    public boolean isGood() {
        return this.sentiment >= 7.0;
    }

    public double getProductivity() { return productivity; }
    public void setProductivity(double productivity) { this.productivity = productivity; }
    public double getSentiment() { return sentiment; }
    public void setSentiment(double sentiment) { this.sentiment = sentiment; }
    public double getResolution() { return resolution; }
    public void setResolution(double resolution) { this.resolution = resolution; }
    public boolean isHasComplaint() { return hasComplaint; }
    public boolean isHasBudget() { return hasBudget; }
    public boolean isHasPersona() { return hasPersona; }
    public boolean isHasMixedSentiment() { return hasMixedSentiment; }
    public boolean isHasTrust() { return hasTrust; }
}