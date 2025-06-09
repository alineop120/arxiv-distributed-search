package br.ucb.arxivdistributed.serverA;

import br.ucb.arxivdistributed.util.Config;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Servidor A que atua como orquestrador do sistema distribuído,
 * recebendo consultas do cliente e encaminhando para os servidores B e C em paralelo.
 */
public class ServerA {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_A_PORT)) {
            System.out.println("Servidor A aguardando conexão na porta " + Config.SERVER_A_PORT + "...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Cliente conectado.");

                    String query = in.readLine();
                    if (query == null || query.isBlank()) {
                        System.out.println("[WARN] Consulta vazia ou conexão fechada pelo cliente.");
                        out.println("[ERRO] Consulta inválida.");
                        continue;
                    }

                    System.out.println("Recebida consulta: " + query);

                    Future<String> futureB = executor.submit(() -> consultaServidor(Config.SERVER_B_HOST, Config.SERVER_B_PORT, query));
                    Future<String> futureC = executor.submit(() -> consultaServidor(Config.SERVER_C_HOST, Config.SERVER_C_PORT, query));

                    String resultadoB;
                    String resultadoC;

                    try {
                        resultadoB = futureB.get();
                    } catch (Exception e) {
                        resultadoB = "[ERRO] Falha ao consultar servidor B.\n";
                        System.err.println("[ERRO] Consulta ao servidor B falhou: " + e.getMessage());
                    }

                    try {
                        resultadoC = futureC.get();
                    } catch (Exception e) {
                        resultadoC = "[ERRO] Falha ao consultar servidor C.\n";
                        System.err.println("[ERRO] Consulta ao servidor C falhou: " + e.getMessage());
                    }

                    // Remove linhas em branco extras
                    String respostaFinal = (resultadoB.trim() + "\n" + resultadoC.trim()).trim();

                    if (respostaFinal.isBlank()) {
                        respostaFinal = "⚠️ Nenhum resultado encontrado.";
                    }

                    out.println(respostaFinal);

                } catch (IOException e) {
                    System.err.println("[ERRO] Comunicação com cliente falhou: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("[ERRO] Falha ao iniciar o servidor: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    /**
     * Consulta um servidor (B ou C) enviando a query e retornando a resposta.
     */
    private static String consultaServidor(String host, int port, String query) {
        StringBuilder response = new StringBuilder();

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(query);

            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

        } catch (IOException e) {
            System.err.println("[ERRO] Falha ao conectar no servidor " + host + ":" + port + " - " + e.getMessage());
            return "[ERRO] Não foi possível consultar o servidor " + host + ":" + port + "\n";
        }

        return response.toString();
    }
}