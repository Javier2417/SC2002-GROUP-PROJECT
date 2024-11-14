
public class Patient extends User {

	public Patient(String userID, String password, String name) {
		super(userID, password, name, User.Role.PATIENT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void displayRoleSpecificMenu() {
		System.out.println("Patient menu");
        System.out.println("1. View Medical Records");
        System.out.println("2. Book Appointment");
        System.out.println("3. Logout");

	}

}
