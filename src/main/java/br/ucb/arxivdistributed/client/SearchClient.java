package br.ucb.arxivdistributed.client;

import br.ucb.arxivdistributed.util.Config;
import java.io.*;
import java.net.Socket;

/**
 * Cliente para o sistema distribuído de busca de artigos científicos.
 * Envia consulta ao servidor A e recebe resultados da busca.
 */
public class SearchClient {

    public static void main(String[] args) {
        System.out.println("🔍 Sistema de Busca Distribuído - Cliente");

        try (Socket socket = new Socket(Config.SERVER_A_HOST, Config.SERVER_A_PORT);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            System.out.print("Digite uma palavra ou trecho para buscar em artigos científicos: ");
            String query = userInput.readLine();

            if (query == null || query.isBlank()) {
                System.out.println("⚠️ Consulta vazia! Por favor, digite um termo válido.");
                return;
            }

            out.println(query);
            System.out.println("[INFO] Encaminhando consulta para servidores B e C...");

            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                responseBuilder.append(line).append("\n");
            }
            String response = responseBuilder.toString().trim();

            if (response.isEmpty()) {
                System.out.println("⚠️ Nenhum resultado encontrado ou houve uma falha na busca.");
            } else if (response.contains("[ERRO]")) {
                System.out.println("❌ Ocorreu um erro durante a busca:");
                System.out.println(response);
            } else {
                System.out.println("\n✅ Busca finalizada.");
                System.out.println("------ RESULTADOS ENCONTRADOS ------");
                System.out.println(response);
                System.out.println("------------ FIM DA BUSCA ----------");
            }

        } catch (IOException e) {
            System.err.println("[ERRO] Falha na comunicação com o servidor: " + e.getMessage());
        }

        System.out.println("Conexão encerrada.");
    }
}