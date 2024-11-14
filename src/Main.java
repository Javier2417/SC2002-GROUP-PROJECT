import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static CSVUtility patientCSV = new CSVUtility("Patient_List.csv");
    private static CSVUtility staffCSV = new CSVUtility("Staff_List.csv");
    private static PasswordManager patientPasswordManager = new PasswordManager(patientCSV);
    private static PasswordManager staffPasswordManager = new PasswordManager(staffCSV);

    public static void main(String[] args) {
        // Initialize users from CSV files
        UserInitialization userInitialization = new UserInitialization(patientCSV, staffCSV);
        userInitialization.initializeUsers();

        // Main login menu
        displayLoginMenu();
    }

    private static void displayLoginMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Login Menu");
            System.out.println("1. Login");
            System.out.println("2. Terminate");
            System.out.print("Choose an option: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("User ID: ");
                        String userID = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();

                        User user = authenticateUser(userID, password);
                        if (user != null) {
                            displayPasswordChangeMenu(user);
                        } else {
                            System.out.println("Invalid User ID or Password. Please try again.");
                        }
                        break;
                    case 2:
                        System.out.println("Terminating program.");
                        return; // Exit the program
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    private static User authenticateUser(String userID, String password) {
        List<String[]> patientData = patientCSV.readCSVWithoutHeader();
        List<String[]> staffData = staffCSV.readCSVWithoutHeader();

        for (String[] user : patientData) {
            if (user[0].equals(userID)) {
                String storedPassword = user[5];
                try {
                    if (PasswordUtils.validatePassword(password, storedPassword)) {
                        return new Patient(userID, password, user[1]);
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        }

        for (String[] user : staffData) {
            if (user[0].equals(userID)) {
                String storedPassword = user[5];
                try {
                    if (PasswordUtils.validatePassword(password, storedPassword)) {
                        User.Role role = determineUserRole(userID);
                        switch (role) {
                            case ADMINISTRATOR:
                                return new Administrator(userID, password, user[1]);
                            case DOCTOR:
                                return new Doctor(userID, password, user[1]);
                            case PHARMACIST:
                                return new Pharmacist(userID, password, user[1]);
                        }
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static User.Role determineUserRole(String userID) {
        // Logic to determine user role based on userID
        // For demonstration, we'll assume userID starting with 'A' is Administrator, 'D' is Doctor, 'P' is Pharmacist, and 'T' is Patient
        if (userID.startsWith("A")) {
            return User.Role.ADMINISTRATOR;
        } else if (userID.startsWith("D")) {
            return User.Role.DOCTOR;
        } else if (userID.startsWith("P")) {
            return User.Role.PHARMACIST;
        } else {
            return User.Role.PATIENT;
        }
    }

    private static void displayPasswordChangeMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Password Change Menu");
            System.out.println("1. Change Password");
            System.out.println("2. Display Role-Specific Menu");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        PasswordManager passwordManager = user.role == User.Role.PATIENT ? patientPasswordManager : staffPasswordManager;
                        user.changePassword(scanner, passwordManager);
                        break;
                    case 2:
                        user.displayRoleSpecificMenu();
                        break;
                    case 3:
                        System.out.println("Logged out.");
                        return; // Return to the login menu
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }
}
