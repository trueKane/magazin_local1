import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Magazin {
    private static final String FILE_PATH = "magazin.csv"; // Calea către fișierul CSV
    private static final String FILE_PATH_CANTITATE = "produse_cantitate.csv"; // Calea către fișierul CSV pentru produse și cantitate
    private static final String FILE_PATH_PRET = "produse_pret.csv"; // Calea către fișierul CSV pentru produse și preț

    // Clasa pentru reprezentarea produsului
    private static class Produs {
        String nume;
        int pret;
        int cantitate;

        public Produs(String nume, int pret, int cantitate) {
            this.nume = nume;
            this.pret = pret;
            this.cantitate = cantitate;
        }

        @Override
        public String toString() {
            return "Nume: " + nume + ", Preț: " + pret + ", Cantitate: " + cantitate;
        }
    }

    // Metoda pentru a citi produsele din fișierul CSV
    private static List<Produs> citesteProduse(String filePath) {
        List<Produs> produse = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linie;

            while ((linie = br.readLine()) != null) {
                String[] informatii = linie.split(",");

                if (informatii.length == 3) {
                    String nume = informatii[0];
                    int pret = Integer.parseInt(informatii[1]);
                    int cantitate = Integer.parseInt(informatii[2]);

                    Produs produs = new Produs(nume, pret, cantitate);
                    produse.add(produs);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return produse;
    }

    // Metoda pentru a afișa toate produsele
    private static void afiseazaProduse(List<Produs> produse) {
        System.out.println("Produsele disponibile:");

        for (Produs produs : produse) {
            System.out.println(produs);
        }
    }

    // Metoda pentru a adăuga un produs nou
    private static void adaugaProdus(List<Produs> produse, Scanner scanner) {
        System.out.print("Introduceti numele produsului: ");
        String nume = scanner.nextLine();

        System.out.print("Introduceti pretul produsului: ");
        int pret = Integer.parseInt(scanner.nextLine());

        System.out.print("Introduceti cantitatea produsului: ");
        int cantitate = Integer.parseInt(scanner.nextLine());

        Produs produs = new Produs(nume, pret, cantitate);
        produse.add(produs);

        System.out.println("Produsul a fost adăugat cu succes!");
    }

    // Metoda pentru a șterge un produs existent
    private static void stergeProdus(List<Produs> produse, Scanner scanner) {
        System.out.print("Introduceti numele produsului pe care doriti sa-l stergeti: ");
        String nume = scanner.nextLine();

        Produs produsGasit = null;

        for (Produs produs : produse) {
            if (produs.nume.equalsIgnoreCase(nume)) {
                produsGasit = produs;
                break;
            }
        }

        if (produsGasit != null) {
            produse.remove(produsGasit);
            System.out.println("Produsul a fost șters cu succes!");
        } else {
            System.out.println("Produsul nu a fost găsit!");
        }
    }

    // Metoda pentru a salva produsele în fișierul CSV
    private static void salveazaProduse(List<Produs> produse, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Produs produs : produse) {
                String linie = produs.nume + "," + produs.pret + "," + produs.cantitate + "\n";
                writer.write(linie);
            }

            System.out.println("Produsele au fost salvate cu succes în fișierul " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Produs> produse = citesteProduse(FILE_PATH);
        List<Produs> produseCantitate = citesteProduse(FILE_PATH_CANTITATE);
        List<Produs> produsePret = citesteProduse(FILE_PATH_PRET);

        boolean ruleaza = true;

        while (ruleaza) {
            System.out.println("\n--- MENIU ---");
            System.out.println("1. Afiseaza produsele");
            System.out.println("2. Adauga produs");
            System.out.println("3. Sterge produs");
            System.out.println("4. Iesire");

            System.out.print("Alegeți o opțiune: ");
            int optiune = Integer.parseInt(scanner.nextLine());

            switch (optiune) {
                case 1:
                    System.out.println("\n--- Produse ---");
                    afiseazaProduse(produse);
                    System.out.println("\n--- Produse și Cantitate ---");
                    afiseazaProduse(produseCantitate);
                    System.out.println("\n--- Produse și Preț ---");
                    afiseazaProduse(produsePret);
                    break;
                case 2:
                    adaugaProdus(produse, scanner);
                    adaugaProdus(produseCantitate, scanner);
                    adaugaProdus(produsePret, scanner);
                    break;
                case 3:
                    stergeProdus(produse, scanner);
                    stergeProdus(produseCantitate, scanner);
                    stergeProdus(produsePret, scanner);
                    break;
                case 4:
                    ruleaza = false;
                    salveazaProduse(produse, FILE_PATH);
                    salveazaProduse(produseCantitate, FILE_PATH_CANTITATE);
                    salveazaProduse(produsePret, FILE_PATH_PRET);
                    break;
                default:
                    System.out.println("Opțiune invalidă. Reîncercați!");
            }
        }

        scanner.close();
    }
}
