package ua.foxminded.schooljdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import ua.foxminded.schooljdbc.models.Student;

public class StudentRowMapper implements RowMapper<Student> {
	@Override
    public Student mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Student(resultSet.getInt("student_id"), 
        					resultSet.getInt("group_id"),
        					resultSet.getString("first_name").trim(),
        					resultSet.getString("last_name").trim());
	}
}

