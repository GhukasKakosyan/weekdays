package net.bsoftlab.dao.implement;

import net.bsoftlab.dao.WorkdayDao;
import net.bsoftlab.dao.exception.DataAccessException;
import net.bsoftlab.model.Workday;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;

import javax.sql.DataSource;

import java.util.List;

public class WorkdayDaoImpl implements WorkdayDao {

    private DataSource dataSource;

    public WorkdayDaoImpl(DataSource dataSource) {
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
    private void rollbackConnection(Connection connection, Savepoint savepoint)
            throws DataAccessException {
        try {
            if(connection != null) connection.rollback(savepoint);
        } catch (SQLException rollbackSqlException) {
            throw new DataAccessException(rollbackSqlException.getMessage());
        }
    }

    @Override
    public void insertWorkdays(List<Workday> workdayList) throws DataAccessException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Savepoint savepoint = null;

        try {
            String workdayInsertStatementSql =
                    "INSERT INTO daysholder.Workdays " +
                            "(Weekday, Day, Month, Year) VALUES (?, ?, ?, ?)";

            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            savepoint = connection.setSavepoint();
            preparedStatement = connection.prepareStatement(workdayInsertStatementSql);

            for (int index = 0; index < workdayList.size(); index++) {
                Workday workday = workdayList.get(index);
                preparedStatement.setInt(1, workday.getWeekday());
                preparedStatement.setInt(2, workday.getDay());
                preparedStatement.setInt(3, workday.getMonth());
                preparedStatement.setInt(4, workday.getYear());
                preparedStatement.addBatch();
                if (index % 1000 == 0 || index == workdayList.size() - 1) {
                    preparedStatement.executeBatch();
                }
            }
            connection.commit();

        } catch (SQLException sqlException) {
            this.rollbackConnection(connection, savepoint);
            throw new DataAccessException(sqlException.getMessage());
        } finally {
            this.closePreparedStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}
