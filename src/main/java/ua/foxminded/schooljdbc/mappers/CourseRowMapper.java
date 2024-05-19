package ua.foxminded.schooljdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.schooljdbc.models.Course;

public class CourseRowMapper implements RowMapper<Course> {
	
	@Override
    public Course mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Course(resultSet.getInt("course_id"), resultSet.getString("course_name").trim(), resultSet.getString("course_description").trim());
	}
}

