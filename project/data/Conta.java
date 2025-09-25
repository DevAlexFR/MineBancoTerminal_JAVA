package project.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;



// Aqui quis separar tudo que e funcionalidde referente a conta, depositar, sacar e validações na conta do cliente...
public class Conta {
    private final String numero;
    private final String titular;
    private BigDecimal saldo = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    public Conta(String numero, String titular, BigDecimal saldoInicial) {
        this.numero = Objects.requireNonNull(numero);
        this.titular = Objects.requireNonNull(titular);
        if (saldoInicial != null)
            this.saldo = saldoInicial.setScale(2, RoundingMode.HALF_UP);
    }


    public String getNumero() {
        return numero;
    }


    public String getTitular() {
        return titular;
    }


    public BigDecimal getSaldo() {
        return saldo;
    }


    public void depositar(BigDecimal valor) {
        validarValor(valor);
        saldo = saldo.add(valor).setScale(2, RoundingMode.HALF_UP);
    }


    public void sacar(BigDecimal valor) {
        validarValor(valor);
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }
        saldo = saldo.subtract(valor).setScale(2, RoundingMode.HALF_UP);
    }


    private static void validarValor(BigDecimal v) {
        if (v == null || v.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo.");
        }
    }

    
    @Override
    public String toString() {
        return numero + " | " + titular + " | R$ " + saldo;
    }
}
