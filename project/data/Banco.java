package project.data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;



// Aqui quis separar tudo que e funcionalidade de Banco no caso criar conta, transferir, salvar ou carregar o dado do banco... 
public class Banco {
    private static final Logger logger = Logger.getLogger(Banco.class.getName());
    private final Map<String, Conta> contas = new HashMap<>();

    // Controle de saques diários por conta
    private final Map<String, BigDecimal> saquesDiarios = new HashMap<>();
    private LocalDate dataUltimoSaque = LocalDate.now();
    private final BigDecimal limiteDiario = new BigDecimal("1000.00");  // Limite diário de saque

    public Conta criarConta(String numero, String titular, BigDecimal saldoInicial) {
        logger.info("Criando conta - Número: " + numero + ", Titular: " + titular + ", Saldo inicial: " + saldoInicial);
        if (contas.containsKey(numero))
            throw new IllegalArgumentException("Conta já existe.");
        Conta c = new Conta(numero, titular, saldoInicial);
        contas.put(numero, c);
        return c;
    }

    public Conta getConta(String numero) {
        Conta c = contas.get(numero);
        if (c == null) {
            logger.severe("Conta não encontrada: " + numero);
            throw new NoSuchElementException("Conta não encontrada: " + numero);
        }
        return c;
    }

    public void transferir(String de, String para, BigDecimal valor) {
        if (de.equals(para)) {
            logger.severe("Contas de origem e destino não podem ser iguais.");
            throw new IllegalArgumentException("Contas de origem e destino não podem ser iguais.");
        }
        Conta origem = getConta(de);
        Conta destino = getConta(para);
        origem.sacar(valor);
        destino.depositar(valor);
    }

    // Metodo para atualizar controle dos saques diários, limpando ao mudar o dia
    private void verificarData() {
        LocalDate hoje = LocalDate.now();
        if (!hoje.equals(dataUltimoSaque)) {
            saquesDiarios.clear();
            dataUltimoSaque = hoje;
            logger.info("Limpeza do controle diário de saques para novo dia: " + hoje);
        }
    }

    //Método sacar modificado para bloqueio por limite diário
    public void sacar(String numero, BigDecimal valor) {
        verificarData();

        BigDecimal totalSacadoHoje = saquesDiarios.getOrDefault(numero, BigDecimal.ZERO);
        if (totalSacadoHoje.add(valor).compareTo(limiteDiario) > 0) {
            logger.warning("Tentativa de saque acima do limite diário para conta " + numero +
                ". Valor solicitado: " + valor + ", Já sacado hoje: " + totalSacadoHoje +
                ", Limite diário: " + limiteDiario);
            throw new IllegalArgumentException("Limite diário de saque ultrapassado.");
        }

        Conta conta = getConta(numero);

        conta.sacar(valor);
        saquesDiarios.put(numero, totalSacadoHoje.add(valor));
        logger.info("Saque efetuado para conta " + numero + ", valor: " + valor);
    }

    public List<Conta> listarOrdenadasPorSaldoDesc() {
        List<Conta> lst = new ArrayList<>(contas.values());
        lst.sort(Comparator.comparing(Conta::getSaldo).reversed());
        return lst;
    }

    public void salvar(Path path) throws IOException {
        List<String> linhas = new ArrayList<>();
        linhas.add("numero;titular;saldo");
        for (Conta c : listarOrdenadasPorSaldoDesc()) {
            linhas.add(String.join(";", c.getNumero(), c.getTitular(), c.getSaldo().toString()));
        }
        Files.write(path, linhas, StandardCharsets.UTF_8);
    }

    public void carregar(Path path) throws IOException {
        contas.clear();
        if (!Files.exists(path))
            return;
        List<String> linhas = Files.readAllLines(path, StandardCharsets.UTF_8);
        for (int i = 1; i < linhas.size(); i++) {
            String[] p = linhas.get(i).split(";");
            if (p.length < 3)
                continue;
            String numero = p[0];
            String titular = p[1];
            BigDecimal saldo = new BigDecimal(p[2]).setScale(2, RoundingMode.HALF_UP);
            contas.put(numero, new Conta(numero, titular, saldo));
        }
    }
}
