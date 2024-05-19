package ua.foxminded.schooljdbc.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ua.foxminded.schooljdbc.mappers.CourseRowMapper;
import ua.foxminded.schooljdbc.models.Course;

@Repository
public class CourseDaoImpl implements CourseDao {
	
	public static final String SELECT_ALL = "SELECT * FROM school.courses";

	private JdbcTemplate jdbcTemplate;	

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;     	
    }
 
    @Override
    public List<Course> getAllCourses() {
    	return jdbcTemplate.query( SELECT_ALL, new CourseRowMapper() );
    }
}


