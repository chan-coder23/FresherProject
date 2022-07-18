package biz.aavaz.aavazapplicantfreshjrproject.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import biz.aavaz.aavazapplicantfreshjrproject.dao.UserDao;
import biz.aavaz.aavazapplicantfreshjrproject.model.User;
/**
 * 
 * @author singh
 *
 */
public class UserController {
	private static final UserDao userDao = new UserDao();

	/**
	 * 
	 * @param path
	 * @throws FileNotFoundException
	 */
	public void addUser(String path) throws FileNotFoundException {
		// deserialize the skill file at path using gson getting a skill object
		// skill object should be saved with SkillDAO.save(skill)
		Gson gson = new Gson();
		File file = new File(path);
		file.setReadable(true);
		JsonReader reader = new JsonReader(new FileReader(file));
		User user = gson.fromJson(reader, User.class);
		userDao.save(user);
	}

	public void printAllUsers(String skillName) {
//		userDao.printAll();
		List<User> listOfSkill = userDao.getAllBySkillName(skillName);
		if (listOfSkill != null && !listOfSkill.isEmpty()) {
			listOfSkill.forEach(System.out::println);
		}else {
			System.out.println("No User found with the skill:"+skillName);
		}
	}

	public void printUserById() {

		Scanner input = new Scanner(System.in);

		System.out.println("Enter user id:");
		User user = userDao.get(input.nextInt());

		if (user != null) {
			System.out.println(user);
		} else {
			System.out.println("User not found");
		}
	}
}
