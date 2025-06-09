package br.ucb.arxivdistributed.util;

/**
 * Classe de configuração contendo os hosts e portas dos servidores.
 */
public final class Config {

    /**
     * Host do Servidor B.
     */
    public static final String SERVER_B_HOST = "localhost";

    /**
     * Porta do Servidor B.
     */
    public static final int SERVER_B_PORT = 5001;

    /**
     * Host do Servidor C.
     */
    public static final String SERVER_C_HOST = "localhost";

    /**
     * Porta do Servidor C.
     */
    public static final int SERVER_C_PORT = 5002;

    /**
     * Host do Servidor A.
     */
    public static final String SERVER_A_HOST = "localhost";

    /**
     * Porta do Servidor A (responsável por receber requisições do cliente).
     */
    public static final int SERVER_A_PORT = 5000;

    // Construtor privado para impedir instanciação
    private Config() {
    }
}