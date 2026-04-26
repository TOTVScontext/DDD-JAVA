package br.com.totvs.domain;

public class Analyzer {
    private String nlpVersion;

    public Analyzer(String nlpVersion) {
        this.nlpVersion = nlpVersion;
    }

    public Analysis analyze(Conversation conversation) {
        String texto = conversation.getText().toLowerCase().trim(); /// deixa tudo minusculo

        double notaProdutividade = 5.0;
        double notaSentimento = 8.0;

        if (texto.contains("senior") || texto.contains("cancelar") || texto.contains("caro")) {
            notaSentimento = 3.0;
        }
        if (texto.contains("comprar") || texto.contains("gostei") || texto.contains("rm")) {
            notaProdutividade = 9.5;
        }

        return new Analysis(notaProdutividade, notaSentimento, 5.0);
    }
}