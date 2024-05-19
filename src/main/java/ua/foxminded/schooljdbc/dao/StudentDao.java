package ua.foxminded.schooljdbc.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import ua.foxminded.schooljdbc.models.Group;
import ua.foxminded.schooljdbc.models.Student;


public interface StudentDao {
	List<Group>   getStudentsInGroups();
	List<Student> getStudentsOnCourse(int courseID);
	void updateStudent(String firstName, String lastName);
	void deleteStudent(int studentID);
}

