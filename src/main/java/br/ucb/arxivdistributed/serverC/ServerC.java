package br.ucb.arxivdistributed.serverC;

import br.ucb.arxivdistributed.util.Config;
import br.ucb.arxivdistributed.util.SearchUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Servidor C que realiza buscas na parte 2 dos dados (arxiv_part2.json).
 */
public class ServerC {

    private static final String DATA_FILE = "data/arxiv_part2.json";
    private static final JSONArray cachedData = SearchUtils.loadJsonFileCached(DATA_FILE);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_C_PORT)) {
            System.out.println("Servidor C aguardando na porta " + Config.SERVER_C_PORT + "...");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    System.out.println("Conectado ao Servidor A");
                    System.out.println("[INFO] Servidor C conectado e aguardando busca...");

                    String query = in.readLine();
                    if (query == null || query.isBlank()) {
                        out.println("[ERRO] Consulta inválida recebida.");
                        continue;
                    }

                    String result = performSearch(query);
                    out.println(result);

                } catch (IOException e) {
                    System.err.println("[ERRO] Comunicação com Servidor A falhou: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("[ERRO] Falha ao iniciar Servidor C: " + e.getMessage());
        }
    }

    private static String performSearch(String query) {
        StringBuilder results = new StringBuilder();

        try {
            for (int i = 0; i < cachedData.length(); i++) {
                JSONObject article = cachedData.getJSONObject(i);
                String title = article.optString("title", "");
                String summary = article.optString("abstract", "");

                if (SearchUtils.containsSubstring(title, query) || SearchUtils.containsSubstring(summary, query)) {
                    results.append("Título: ").append(title).append("\nResumo: ").append(summary).append("\n\n");
                }
            }
        } catch (Exception e) {
            System.err.println("[ERRO] Erro durante busca: " + e.getMessage());
            return "[ERRO] Falha ao acessar os dados no servidor.\n";
        }

        if (results.isEmpty()) {
            return "⚠️ Nenhum resultado encontrado para a consulta.";
        }

        return results.toString();
    }
}