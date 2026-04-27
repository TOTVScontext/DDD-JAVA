package br.com.totvs.domain;

public class Analyzer {
    private String nlpVersion;

    public Analyzer(String nlpVersion) {
        this.nlpVersion = nlpVersion;
    }

    public Analysis analyze(Conversation conversation) {
        String texto = java.text.Normalizer
                .normalize(conversation.getText(), java.text.Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .trim();

        double notaProdutividade = 5.0;
        double notaSentimento = 8.0;

        if (texto.contains("senior") || texto.contains("cancelar") || texto.contains("caro")) {
            notaSentimento = 3.0;
        }

        if (texto.contains("comprar") || texto.contains("gostei") || texto.contains("rm")) {
            notaProdutividade = 9.5;
        }

        boolean temReclamacao = texto.contains("trava") || texto.contains("lento")
                || texto.contains("péssimo") || texto.contains("pessimo")
                || texto.contains("reclamação") || texto.contains("reclamacao");

        boolean temBudget = texto.contains("mil") || texto.contains("r$")
                || texto.contains("investimento") || texto.contains("valor");

        boolean temPersona = texto.contains("cfo") || texto.contains("diretor")
                || texto.contains("gestor") || texto.contains("roi");

        boolean temSentimentoMisto = (texto.contains("satisfeito") || texto.contains("gosto"))
                && (texto.contains("frustrado") || texto.contains("sofrendo"));

        boolean temConfianca = texto.contains("confiança") || texto.contains("confianca")
                || texto.contains("confio");

        return new Analysis(notaProdutividade, notaSentimento, 5.0, temReclamacao, temBudget, temPersona, temSentimentoMisto, temConfianca);
    }
}