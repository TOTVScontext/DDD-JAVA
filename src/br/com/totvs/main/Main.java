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
        System.out.println("\n--- Inteligencia de Interacoes Corporativas ---\n");


        System.out.println("Insira o ID da reunião:");
        String idConversa = scan.nextLine();

        System.out.println("Insira o texto transcrito da reunião:");
        String textoConversa = scan.nextLine();

        Conversation conversa = new Conversation(idConversa, textoConversa, Arrays.asList("Vendedor", "Cliente"));

        System.out.println("\n[Aguarde] Enviando texto para o motor cognitivo TOTVS...");

        Analyzer nlp = new Analyzer("Context-NLP-v1.0");
        Analysis analise = nlp.analyze(conversa);

        InsightService service = new InsightService(7.0);
        List<Insight> alertas = service.generate(analise);

        System.out.println("\n======================================================");
        System.out.println("                RELATÓRIO DE INSIGHTS                 ");
        System.out.println("======================================================");

        System.out.println("DEBUG - Produtividade lida: " + analise.getProductivity());
        System.out.println("DEBUG - Sentimento lido: " + analise.getSentiment());
        System.out.println("DEBUG - Quantidade de alertas gerados: " + alertas.size());

        for (Insight alerta : alertas) {
            System.out.println(">> " + alerta.getMessage());
        }

        System.out.println("======================================================\n");
        scan.close();

    }
}