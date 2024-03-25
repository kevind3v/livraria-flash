package support;

import org.mindrot.jbcrypt.BCrypt;

public class Passwd {
    public static String gerarHashSenha(String senhaPlana) {
        // Gera um hash de senha
        return BCrypt.hashpw(senhaPlana, BCrypt.gensalt());
    }

    public static boolean verificarSenha(String senhaPlana, String hashArmazenado) {
        // Verifica se a senha fornecida corresponde ao hash armazenado
        return BCrypt.checkpw(senhaPlana, hashArmazenado);
    }
}
