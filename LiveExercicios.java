

// public class Ecercicio1 {
//     public static void main(String[] args) {
//         System.out.println("Olá, mundo!");
//         System.out.println("Olá, ASH!");
//     }
// }
/**
Olá, mundo!
Olá, ASH!
*/


import java.util.Scanner;
public class LiveExercicios {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite seu nome: ");
        String nome = sc.nextLine();
        System.out.print("Digite sua idade: ");
        String idadeStr = sc.nextLine();
        int idade;

        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException e) { // Catch quando da merda, expetion do python
            System.out.println("Idade inválida. Use apenas números.");
            sc.close();
            return;
        }

        System.out.println("Olá, " + nome + "! Você tem " + idade + " anos.");
        sc.close();
    }
}
/**
Digite seu nome: Ash
Digite sua idade: 25
Olá, Ash! Você tem 25 anos. 
 */
