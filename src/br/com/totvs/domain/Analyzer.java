package br.com.totvs.domain;

public class Analyzer {
    private String nlpVersion;

    public Analyzer(String nlpVersion){
        this.nlpVersion = nlpVersion;
    }

    public Analysis analyze(Conversation conversation) {
        System.out.println("Processando texto na versão NLP: " + this.nlpVersion);

        String texto = conversation.getText().toLowerCase();

        double notaProdutividade = 8.5;
        double notaSentimento = 8.0;
        double notaResolucao = 9.0;

        if (texto.contains("senior") || texto.contains("cancelar") || texto.contains("caro")) {
            notaSentimento -= 4.0;
            notaResolucao -= 2.0;
        }

        if (texto.contains("comprar") || texto.contains("gostei") || texto.contains("rm")) {
            notaProdutividade += 1.0;
        }
        return new Analysis(notaProdutividade, notaSentimento, notaResolucao);
    }
}
