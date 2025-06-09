package br.ucb.arxivdistributed.util;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Utilitários para busca de padrões em textos e carregamento de arquivos JSON.
 */
public class SearchUtils {

    // Cache estático em memória para arquivos JSON já carregados
    private static final Map<String, JSONArray> cache = new ConcurrentHashMap<>();

    /**
     * Verifica se 'pattern' é substring de 'text', ignorando maiúsculas/minúsculas.
     *
     * @param text    Texto onde será feita a busca.
     * @param pattern Substring a buscar.
     * @return true se pattern está contido em text; false caso contrário.
     */
    public static boolean containsSubstring(String text, String pattern) {
        if (text == null || pattern == null || pattern.isEmpty()) return false;
        return KMPAlgorithm.contains(text.toLowerCase(), pattern.toLowerCase());
    }

    /**
     * Carrega e mantém na memória o conteúdo JSON de um arquivo.
     * Na primeira chamada, o conteúdo é lido do disco; nas demais, do cache.
     *
     * @param filePath Caminho do arquivo JSON.
     * @return JSONArray carregado.
     */
    public static JSONArray loadJsonFileCached(String filePath) {
        return cache.computeIfAbsent(filePath, path -> {
            try (InputStream is = new FileInputStream(path)) {
                return new JSONArray(new JSONTokener(is));
            } catch (Exception e) {
                System.err.println("[ERRO] Falha ao carregar JSON: " + e.getMessage());
                return new JSONArray(); // retorno seguro
            }
        });
    }

    /**
     * Versão sem cache (ainda usada para testes ou leitura direta).
     *
     * @param filePath Caminho do arquivo.
     * @return JSONArray.
     * @throws IOException Se falhar.
     */
    public static JSONArray loadJsonFile(String filePath) throws IOException {
        try (InputStream is = new FileInputStream(filePath)) {
            return new JSONArray(new JSONTokener(is));
        }
    }

    // Método de teste simples
    public static void main(String[] args) {
        String text = "Exemplo de artigo sobre aprendizado de máquina.";
        String query = "aprendizado";

        System.out.println("Contém? " + containsSubstring(text, query));
    }
}