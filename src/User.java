import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

public abstract class User {
    public enum Role { PATIENT, DOCTOR, PHARMACIST, ADMINISTRATOR }
    protected String userID;
    protected String hashedPassword;
    protected String salt;
    protected String name;
    protected Role role;

    public User(String userID, String password, String name, Role role) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        try {
            this.salt = Base64.getEncoder().encodeToString(PasswordUtils.generateSalt());
            this.hashedPassword = PasswordUtils.hashPassword(password, Base64.getDecoder().decode(this.salt));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error initializing user password", e);
        }
    }

    public boolean login(String userID, String password) {
        try {
            if (this.userID.equals(userID) && PasswordUtils.validatePassword(password, this.hashedPassword + ":" + this.salt)) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Invalid credentials. Login failed.");
                return false;
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error validating user password", e);
        }
    }

    public void logout() {
        System.out.println("Logging out user: " + userID);
        
    }

    public void changePassword(Scanner scanner, PasswordManager passwordManager) {
        try {
            System.out.println("Enter your current password:");
            String currentPassword = scanner.nextLine();

            if (!PasswordUtils.validatePassword(currentPassword, this.hashedPassword + ":" + this.salt)) {
                System.out.println("Incorrect password. Please try again.");
                return;
            }

            System.out.println("Enter your new password:");
            String newPassword1 = scanner.nextLine();
            System.out.println("Confirm your new password:");
            String newPassword2 = scanner.nextLine();

            if (!newPassword1.equals(newPassword2)) {
                System.out.println("Passwords do not match. Password change failed.");
                return;
            }

            passwordManager.changePassword(this, newPassword1);
            System.out.println("Password changed successfully.");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error changing user password", e);
        }
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserID() {
        return userID;
    }

    public abstract void displayRoleSpecificMenu();
}
