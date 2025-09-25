package project.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;



// Aqui temos o nosso front kkkkk a Classe principal do mini banco, usando o CrudUtils que e tudo referente a CRUD ta la...
public class MiniBanco {

    private final Banco banco = new Banco();
    private final Scanner sc = new Scanner(System.in);
    private final Path arquivo = Path.of("project/data", "contas.csv");

    public void executar() {
        try {
            banco.carregar(arquivo);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível carregar contas: " + e.getMessage());
        }

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
                    case "5" -> CrudUtils.listarContas(banco).forEach(System.out::println);
                    case "6" -> { // Aqui quis demonstar dados na pasta data entao movi o csv para la criando e garantindo a existencia
                        try {
                            if (arquivo.getParent() != null) {
                                Files.createDirectories(arquivo.getParent());  // garante que pasta exista, cria se não existir
                            }
                            banco.salvar(arquivo);
                            System.out.println("Salvo em " + arquivo.toAbsolutePath());
                            sc.close();
                            return;
                        } catch (IOException e) {
                            System.out.println("Erro ao salvar: " + e.getMessage());
                        }
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

        }
    }

    private void criarConta() {
        System.out.print("Número da conta: ");
        String numero = sc.nextLine().trim();
        System.out.print("Titular: ");
        String titular = sc.nextLine().trim();
        System.out.print("Saldo inicial (ex.: 100.00): ");

        BigDecimal saldo;
        try {
            saldo = new BigDecimal(sc.nextLine().trim());
            if (saldo.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Saldo inicial deve ser não negativo.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Saldo inválido. Tente novamente.");
            return;
        }

        CrudUtils.criarConta(banco, numero, titular, saldo);
        System.out.println("Conta criada!");
    }

    private void depositar() {
        System.out.print("Conta: ");
        String numero = sc.nextLine().trim();
        System.out.print("Valor: ");
        try {
            BigDecimal v = new BigDecimal(sc.nextLine().trim());
            CrudUtils.depositar(banco, numero, v);
            System.out.println("Depósito feito.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void sacar() {
        System.out.print("Conta: ");
        String numero = sc.nextLine().trim();
        System.out.print("Valor: ");
        try {
            BigDecimal v = new BigDecimal(sc.nextLine().trim());
            CrudUtils.sacar(banco, numero, v);
            System.out.println("Saque feito.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void transferir() {
        System.out.print("De (nº conta): ");
        String de = sc.nextLine().trim();
        System.out.print("Para (nº conta): ");
        String para = sc.nextLine().trim();
        System.out.print("Valor: ");
        try {
            BigDecimal v = new BigDecimal(sc.nextLine().trim());
            CrudUtils.transferir(banco, de, para, v);
            System.out.println("Transferência concluída.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
