import org.mariadb.jdbc.MariaDbDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableClearer {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String username;
    private final String password;
    private final String dataSourceUrl;

    private DataSource dataSource;

    @Autowired
    TableClearer(
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.url}") String dataSourceUrl) {
        this.username = username;
        this.password = password;
        this.dataSourceUrl = dataSourceUrl;
        this.dataSource = createDataSource();
    }

    private Connection connection;

    private DataSource createDataSource() {
        MariaDbDataSource dataSource = new MariaDbDataSource(dataSourceUrl);
        setUsernameAndPw(dataSource);
        try {
            dataSource.getConnection();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return dataSource;
    }

    private void setUsernameAndPw(MariaDbDataSource dataSource) {
        try {
            dataSource.setUserName(username);
            dataSource.setPassword(password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearTables() {
        logger.debug("Clearing tables");
        try {
            try {
                connection = dataSource.getConnection();
                tryToClearTables();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void tryToClearTables() throws SQLException {
        List<String> tableNames = getTableNames();
        clear(tableNames);
        resetSequences(tableNames);
    }

    private List<String> getTableNames() throws SQLException {
        List<String> tableNames = new ArrayList<>();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(
                connection.getCatalog(), null, null, new String[]{"TABLE"});

        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            if (!"flyway_schema_history".equalsIgnoreCase(tableName)) {
                tableNames.add(tableName);
            }
        }

        return tableNames;
    }

    private void clear(List<String> tableNames) throws SQLException {
        Statement statement = buildSqlStatement(tableNames);

        logger.debug("Executing SQL");
        statement.executeBatch();
    }

    private void resetSequences(List<String> tableNames) throws SQLException {
        Statement statement = connection.createStatement();

        tableNames.forEach(tableName -> {
            try {
                statement.addBatch("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1;");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        statement.executeBatch();
    }

    private Statement buildSqlStatement(List<String> tableNames) throws SQLException {
        Statement statement = connection.createStatement();

        statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 0"));
        addDeleteSatements(tableNames, statement);
        statement.addBatch(sql("SET FOREIGN_KEY_CHECKS = 1"));

        return statement;
    }

    private void addDeleteSatements(List<String> tableNames, Statement statement) {
        tableNames.forEach(tableName -> {
            try {
                statement.addBatch(sql("DELETE FROM " + tableName));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String sql(String sql) {
        logger.debug("Adding SQL: {}", sql);
        return sql;
    }
}
