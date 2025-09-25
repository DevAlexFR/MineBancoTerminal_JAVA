package project.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;



// Aqui temos o nosso front kkkkk a Classe principal do mini banco, usando o CrudUtils que e tudo referente a CRUD ta la...
public class MiniBanco {

    private final Banco banco = new Banco();
    private final Scanner sc = new Scanner(System.in);
    private final Path arquivo = Path.of("project/arquivos", "contas.csv");
    private static final Logger logger = Logger.getLogger(MiniBanco.class.getName());

    // Instancia para registrar extrato das operações
    private final Extrato extrato = new Extrato();

    public void executar() {
        try {
            logger.info("Demonstando contas, chamando carregar");
            banco.carregar(arquivo);
        } catch (IOException e) {
            logger.log(Level.FINE, "Aviso: não foi possível carregar contas: " + e.getMessage());
            System.out.println("Aviso: não foi possível carregar contas: " + e.getMessage());
        }

        logger.info("Demonstrando opções 1 a 6");
        while (true) {
            System.out.println("\n=== MINI BANCO ===");
            System.out.println("1) Criar conta");
            System.out.println("2) Depositar");
            System.out.println("3) Sacar");
            System.out.println("4) Transferir");
            System.out.println("5) Listar contas por saldo (desc)");
            System.out.println("6) Salvar e sair");
            System.out.print("Escolha: ");
            String op = sc.nextLine().trim();

            try {
                switch (op) {
                    case "1" -> criarConta();
                    case "2" -> depositar();
                    case "3" -> sacar();
                    case "4" -> transferir();
                    case "5" -> {
                        logger.info("5 Selecionado listar contas por saldo (desc)");
                        CrudUtils.listarContas(banco).forEach(System.out::println);
                    }
                    case "6" -> { // Aqui quis demonstrar dados na pasta data entao movi o csv para la criando e garantindo a existencia
                        logger.info("6 Selecionado salvar e sair");
                        try {
                            if (arquivo.getParent() != null) {
                                Files.createDirectories(arquivo.getParent());  // garante que pasta exista
                            }
                            banco.salvar(arquivo);
                            extrato.salvar();
                            logger.info("Arquivo salvo em " + arquivo.toAbsolutePath());
                            System.out.println("Salvo em " + arquivo.toAbsolutePath());
                            sc.close();
                            return;
                        } catch (IOException e) {
                            logger.warning("Erro ao salvar: " + e.getMessage());
                            System.out.println("Erro ao salvar: " + e.getMessage());
                        }
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                logger.log(Level.FINE, "Erro: " + e.getMessage());
                System.out.println("Erro: " + e.getMessage());
            }

        }
    }

    private void criarConta() {
        logger.info("1 Selecionado Criar Conta");
        System.out.print("Número da conta: ");
        String numero = sc.nextLine().trim();
        logger.info("Número da conta digitado: " + numero);
        System.out.print("Titular: ");
        String titular = sc.nextLine().trim();
        logger.info("Titular digitado: " + titular);
        System.out.print("Saldo inicial (ex.: 100.00): ");

        BigDecimal saldo;
        try {
            saldo = new BigDecimal(sc.nextLine().trim());
            if (saldo.compareTo(BigDecimal.ZERO) < 0) {
                logger.warning("Saldo inicial deve ser não negativo.");
                System.out.println("Saldo inicial deve ser não negativo.");
                return;
            }
        } catch (Exception e) {
            logger.log(Level.FINE, "Saldo inválido. Tente novamente.");
            System.out.println("Saldo inválido. Tente novamente.");
            return;
        }

        CrudUtils.criarConta(banco, numero, titular, saldo);
        System.out.println("Conta criada!");
    }

    private void depositar() {
        logger.info("2 Selecionado depositar");
        System.out.print("Conta: ");
        String numero = sc.nextLine().trim();
        logger.info("Número da conta digitado: " + numero);
        System.out.print("Valor: ");
        try {
            BigDecimal v = new BigDecimal(sc.nextLine().trim());
            logger.info("Valor de depósito digitado: " + v.toString());
            CrudUtils.depositar(banco, numero, v, extrato);
            System.out.println("Depósito feito.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            logger.warning("Erro ao tentar depositar: " + e.getMessage());
        }
    }

    private void sacar() {
        logger.info("3 Selecionado Sacar");
        System.out.print("Conta: ");
        String numero = sc.nextLine().trim();
        logger.info("Número da conta digitado para saque: " + numero);
        System.out.print("Valor: ");
        try {
            BigDecimal v = new BigDecimal(sc.nextLine().trim());
            logger.info("Valor de saque digitado: " + v.toString());
            CrudUtils.sacar(banco, numero, v, extrato);
            System.out.println("Saque feito.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            logger.warning("Erro ao tentar sacar: " + e.getMessage());
        }
    }

    private void transferir() {
        logger.info("4 Selecionado transferir");
        System.out.print("De (nº conta): ");
        String de = sc.nextLine().trim();
        logger.info("Conta origem digitada: " + de);
        System.out.print("Para (nº conta): ");
        String para = sc.nextLine().trim();
        logger.info("Conta destino digitada: " + para);
        System.out.print("Valor: ");
        try {
            BigDecimal v = new BigDecimal(sc.nextLine().trim());
            logger.info("Valor para transferência digitado: " + v.toString());
            CrudUtils.transferir(banco, de, para, v, extrato);
            System.out.println("Transferência concluída.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
            logger.warning("Erro ao tentar transferir: " + e.getMessage());
        }
    }
}
