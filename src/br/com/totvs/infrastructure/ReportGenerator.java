package br.com.totvs.infrastructure;

import br.com.totvs.domain.Analysis;
import br.com.totvs.domain.Conversation;
import br.com.totvs.domain.Insight;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.filechooser.FileSystemView;
import java.io.File;


public class ReportGenerator {

    private static final String AZUL_TOTVS  = "003087";
    private static final String AZUL_CLARO  = "0050C8";
    private static final String CINZA_LINHA = "E8EDF2";
    private static final String BRANCO      = "FFFFFF";
    private static final String VERMELHO    = "C0392B";
    private static final String VERDE       = "1A7A4A";
    private static final String AMARELO     = "B7770D";
    private static final String FUNDO_RISCO = "FFF0F0";
    private static final String FUNDO_OPO   = "F0FFF4";
    private static final String FUNDO_INFO  = "FFFBF0";

    public static String generate(Conversation conversa, Analysis analise,
                                  List<Insight> insights, java.util.Scanner scan) {
        String idReuniao     = conversa.getId();
        String transcricao   = conversa.getText();
        String participantes = String.join(", ", conversa.getParticipants());
        String dataHora      = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        List<String> riscos = insights.stream().map(Insight::getMessage)
                .filter(m -> m.startsWith("[ALERTA")).collect(Collectors.toList());
        List<String> oportunidades = insights.stream().map(Insight::getMessage)
                .filter(m -> m.startsWith("[OPORTUNIDADE")).collect(Collectors.toList());
        List<String> informacoes = insights.stream().map(Insight::getMessage)
                .filter(m -> !m.startsWith("[ALERTA") && !m.startsWith("[OPORTUNIDADE"))
                .collect(Collectors.toList());

        String nomeArquivo = "analise-" + idReuniao + ".docx";
        File documentos = FileSystemView.getFileSystemView().getDefaultDirectory();
        String padrao = documentos.getAbsolutePath() + File.separator + nomeArquivo;

        System.out.println("  Onde salvar o relatorio?");
        System.out.println("  \u001B[90m  Exemplo Windows : C:\\Users\\user\\Documents\\" + nomeArquivo + "\u001B[0m");
        System.out.println("  \u001B[90m  Exemplo Mac/Linux: /home/user/documentos/" + nomeArquivo + "\u001B[0m");
        System.out.print("\n  Caminho (Enter para salvar em " + padrao + "): ");

        String entrada = scan.nextLine().trim();
        String outputPath;

        if (entrada.isEmpty()) {
            outputPath = padrao;
        } else {
            // Se o usuario digitou so uma pasta, adiciona o nome do arquivo
            File f = new File(entrada);
            if (f.isDirectory() || entrada.endsWith(File.separator) || entrada.endsWith("/") || entrada.endsWith("\\")) {
                outputPath = entrada + File.separator + nomeArquivo;
            } else {
                outputPath = entrada.endsWith(".docx") ? entrada : entrada + ".docx";
            }
        }

        // Garante que a pasta existe
        File arquivo = new File(outputPath);
        if (arquivo.getParentFile() != null && !arquivo.getParentFile().exists()) {
            arquivo.getParentFile().mkdirs();
        }

        try {
            gerarDocx(outputPath, idReuniao, transcricao, participantes, dataHora,
                    analise, riscos, oportunidades, informacoes, insights.size());
            return outputPath;
        } catch (Exception e) {
            return null;
        }
    }

    // ── Montagem do DOCX ─────────────────────────────────────

    private static void gerarDocx(String caminho, String id, String transcricao,
                                  String participantes, String dataHora, Analysis analise,
                                  List<String> riscos, List<String> oportunidades,
                                  List<String> informacoes, int total) throws IOException {

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(caminho))) {
            escrever(zip, "[Content_Types].xml",         contentTypes());
            escrever(zip, "_rels/.rels",                 relsRaiz());
            escrever(zip, "word/_rels/document.xml.rels",relsDoc());
            escrever(zip, "word/styles.xml",             styles());
            escrever(zip, "word/settings.xml",           settings());
            escrever(zip, "word/document.xml",
                    document(id, transcricao, participantes, dataHora,
                            analise, riscos, oportunidades, informacoes, total));
        }
    }

    private static void escrever(ZipOutputStream zip, String nome, String conteudo) throws IOException {
        zip.putNextEntry(new ZipEntry(nome));
        zip.write(conteudo.getBytes(StandardCharsets.UTF_8));
        zip.closeEntry();
    }

    // ── Arquivos estruturais ──────────────────────────────────

    private static String contentTypes() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Types xmlns=\"http://schemas.openxmlformats.org/package/2006/content-types\">"
                + "<Default Extension=\"rels\" ContentType=\"application/vnd.openxmlformats-package.relationships+xml\"/>"
                + "<Default Extension=\"xml\" ContentType=\"application/xml\"/>"
                + "<Override PartName=\"/word/document.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml\"/>"
                + "<Override PartName=\"/word/styles.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml\"/>"
                + "<Override PartName=\"/word/settings.xml\" ContentType=\"application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml\"/>"
                + "</Types>";
    }

    private static String relsRaiz() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument\" Target=\"word/document.xml\"/>"
                + "</Relationships>";
    }

    private static String relsDoc() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">"
                + "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles\" Target=\"styles.xml\"/>"
                + "<Relationship Id=\"rId2\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings\" Target=\"settings.xml\"/>"
                + "</Relationships>";
    }

    private static String settings() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<w:settings xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">"
                + "<w:defaultTabStop w:val=\"720\"/>"
                + "</w:settings>";
    }

    private static String styles() {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<w:styles xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">"
                + "<w:docDefaults><w:rPrDefault><w:rPr>"
                + "<w:rFonts w:ascii=\"Arial\" w:hAnsi=\"Arial\"/><w:sz w:val=\"20\"/>"
                + "</w:rPr></w:rPrDefault></w:docDefaults>"
                + "</w:styles>";
    }

    // ── Corpo principal ───────────────────────────────────────

    private static String document(String id, String transcricao, String participantes,
                                   String dataHora, Analysis analise,
                                   List<String> riscos, List<String> oportunidades,
                                   List<String> informacoes, int total) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        sb.append("<w:document xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">");
        sb.append("<w:body>");

        // Cabeçalho
        sb.append(paraH1("TOTVS CONTEXT"));
        sb.append(paraTexto("Relatorio de Inteligencia de Interacoes Corporativas", "22", false, AZUL_CLARO, 0, 160));
        sb.append(divisor(AZUL_CLARO));

        // Identificacao
        sb.append(paraH2("Identificacao da Reuniao"));
        sb.append(inicioTabela(9360));
        sb.append(linhaDupla("ID da Reuniao",     id,                              AZUL_TOTVS, BRANCO,     BRANCO,     "000000"));
        sb.append(linhaDupla("Data e Hora",        dataHora,                        CINZA_LINHA,"000000",   BRANCO,     "000000"));
        sb.append(linhaDupla("Participantes",       participantes,                   AZUL_TOTVS, BRANCO,     BRANCO,     "000000"));
        sb.append(linhaDupla("Total de Insights",   total + " sinais identificados", CINZA_LINHA,"000000",   BRANCO,     "000000"));
        sb.append(fimTabela());

        // Transcricao
        sb.append(espacamento(240));
        sb.append(paraH2("1. Transcricao da Reuniao"));
        sb.append(divisor(AZUL_CLARO));
        sb.append(paraTexto(xml(transcricao), "20", false, "333333", 80, 160));

        // Metricas
        sb.append(espacamento(240));
        sb.append(paraH2("2. Metricas da Analise"));
        sb.append(divisor(AZUL_CLARO));
        sb.append(paraTexto("Indicadores extraidos pelo motor cognitivo TOTVS Context NLP v1.0.", "18", false, "666666", 0, 120));
        sb.append(inicioTabela(9360));
        sb.append(linhaTripla("Indicador", "Nota / 10", "Status", AZUL_TOTVS, BRANCO, true));
        sb.append(linhaMetrica("Produtividade", analise.getProductivity(), BRANCO));
        sb.append(linhaMetrica("Sentimento",    analise.getSentiment(),    CINZA_LINHA));
        sb.append(linhaMetrica("Resolucao",     analise.getResolution(),   BRANCO));
        sb.append(fimTabela());

        // Insights
        sb.append(espacamento(240));
        sb.append(paraH2("3. Insights Identificados"));
        sb.append(divisor(AZUL_CLARO));

        if (!riscos.isEmpty()) {
            sb.append(paraH3("3.1 Alertas de Risco"));
            sb.append(inicioTabela(9360));
            for (String r : riscos) sb.append(linhaInsight("!", xml(r), FUNDO_RISCO, VERMELHO));
            sb.append(fimTabela());
            sb.append(espacamento(120));
        }
        if (!oportunidades.isEmpty()) {
            sb.append(paraH3("3.2 Oportunidades de Negocio"));
            sb.append(inicioTabela(9360));
            for (String o : oportunidades) sb.append(linhaInsight("+", xml(o), FUNDO_OPO, VERDE));
            sb.append(fimTabela());
            sb.append(espacamento(120));
        }
        if (!informacoes.isEmpty()) {
            sb.append(paraH3("3.3 Informacoes Adicionais"));
            sb.append(inicioTabela(9360));
            for (String i : informacoes) sb.append(linhaInsight("i", xml(i), FUNDO_INFO, AMARELO));
            sb.append(fimTabela());
            sb.append(espacamento(120));
        }

        // Recomendacoes
        sb.append(espacamento(240));
        sb.append(paraH2("4. Recomendacoes para a Equipe Comercial"));
        sb.append(divisor(AZUL_CLARO));
        if (!riscos.isEmpty())
            sb.append(paraBullet("Acionar equipe de retencao imediatamente para tratar risco de churn identificado."));
        if (!oportunidades.isEmpty())
            sb.append(paraBullet("Preparar proposta comercial detalhada com base no sinal de upsell detectado."));
        if (informacoes.stream().anyMatch(i -> i.contains("Budget")))
            sb.append(paraBullet("Registrar o valor de budget mencionado no CRM antes da proxima interacao."));
        if (informacoes.stream().anyMatch(i -> i.contains("Decisor")))
            sb.append(paraBullet("Direcionar comunicacao ao decisor identificado com foco em ROI."));
        if (informacoes.stream().anyMatch(i -> i.contains("suporte")))
            sb.append(paraBullet("Abrir ticket proativo de suporte para tratar reclamacao tecnica relatada."));
        if (informacoes.stream().anyMatch(i -> i.contains("confianca")))
            sb.append(paraBullet("Aproveitar o sinal de confianca para avancar no fechamento da proposta."));

        // Classificacao final
        sb.append(espacamento(240));
        sb.append(paraH2("5. Classificacao Final da Reuniao"));
        sb.append(divisor(AZUL_CLARO));

        String saudeFill  = analise.getSentiment() >= 7 ? "E8F8F0" : "FFF0F0";
        String saudeCor   = analise.getSentiment() >= 7 ? VERDE : VERMELHO;
        String saudeLabel = analise.getSentiment() >= 7 ? "POSITIVO" : "REQUER ATENCAO";
        String vendaLabel = !oportunidades.isEmpty() ? "ALTO" : "MODERADO";
        String vendaCor   = !oportunidades.isEmpty() ? VERDE : AMARELO;
        String urgLabel   = !riscos.isEmpty() ? "IMEDIATA" : "NORMAL";
        String urgFill    = !riscos.isEmpty() ? "FFF0F0" : "F0FFF4";
        String urgCor     = !riscos.isEmpty() ? VERMELHO : VERDE;

        sb.append(inicioTabela(9360));
        sb.append(linhaDupla("Saude Geral do Cliente", saudeLabel, AZUL_TOTVS, BRANCO,     saudeFill, saudeCor));
        sb.append(linhaDupla("Potencial de Venda",     vendaLabel, CINZA_LINHA,"000000",   CINZA_LINHA, vendaCor));
        sb.append(linhaDupla("Urgencia de Acao",       urgLabel,   AZUL_TOTVS, BRANCO,     urgFill,   urgCor));
        sb.append(fimTabela());

        // Rodape
        sb.append(espacamento(400));
        sb.append(paraTexto("Gerado em " + dataHora + "  |  Reuniao: " + xml(id) + "  |  TOTVS Context  |  Uso interno e confidencial",
                "16", false, "AAAAAA", 0, 0));

        sb.append("<w:sectPr>");
        sb.append("<w:pgSz w:w=\"11906\" w:h=\"16838\"/>");
        sb.append("<w:pgMar w:top=\"1134\" w:right=\"1134\" w:bottom=\"1134\" w:left=\"1134\"/>");
        sb.append("</w:sectPr>");
        sb.append("</w:body></w:document>");
        return sb.toString();
    }

    // ── Helpers de parágrafo ─────────────────────────────────

    private static String paraH1(String texto) {
        return "<w:p><w:pPr><w:spacing w:before=\"0\" w:after=\"80\"/></w:pPr>"
                + run(texto, "40", true, AZUL_TOTVS) + "</w:p>";
    }

    private static String paraH2(String texto) {
        return "<w:p><w:pPr><w:spacing w:before=\"280\" w:after=\"80\"/></w:pPr>"
                + run(texto, "26", true, AZUL_TOTVS) + "</w:p>";
    }

    private static String paraH3(String texto) {
        return "<w:p><w:pPr><w:spacing w:before=\"160\" w:after=\"60\"/></w:pPr>"
                + run(texto, "22", true, AZUL_CLARO) + "</w:p>";
    }

    private static String paraTexto(String texto, String sz, boolean bold, String cor, int before, int after) {
        return "<w:p><w:pPr><w:spacing w:before=\"" + before + "\" w:after=\"" + after + "\"/></w:pPr>"
                + run(texto, sz, bold, cor) + "</w:p>";
    }

    private static String paraBullet(String texto) {
        return "<w:p><w:pPr><w:ind w:left=\"360\"/><w:spacing w:before=\"60\" w:after=\"60\"/></w:pPr>"
                + "<w:r><w:rPr><w:sz w:val=\"20\"/><w:rFonts w:ascii=\"Arial\" w:hAnsi=\"Arial\"/></w:rPr>"
                + "<w:t xml:space=\"preserve\">&#x2022;  " + xml(texto) + "</w:t></w:r></w:p>";
    }

    private static String divisor(String cor) {
        return "<w:p><w:pPr>"
                + "<w:pBdr><w:bottom w:val=\"single\" w:sz=\"6\" w:space=\"1\" w:color=\"" + cor + "\"/></w:pBdr>"
                + "<w:spacing w:before=\"40\" w:after=\"40\"/></w:pPr></w:p>";
    }

    private static String espacamento(int val) {
        return "<w:p><w:pPr><w:spacing w:before=\"" + val + "\" w:after=\"0\"/></w:pPr></w:p>";
    }

    private static String run(String texto, String sz, boolean bold, String cor) {
        return "<w:r><w:rPr>"
                + (bold ? "<w:b/>" : "")
                + "<w:color w:val=\"" + cor + "\"/>"
                + "<w:sz w:val=\"" + sz + "\"/>"
                + "<w:rFonts w:ascii=\"Arial\" w:hAnsi=\"Arial\"/>"
                + "</w:rPr><w:t xml:space=\"preserve\">" + texto + "</w:t></w:r>";
    }

    // ── Helpers de tabela ────────────────────────────────────

    private static String inicioTabela(int largura) {
        return "<w:tbl><w:tblPr>"
                + "<w:tblW w:w=\"" + largura + "\" w:type=\"dxa\"/>"
                + "<w:tblBorders>"
                + "<w:top w:val=\"single\" w:sz=\"4\" w:color=\"CCCCCC\"/>"
                + "<w:left w:val=\"single\" w:sz=\"4\" w:color=\"CCCCCC\"/>"
                + "<w:bottom w:val=\"single\" w:sz=\"4\" w:color=\"CCCCCC\"/>"
                + "<w:right w:val=\"single\" w:sz=\"4\" w:color=\"CCCCCC\"/>"
                + "<w:insideH w:val=\"single\" w:sz=\"4\" w:color=\"CCCCCC\"/>"
                + "<w:insideV w:val=\"single\" w:sz=\"4\" w:color=\"CCCCCC\"/>"
                + "</w:tblBorders>"
                + "<w:tblCellMar>"
                + "<w:top w:w=\"80\" w:type=\"dxa\"/><w:left w:w=\"140\" w:type=\"dxa\"/>"
                + "<w:bottom w:w=\"80\" w:type=\"dxa\"/><w:right w:w=\"140\" w:type=\"dxa\"/>"
                + "</w:tblCellMar></w:tblPr>";
    }

    private static String fimTabela() { return "</w:tbl>"; }

    private static String celula(String texto, int largura, String fill, String cor, boolean bold) {
        return "<w:tc>"
                + "<w:tcPr><w:tcW w:w=\"" + largura + "\" w:type=\"dxa\"/>"
                + "<w:shd w:val=\"clear\" w:color=\"auto\" w:fill=\"" + fill + "\"/></w:tcPr>"
                + "<w:p><w:pPr><w:spacing w:before=\"60\" w:after=\"60\"/></w:pPr>"
                + run(texto, "20", bold, cor)
                + "</w:p></w:tc>";
    }

    private static String linhaDupla(String c1, String c2,
                                     String fill1, String cor1,
                                     String fill2, String cor2) {
        return "<w:tr>"
                + celula(c1, 3600, fill1, cor1, true)
                + celula(c2, 5760, fill2, cor2, false)
                + "</w:tr>";
    }

    private static String linhaTripla(String c1, String c2, String c3,
                                      String fill, String cor, boolean bold) {
        return "<w:tr>"
                + celula(c1, 4320, fill, cor, bold)
                + celula(c2, 2160, fill, cor, bold)
                + celula(c3, 2880, fill, cor, bold)
                + "</w:tr>";
    }

    private static String linhaMetrica(String label, double valor, String fill) {
        boolean bom   = valor >= 7.0;
        String  nota  = String.format("%.1f", valor);
        String  st    = bom ? "Adequado" : "Requer atencao";
        String  cor   = bom ? VERDE : VERMELHO;
        return "<w:tr>"
                + celula(label, 4320, fill, "000000", false)
                + celula(nota,  2160, fill, cor,      true)
                + celula(st,    2880, fill, cor,      false)
                + "</w:tr>";
    }

    private static String linhaInsight(String icone, String texto, String fill, String cor) {
        return "<w:tr>"
                + celula(icone, 480,  fill, cor,      true)
                + celula(texto, 8880, fill, "000000", false)
                + "</w:tr>";
    }

    // ── Escape XML ────────────────────────────────────────────

    private static String xml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}