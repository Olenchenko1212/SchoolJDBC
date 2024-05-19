package ua.foxminded.schooljdbc.dao;

import java.util.List;

import ua.foxminded.schooljdbc.models.Course;

public interface EnrollmentDao {
	void saveToCourse(int studentID, int courseID);
	int getStudentCourse(int studentID, int courseID);
	List<Course> getStudentCourses(int studentID);
	void deleteFromCourse(int studentID, int courseID);
}