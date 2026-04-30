package br.com.totvs.main;

import br.com.totvs.domain.*;
import java.util.Scanner;
import java.util.List;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print(
                "\n" +
                        "\u001B[38;2;80;160;255m████████╗\u001B[38;2;75;150;250m ██████╗\u001B[38;2;70;140;245m ████████╗\u001B[38;2;65;130;240m██╗   ██╗\u001B[38;2;60;120;235m███████╗\n" +
                        "\u001B[38;2;75;150;250m╚══██╔══╝\u001B[38;2;70;140;245m██╔═══██╗\u001B[38;2;65;130;240m╚══██╔══╝\u001B[38;2;60;120;235m██║   ██║\u001B[38;2;55;110;230m██╔════╝\n" +
                        "\u001B[38;2;70;140;245m   ██║\u001B[38;2;65;130;240m   ██║   ██║\u001B[38;2;60;120;235m   ██║\u001B[38;2;55;110;230m   ██║   ██║\u001B[38;2;50;100;225m███████╗\n" +
                        "\u001B[38;2;65;130;240m   ██║\u001B[38;2;60;120;235m   ██║   ██║\u001B[38;2;55;110;230m   ██║\u001B[38;2;50;100;225m   ╚██╗ ██╔╝\u001B[38;2;45;90;220m╚════██║\n" +
                        "\u001B[38;2;60;120;235m   ██║\u001B[38;2;55;110;230m   ╚██████╔╝\u001B[38;2;50;100;225m   ██║\u001B[38;2;45;90;220m    ╚████╔╝ \u001B[38;2;40;80;215m███████║\n" +
                        "\u001B[38;2;55;110;230m   ╚═╝\u001B[38;2;50;100;225m    ╚═════╝ \u001B[38;2;45;90;220m   ╚═╝\u001B[38;2;40;80;215m     ╚═══╝  \u001B[38;2;35;70;210m╚══════╝\n" +
                        "\u001B[38;2;50;100;225m ██████╗\u001B[38;2;45;90;220m ██████╗ ███╗   ██╗████████╗███████╗██╗  ██╗████████╗\n" +
                        "\u001B[38;2;45;90;220m██╔════╝\u001B[38;2;40;80;215m██╔═══██╗████╗  ██║╚══██╔══╝██╔════╝╚██╗██╔╝╚══██╔══╝\n" +
                        "\u001B[38;2;40;80;215m██║\u001B[38;2;35;70;210m     ██║   ██║██╔██╗ ██║   ██║   █████╗   ╚███╔╝    ██║\n" +
                        "\u001B[38;2;35;70;210m██║\u001B[38;2;30;60;205m     ██║   ██║██║╚██╗██║   ██║   ██╔══╝   ██╔██╗    ██║\n" +
                        "\u001B[38;2;30;60;205m╚██████╗\u001B[38;2;25;50;200m╚██████╔╝██║ ╚████║   ██║   ███████╗██╔╝ ██╗   ██║\n" +
                        "\u001B[38;2;25;50;200m ╚═════╝\u001B[38;2;20;40;195m ╚═════╝ ╚═╝  ╚═══╝   ╚═╝   ╚══════╝╚═╝  ╚═╝   ╚═╝\u001B[0m\n"
        );
        System.out.println("  Inteligencia de Interacoes Corporativas\n");

        System.out.print("ID da reuniao: ");
        String idConversa = scan.nextLine();

        System.out.print("Transcricao: ");
        String textoConversa = scan.nextLine();

        Conversation conversa = new Conversation(idConversa, textoConversa, Arrays.asList("Vendedor", "Cliente"));

        System.out.println("\n  Analisando...\n");

        Analyzer nlp = new Analyzer("Context-NLP-v1.0");
        Analysis analise = nlp.analyze(conversa);

        InsightService service = new InsightService(7.0);
        List<Insight> alertas = service.generate(analise);

        // ── Métricas ─────────────────────────────────────────────
        System.out.println("  Metricas");
        System.out.println("  --------");
        System.out.printf("  Produtividade   %.1f / 10%n", analise.getProductivity());
        System.out.printf("  Sentimento      %.1f / 10  %s%n",
                analise.getSentiment(),
                analise.getSentiment() >= 7.0 ? "(positivo)" : "(negativo)");
        System.out.printf("  Resolucao       %.1f / 10%n", analise.getResolution());

        // ── Insights ─────────────────────────────────────────────
        System.out.println("\n  Insights  (" + alertas.size() + " encontrados)");
        System.out.println("  --------");

        int risco = 0, negocio = 0, info = 0;
        for (Insight alerta : alertas) {
            String msg = alerta.getMessage();
            if (msg.startsWith("[ALERTA")) {
                risco++;
                System.out.println("  \u001B[31m! " + msg + "\u001B[0m");
            } else if (msg.startsWith("[OPORTUNIDADE")) {
                negocio++;
                System.out.println("  \u001B[32m+ " + msg + "\u001B[0m");
            } else {
                info++;
                System.out.println("  \u001B[33m· " + msg + "\u001B[0m");
            }
        }

        // ── Resumo ────────────────────────────────────────────────
        System.out.println("\n  Resumo");
        System.out.println("  ------");
        System.out.println("  \u001B[31mRiscos         " + risco + "\u001B[0m");
        System.out.println("  \u001B[32mOportunidades  " + negocio + "\u001B[0m");
        System.out.println("  \u001B[33mInformacoes    " + info + "\u001B[0m");
        System.out.println("  \u001B[90mReuniao        " + idConversa + "\u001B[0m");

        // ── Relatório ─────────────────────────────────────────────
        System.out.println("\n  Relatorio");
        System.out.println("  ---------");
        System.out.print("  Gerar relatorio detalhado para a equipe? (s/n): ");
        String resposta = scan.nextLine().trim().toLowerCase();

        if (resposta.equals("s")) {
            System.out.println();
            String caminho = ReportGenerator.generate(conversa, analise, alertas, scan);
            System.out.println();
            if (caminho != null) {
                System.out.println("  \u001B[32mRelatorio salvo em:\u001B[0m");
                System.out.println("  \u001B[32m" + caminho + "\u001B[0m");
                System.out.print("\n  Abrir o arquivo agora? (s/n): ");
                if (scan.nextLine().trim().equalsIgnoreCase("s")) {
                    try {
                        java.awt.Desktop.getDesktop().open(new java.io.File(caminho));
                    } catch (Exception e) {
                        System.out.println("  \u001B[33mNao foi possivel abrir automaticamente. Acesse o caminho acima.\u001B[0m");
                    }
                }
            } else {
                System.out.println("  \u001B[31mErro ao gerar o relatorio.\u001B[0m");
            }
        }

        System.out.println();
        scan.close();
    }
}