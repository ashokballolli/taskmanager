package it.fds.taskmanager;

import it.fds.dto.ListDto;
import utils.ReadConfig;

import java.sql.*;
import java.util.*;

public class DataSource {

    private final String url = ReadConfig.getProperty("db_url");
    private final String user = ReadConfig.getProperty("db_username");
    private final String password = ReadConfig.getProperty("db_password");
    private final String sql_getAllRecordsExceptPostponed = "SELECT *, CAST( substring(CAST (BYTEA (task.uuid) AS text) from 3) AS uuid) as casted_uuid from task where status <> 'POSTPONED'";

    public Connection connection() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        }
        return con;
    }

    public List<ListDto> getAllRecordsExceptPostponed() {
        List<ListDto> list = new ArrayList<ListDto>();
        ResultSet resultSet = null;
        Statement statement = null;
        Connection connection = connection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql_getAllRecordsExceptPostponed);
            while (resultSet.next()) {
                ListDto lDto = new ListDto();
                lDto.setUuid(resultSet.getString("casted_uuid"));
                lDto.setCreatedat(resultSet.getTimestamp("createdat"));
                lDto.setDescription(resultSet.getString("description"));
                lDto.setDuedate(resultSet.getTimestamp("duedate"));
                lDto.setPostponedat(resultSet.getTimestamp("postponedat"));
                lDto.setPostponedtime(resultSet.getString("postponedtime"));
                lDto.setPriority(resultSet.getString("priority"));
                lDto.setResolvedat(resultSet.getTimestamp("resolvedat"));
                lDto.setStatus(resultSet.getString("status"));
                lDto.setTitle(resultSet.getString("title"));
                lDto.setUpdatedat(resultSet.getTimestamp("updatedat"));
                list.add(lDto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
        } finally {
            try { resultSet.close(); } catch (Exception e) { e.printStackTrace(); }
            try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return list;
    }
}