

public class Pharmacist extends User {

	public Pharmacist(String userID, String password, String name) {
		super(userID, password, name, User.Role.PHARMACIST);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void displayRoleSpecificMenu() {
		System.out.println("Pharmacist menu");
        System.out.println("1. View Prescriptions");
        System.out.println("2. Manage Inventory");
        System.out.println("3. Logout");

	}

}

