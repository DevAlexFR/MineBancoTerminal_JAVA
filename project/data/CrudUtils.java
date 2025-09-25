package project.data;

import java.math.BigDecimal;
import java.util.List;

// Aqui quis demonstrar o CRUD em Classe utilitária para operações CRUD de contas.
public class CrudUtils {
    public static Conta criarConta(Banco banco, String numero, String titular, BigDecimal saldoInicial) {
        return banco.criarConta(numero, titular, saldoInicial);
    }


    public static void depositar(Banco banco, String numero, BigDecimal valor, Extrato extrato) {
        banco.getConta(numero).depositar(valor);
        extrato.registrar("depósito", numero, null, valor);
    }


    public static void sacar(Banco banco, String numero, BigDecimal valor, Extrato extrato) {
        banco.sacar(numero, valor);
        extrato.registrar("saque", numero, null, valor);
    }


    public static void transferir(Banco banco, String de, String para, BigDecimal valor, Extrato extrato) {
        banco.transferir(de, para, valor);
        extrato.registrar("transferência", de, para, valor);
    }


    public static List<Conta> listarContas(Banco banco) {
        return banco.listarOrdenadasPorSaldoDesc();
    }
}
