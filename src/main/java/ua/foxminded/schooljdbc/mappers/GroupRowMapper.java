package ua.foxminded.schooljdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import ua.foxminded.schooljdbc.models.Group;

public class GroupRowMapper implements RowMapper<Group> {
	@Override
    public Group mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        return new Group(resultSet.getInt("group_id"), resultSet.getString("group_name").trim());
	}
}

