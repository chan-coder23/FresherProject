package biz.aavaz.aavazapplicantfreshjrproject.view;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import biz.aavaz.aavazapplicantfreshjrproject.controller.SkillController;
import biz.aavaz.aavazapplicantfreshjrproject.controller.UserController;

public class MainMenu {

	

	public static void main(String[] args) throws FileNotFoundException {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);
		boolean isContinue = true;

		do {
			paintMenu();

			switch (processInput()) {

			case 1:
				// add skill
				addSkill();
				break;
			case 2:
				// print all skills
				printSkills();
				break;
			case 3:
				// add a user
				addUser();
				break;
			case 4:
				// print all users by skill name
				printUsers(new Scanner(System.in).next());
//				System.out.println("!!!!! To be implemented by candidate");
				break;
			case 5:// exit
				System.out.println("Good bye");
				System.exit(0);
				isContinue = false;
				break;
			default:
				System.out.println("Invalid input. Please try again.");
				break;
			}

		} while (isContinue);
	}

	public static void paintMenu() {
		System.out.println("=========================");
		System.out.println("1. Add a Skill by reading a JSON file");
		System.out.println("2. Print all skills in database");
		System.out.println("3. Add a User by reading a json file");
		System.out.println("4. Get all Users by skill name");
		System.out.println("5. Exit");
	}

	public static int processInput() {

		Scanner input = new Scanner(System.in);
		return input.nextInt();
	}

	private static void addSkill() {

		String defaultSkillsFolder = "src/main/resources/skills/";
		String filePath = "";
		SkillController skillController = new SkillController();

		System.out.println("Default skills folder -> [" + defaultSkillsFolder + " ]");
		System.out.println("1. Use default folder");
		System.out.println("2. Specify full path");

		Scanner userScanner = new Scanner(System.in);

		if (userScanner.nextInt() == 2) {
			System.out.println("Enter full path including filename & extention:");
			filePath = userScanner.next();
		} else {
			System.out.println("Enter filename with extention ( eq. english.json) :");
			filePath = defaultSkillsFolder + userScanner.next();
		}

		try {
			skillController.addSkill(filePath);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("There was an error while processing");
		}
	}

	private static void addUser() {

		String defaultUsersFolder = "src/main/resources/users/";
		String filePath = "";
		UserController userController = new UserController();

		System.out.println("Default users folder -> [" + defaultUsersFolder + " ]");
		System.out.println("1. Use default folder");
		System.out.println("2. Specify full path");

		Scanner userScanner = new Scanner(System.in);

		if (userScanner.nextInt() == 2) {
			System.out.println("Enter full path including filename & extention:");
			filePath = userScanner.next();
		} else {
			System.out.println("Enter filename with extention ( eq. aman.json) :");
			filePath = defaultUsersFolder + userScanner.next();
		}

		try {
			userController.addUser(filePath);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
			System.out.println("There was an error while processing");
		}
	}

	private static void printSkills() {
		SkillController skillController = new SkillController();
		skillController.printAllSkills();
	}

	private static void printUsers(String skillName) {
		UserController userController = new UserController();
		userController.printAllUsers(skillName);
	}
}
