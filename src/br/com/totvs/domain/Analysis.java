package br.com.totvs.domain;

public class Analysis {
    private double productivity;
    private double sentiment;
    private double resolution;

    public Analysis(double productivity, double sentiment, double resolution) {
        this.productivity = productivity;
        this.sentiment = sentiment;
        this.resolution = resolution;
    }

    public boolean isGood() {
        if (this.sentiment >= 7.0) {
            return true;
        } else {
            return false;
        }
    }

    public double getProductivity() {
        return productivity;
    }

    public void setProductivity(double productivity) {
        this.productivity = productivity;
    }

    public double getSentiment() {
        return sentiment;
    }

    public void setSentiment(double sentiment) {
        this.sentiment = sentiment;
    }

    public double getResolution() {
        return resolution;
    }

    public void setResolution(double resolution) {
        this.resolution = resolution;
    }
}