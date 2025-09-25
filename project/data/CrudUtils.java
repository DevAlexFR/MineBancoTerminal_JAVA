package project.data;

import java.math.BigDecimal;
import java.util.List;



// Aqui quis demonstrar o CRUD em Classe utilitária para operações CRUD de contas.
public class CrudUtils {

    // Cria uma conta e adiciona ao banco
    public static Conta criarConta(Banco banco, String numero, String titular, BigDecimal saldoInicial) {
        return banco.criarConta(numero, titular, saldoInicial);
    }

    // Deposita valor na conta
    public static void depositar(Banco banco, String numero, BigDecimal valor) {
        banco.getConta(numero).depositar(valor);
    }

    // Realiza saque
    public static void sacar(Banco banco, String numero, BigDecimal valor) {
        banco.getConta(numero).sacar(valor);
    }

    // Realiza transferência
    public static void transferir(Banco banco, String de, String para, BigDecimal valor) {
        banco.transferir(de, para, valor);
    }

    // Lista contas ordenadas por saldo
    public static List<Conta> listarContas(Banco banco) {
        return banco.listarOrdenadasPorSaldoDesc();
    }
}
