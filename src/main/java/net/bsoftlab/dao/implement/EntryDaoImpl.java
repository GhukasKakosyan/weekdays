package net.bsoftlab.dao.implement;

import net.bsoftlab.dao.EntryDao;
import net.bsoftlab.dao.exception.DataAccessException;
import net.bsoftlab.model.Entry;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

import java.util.ArrayList;
import java.util.List;

public class EntryDaoImpl implements EntryDao {

    private DataSource dataSource;

    public EntryDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void closeConnection (Connection connection) {
        try {
            if(connection != null) connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    private void closePreparedStatement(PreparedStatement preparedStatement) {
        try {
            if(preparedStatement != null) preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    private void closeResultSet(ResultSet resultSet) {
        try {
            if(resultSet != null) resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    private void rollbackConnection(Connection connection, Savepoint savepoint)
            throws DataAccessException {
        try {
            if(connection != null) connection.rollback(savepoint);
        } catch (SQLException rollbackSqlException) {
            throw new DataAccessException(rollbackSqlException.getMessage());
        }
    }

    @Override
    public List<Entry> getEntryList() throws DataAccessException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Savepoint savepoint = null;

        List<Entry> entryList = null;

        try {
            String entryListSelectStatementSql =
                    "SELECT workdays.Weekday, " +
                            "workdays.Day, " +
                            "COUNT(*) AS Quanity " +
                            "FROM workdays " +
                            "GROUP BY workdays.Weekday, workdays.Day " +
                            "ORDER BY 3";

            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            preparedStatement = connection.prepareStatement(entryListSelectStatementSql);
            resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                entryList = new ArrayList<>();
                while (resultSet.next()) {
                    Entry entry = new Entry();
                    entry.setWeekday(resultSet.getInt(1));
                    entry.setDay(resultSet.getInt(2));
                    entry.setQuantity(resultSet.getLong(3));
                    entryList.add(entry);
                }
                if (entryList.isEmpty()) entryList = null;
            }

        } catch (SQLException sqlException) {
            this.rollbackConnection(connection, savepoint);
            throw new DataAccessException(sqlException.getMessage());
        } finally {
            this.closeResultSet(resultSet);
            this.closePreparedStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return entryList;
    }
}
