package ua.foxminded.schooljdbc.dao;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.schooljdbc.mappers.CourseRowMapper;
import ua.foxminded.schooljdbc.models.Course;

@Repository
public class EnrollmentDaoImpl implements EnrollmentDao {
	
	public static final String INSERT_COURSE = "INSERT INTO school.enrollments (student_id, course_id) VALUES (:student_id, :course_id)";

	public static final String SELECT_STUDENT_ON_COURSE = "SELECT COUNT(*) FROM school.enrollments\r\n"
														+ "WHERE student_id = :student_id AND course_id = :course_id";
	
	public static final String SELECT_STUDENT_COURSES = "SELECT courses.course_id, courses.course_name, courses.course_description\r\n"
														+ "FROM school.courses\r\n"
														+ "JOIN school.enrollments ON enrollments.course_id = courses.course_id\r\n"
														+ "WHERE student_id = :student_id;";
	
	public static final String DELETE = "DELETE FROM school.enrollments\r\n"
										+ "WHERE student_id = :student_id AND course_id = :course_id";
										
	
	private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public EnrollmentDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
	
    @Override
	public List<Course> getStudentCourses(int studentID){
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("student_id", studentID);
		return namedParameterJdbcTemplate.query(SELECT_STUDENT_COURSES, namedParameters, new CourseRowMapper());
	}
    
	@Override
	public void saveToCourse(int studentID, int courseID) {
    	namedParameterJdbcTemplate.update(INSERT_COURSE, setNamedParameters(studentID,courseID));
	}
	
	@Override
	public int getStudentCourse(int studentID, int courseID) {
        return namedParameterJdbcTemplate.queryForObject(
        		SELECT_STUDENT_ON_COURSE, setNamedParameters(studentID,courseID), Integer.class);
	}
	
	@Override
	public void deleteFromCourse(int studentID, int courseID) {
    	namedParameterJdbcTemplate.update(DELETE, setNamedParameters(studentID,courseID));
	}
	
	public static MapSqlParameterSource setNamedParameters(int studentID, int courseID) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("student_id", studentID);
        namedParameters.addValue("course_id", courseID);
		return namedParameters;
	}
}

