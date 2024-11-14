
public class Doctor extends User {

	public Doctor(String userID, String password, String name) {
		super(userID, password, name, User.Role.DOCTOR);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void displayRoleSpecificMenu() {
		System.out.println("Doctor menu");
        System.out.println("1. View Patient Records");
        System.out.println("2. Prescribe Medication");
        System.out.println("3. Logout");

	}

}
