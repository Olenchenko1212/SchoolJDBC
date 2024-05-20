package ua.foxminded.schooljdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ua.foxminded.school.jdbc.util.ConfigDB;
import ua.foxminded.schooljdbc.dao.CourseDao;
import ua.foxminded.schooljdbc.dao.CourseDaoImpl;
import ua.foxminded.schooljdbc.dao.EnrollmentDao;
import ua.foxminded.schooljdbc.dao.EnrollmentDaoImpl;
import ua.foxminded.schooljdbc.dao.StudentDao;
import ua.foxminded.schooljdbc.dao.StudentDaoImpl;
import ua.foxminded.schooljdbc.models.Group;

@SpringBootApplication
@ComponentScan("ua.foxminded.schooljdbc")
public class SchoolJdbcApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(SchoolJdbcApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class, StudentDaoImpl.class);	
		try (Scanner scanner = new Scanner(System.in)) {
			int choice = 0;

			while (choice != 7) {
				System.out.println("Menu:");
				System.out.println("1 -> Find all groups with less or equal students’ number");
				System.out.println("2 -> Find all students related to the course with the given name");
				System.out.println("3 -> Add a new student");
				System.out.println("4 -> Delete a student by the STUDENT_ID");
				System.out.println("5 -> Add a student to the course (from a list)");
				System.out.println("6 -> Remove the student from one of their courses");
				System.out.println("7 -> Exit");
				System.out.print("Enter your choice: ");

				choice = scanner.nextInt();

				if (choice == 1) {
					System.out.print("Imput students’ number : ");
					int numberStudents = scanner.nextInt();
					findGroups(numberStudents, context);

				} else if (choice == 2) {
					System.out.println("All courses : ");			
					takeAllCourses();
					System.out.print("Select course : ");
					int inputCourse = scanner.nextInt();
					findStudentsOnCourse(inputCourse);

				} else if (choice == 3) {
					System.out.print("Imput a new students’ first name : ");
					String firstName = scanner.next();
					System.out.print("Imput a new students’ last name : ");
					String lastName = scanner.next();
					addStudent(firstName, lastName);

				} else if (choice == 4) {
					System.out.print("Imput the STUDENT_ID for delete student : ");
					int studentID = scanner.nextInt();
					removeStudent(studentID);

				} else if (choice == 5) {
					System.out.println("Select the course from a list : ");
					takeAllCourses();
					System.out.print("Select course : ");
					int inputCourse = scanner.nextInt();
					System.out.print("Input student's ID : ");
					int inputStudentID = scanner.nextInt();
					addStudentToCourse(inputStudentID, inputCourse);

				} else if (choice == 6) {
					System.out.print("Imput students’ ID to remove the student from one of their courses : ");
					int inputStudentID = scanner.nextInt();
					removeFromCourse(inputStudentID);

				} else if (choice == 7) {
					System.out.println("Exit from app!");
				} else {
					System.out.println("Invalid choice. Please try again.");
				}
				System.out.println();
			}
		}
		context.close();
	}
	
	@SuppressWarnings("resource")
	public static void findGroups(int studentsNumber, AnnotationConfigApplicationContext context) {
		
		if (studentsNumber < 1) {
			throw new IllegalArgumentException("Number of imput students must be more then 1");
		}
		
		StudentDao studentDAO = context.getBean(StudentDaoImpl.class);
		Map<String, Integer> studentsInGroup = new HashMap<>(calcStudentsInGroup(studentDAO.getStudentsInGroups()));
		System.out.format("All groups with less or equal %d students’ number:%n", studentsNumber);
		studentsInGroup.forEach((key, value) -> {
			if (value <= studentsNumber) {
				System.out.print(key + "   ");
			}
		});
	}
	
	public static Map<String, Integer> calcStudentsInGroup(List<Group> studentsInGroups) {

		if (studentsInGroups.isEmpty()) {
			throw new IllegalArgumentException("List<Group> studentsInGroups is empty");
		}
		Map<String, Integer> studentsInGroup = new HashMap<>();
		try {
			for (int i = 0; i < studentsInGroups.size(); i++) {
				Group group = studentsInGroups.get(i);
				if (studentsInGroup.containsKey(group.getGroupName())) {
					studentsInGroup.put(group.getGroupName(), studentsInGroup.get(group.getGroupName()) + 1);
				} else {
					studentsInGroup.put(group.getGroupName(), 1);
				}
			}
			return studentsInGroup;

		} catch (Exception e) {
			throw new IllegalArgumentException("List<Group> studentsInGroups is empty");
		}
	}
	
	public static void takeAllCourses() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class);
		CourseDao courseDAO = context.getBean(CourseDaoImpl.class);
		courseDAO.getAllCourses().stream()
			.forEach(course -> System.out.println(course.getCourseId() + "->" + course.getCourseName()));
		context.close();
	}
	
	public static void findStudentsOnCourse(int courseID) {
		
		if (courseID >= 1 || courseID <= 11) {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class);
			StudentDao studentDao = context.getBean(StudentDaoImpl.class);
			studentDao.getStudentsOnCourse(courseID).stream()
				.forEach(student -> System.out.println(student.getFirstName() + " " + student.getLastName()));
			context.close();
		} else throw new IllegalArgumentException("Course ID must be in range from 1 to 10");
	}

	public static void addStudent(String firstName, String lastName) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class);
		StudentDao studentDao = context.getBean(StudentDaoImpl.class);
	    studentDao.updateStudent(firstName, lastName);
		context.close();
	}
	
	public static void removeStudent(int studentID) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class);
		StudentDao studentDao = context.getBean(StudentDaoImpl.class);
		studentDao.deleteStudent(studentID);
		context.close();
	}
	
	public static void addStudentToCourse(int studentID, int courseID) {
		if(isNotStudentOnCourse(studentID, courseID)) {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class);
			EnrollmentDao enrollmentDao = context.getBean(EnrollmentDaoImpl.class);
			enrollmentDao.saveToCourse(studentID, courseID);
			context.close();
		} else {
			System.out.println("Student already enrolled earlier!");
		}
	}
	
	public static boolean isNotStudentOnCourse(int studentID, int courseID) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class);
		EnrollmentDao enrollmentDao = context.getBean(EnrollmentDaoImpl.class);
		if(enrollmentDao.getStudentCourse(studentID,courseID) == 0) {
			context.close();
			return true;
		} else {
			context.close();
			return false;
		}	
	}
	
	public static void removeFromCourse(int inputStudentID) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigDB.class);
		EnrollmentDao enrollmentDao = context.getBean(EnrollmentDaoImpl.class);
		enrollmentDao.getStudentCourses(inputStudentID).stream()
			.forEach(course -> System.out.println(course.getCourseId() + "-> " + course.getCourseName()));
		System.out.print("Select course by ID to delete: ");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int courseID = scanner.nextInt();
		enrollmentDao.deleteFromCourse(inputStudentID, courseID);
		context.close();
	}

}
