package com.abm.mainet.common.utility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

/**
 * @author pabitra.raulo
 * @since 10th December 2013
 * @description provide DataSource using JdbcTemplate for spring-jdbc
 */
@Component
public class ApplicationDatasourceLoader implements Serializable {

    private static final long serialVersionUID = 1847851912231756206L;

    public Connection getConnection() throws SQLException {

        Connection conn = null;

        DataSource dataSource = null;

        dataSource = (DataSource) ApplicationContextProvider.getApplicationContext().getBean("defaultDataSource");

        if (dataSource == null) {
        }

        try {
            conn = dataSource.getConnection();
            return conn;
        } catch (final SQLException e) {
            return null;
        } catch (final Exception e1) {
            return null;
        }

    }
}
