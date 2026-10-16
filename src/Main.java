
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Donor {
    int id;
    String name;
    String bloodGroup;
    String contact;

    Donor(int id, String name, String bloodGroup, String contact) {
        this.id = id;
        this.name = name;
        this.bloodGroup = bloodGroup.toUpperCase();
        this.contact = contact;
    }

    void display() {
        System.out.println("ID: " + id
                + " | Name: " + name
                + " | Blood Group: " + bloodGroup
                + " | Contact: " + contact);
    }
}

class BloodRequest {
    String patientName;
    String bloodGroup;
    int units;
    String status;

    BloodRequest(String patientName, String bloodGroup, int units,
                 String status) {
        this.patientName = patientName;
        this.bloodGroup = bloodGroup.toUpperCase();
        this.units = units;
        this.status = status;
    }

    void display() {
        System.out.println("Patient: " + patientName
                + " | Group: " + bloodGroup
                + " | Units: " + units
                + " | Status: " + status);
    }
}

public class BloodBankSystem {
    static Scanner sc = new Scanner(System.in);

    static ArrayList<Donor> donors = new ArrayList<>();
    static ArrayList<BloodRequest> requests = new ArrayList<>();

    static HashMap<String, Integer> inventory = new HashMap<>();

    static int nextDonorId = 1;

    static String[] groups = {
        "A+", "A-", "B+", "B-",
        "AB+", "AB-", "O+", "O-"
    };

    static {
        for (String group : groups) {
            inventory.put(group, 0);
        }
    }

    static String readBloodGroup() {
        System.out.print("Enter blood group: ");
        String group = sc.nextLine().trim().toUpperCase();

        if (!inventory.containsKey(group)) {
            System.out.println("Invalid blood group!");
            return null;
        }
        return group;
    }

    static int readPositiveInt(String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println("Enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    static void registerDonor() {
        System.out.print("Enter donor name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        String group = readBloodGroup();
        if (group == null) return;

        System.out.print("Enter contact number: ");
        String contact = sc.nextLine().trim();

        if (contact.isEmpty()) {
            System.out.println("Contact cannot be empty.");
            return;
        }

        Donor donor = new Donor(nextDonorId++, name, group, contact);
        donors.add(donor);

        System.out.println("Donor registered successfully!");
        donor.display();
    }

    static void addBloodStock() {
        String group = readBloodGroup();
        if (group == null) return;

        int units = readPositiveInt("Enter units to add: ");

        inventory.put(group, inventory.get(group) + units);

        System.out.println("Blood stock updated successfully!");
        System.out.println(group + " available units: "
                + inventory.get(group));
    }

    static void viewInventory() {
        System.out.println("\n===== BLOOD INVENTORY =====");
        int total = 0;

        for (String group : groups) {
            int units = inventory.get(group);
            System.out.printf("%-5s : %d units%n", group, units);
            total += units;
        }

        System.out.println("Total available units: " + total);
    }

    static void viewDonors() {
        System.out.println("\n===== DONOR RECORDS =====");

        if (donors.isEmpty()) {
            System.out.println("No donor records found.");
            return;
        }

        for (Donor donor : donors) {
            donor.display();
        }
    }

    static void searchDonor() {
        String group = readBloodGroup();
        if (group == null) return;

        boolean found = false;

        for (Donor donor : donors) {
            if (donor.bloodGroup.equals(group)) {
                donor.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No donors found for " + group);
        }
    }

    static void requestBlood() {
        System.out.print("Enter patient name: ");
        String patient = sc.nextLine().trim();

        if (patient.isEmpty()) {
            System.out.println("Patient name cannot be empty.");
            return;
        }

        String group = readBloodGroup();
        if (group == null) return;

        int units = readPositiveInt("Enter required units: ");

        if (inventory.get(group) >= units) {
            inventory.put(group, inventory.get(group) - units);

            BloodRequest request =
                new BloodRequest(patient, group, units, "Approved");

            requests.add(request);

            System.out.println("Request approved. Units issued: " + units);
            System.out.println("Remaining stock: " + inventory.get(group));
        } else {
            BloodRequest request =
                new BloodRequest(patient, group, units, "Pending - Insufficient stock");

            requests.add(request);

            System.out.println("Insufficient stock. Request marked pending.");
            System.out.println("Available units: " + inventory.get(group));
        }
    }

    static void viewRequests() {
        System.out.println("\n===== BLOOD REQUEST REPORT =====");

        if (requests.isEmpty()) {
            System.out.println("No blood requests found.");
            return;
        }

        for (BloodRequest request : requests) {
            request.display();
        }
    }

    static void lowStockReport() {
        System.out.println("\n===== LOW STOCK REPORT =====");
        boolean low = false;

        for (String group : groups) {
            if (inventory.get(group) < 5) {
                System.out.println(group + " : "
                        + inventory.get(group) + " units");
                low = true;
            }
        }

        if (!low) {
            System.out.println("No low-stock groups.");
        }
    }

    public static void main(String[] args) {
       int choice = 0;

        do {
            System.out.println("\n================================");
            System.out.println(" BLOOD BANK INVENTORY SYSTEM");
            System.out.println("================================");
            System.out.println("1. Register Donor");
            System.out.println("2. Add Blood Stock");
            System.out.println("3. View Blood Inventory");
            System.out.println("4. View All Donors");
            System.out.println("5. Search Donors by Blood Group");
            System.out.println("6. Request Blood");
            System.out.println("7. View Blood Requests");
            System.out.println("8. Low Stock Report");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid menu number.");
                continue;
            }

            switch (choice) {
                case 1:
                    registerDonor();
                    break;
                case 2:
                    addBloodStock();
                    break;
                case 3:
                    viewInventory();
                    break;
                case 4:
                    viewDonors();
                    break;
                case 5:
                    searchDonor();
                    break;
                case 6:
                    requestBlood();
                    break;
                case 7:
                    viewRequests();
                    break;
                case 8:
                    lowStockReport();
                    break;
                case 9:
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 9);

        sc.close();
    }
}
