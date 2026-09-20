import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Blood Bank Inventory and Donor Matcher");

        System.out.print("Enter required blood group: ");
        String bloodGroup = sc.nextLine();

        System.out.print("Enter available blood units: ");
        int units = sc.nextInt();

        System.out.println("\nBlood Group: " + bloodGroup);
        System.out.println("Available Units: " + units);

        if (units > 0) {
            System.out.println("Blood is available.");
            System.out.println("Suitable donor can be matched.");
        } else {
            System.out.println("Blood is not available.");
            System.out.println("Donor is required.");
        }

        sc.close();
    }
}
