package ua.foxminded.schooljdbc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import ua.foxminded.schooljdbc.mappers.GroupRowMapper;
import ua.foxminded.schooljdbc.mappers.StudentRowMapper;
import ua.foxminded.schooljdbc.models.Group;
import ua.foxminded.schooljdbc.models.Student;


@Repository
@ComponentScan("ua.foxminded.schooljdbc.dao")

public class StudentDaoImpl implements StudentDao {
	
	public static final String GET_ALL_IN_GROUPS = "SELECT groups.group_name, students.group_id\r\n"
											+ "FROM school.students\r\n"
											+ "JOIN school.groups ON students.group_id = groups.group_id;";
	
	public static final String GET_ALL_IN_COURSE = "SELECT students.student_id, students.group_id, students.first_name, students.last_name\r\n"
												+ "FROM school.enrollments\r\n"
												+ "JOIN school.students ON students.student_id = enrollments.student_id\r\n"
												+ "WHERE enrollments.course_id = :course_id";
	
	public static final String INSERT = "INSERT INTO school.students (first_name, last_name) VALUES (:firstName, :lastName)";
	
	public static final String DELETE = "DELETE FROM school.students \r\n"
										+ "WHERE student_id = :id;\r\n"
										+ "DELETE FROM school.enrollments \r\n"
										+ "WHERE student_id = :id;";
	private JdbcTemplate jdbcTemplate;
	private  NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
    public StudentDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate; 
    }

    @Override
    public List<Group> getStudentsInGroups() {
    	return jdbcTemplate.query( GET_ALL_IN_GROUPS, new GroupRowMapper() );
    }
    
	@Override
    public List<Student> getStudentsOnCourse(int courseID){      
        MapSqlParameterSource insertParameter = new MapSqlParameterSource();
        insertParameter.addValue("course_id", courseID);
    	return namedParameterJdbcTemplate.query(GET_ALL_IN_COURSE, insertParameter, new StudentRowMapper());
    }
	
	@Override
    public void updateStudent(String firstName, String lastName){    
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", firstName);
        params.addValue("lastName", lastName);
    	namedParameterJdbcTemplate.update(INSERT, params);
    }
	
	@Override
    public void deleteStudent(int studentID){    
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", studentID);
    	namedParameterJdbcTemplate.update(DELETE, params);
    }
}
