package com.abm.mainet.common.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationContextProvider;

/**
 * @author pabitra.raulo
 * @since 10th December 2013
 * @description provide DataSource using JdbcTemplate for spring-jdbc
 */
@Component
public class ApplicationDatasourceLoader implements Serializable {

    private static final long serialVersionUID = 1847851912231756206L;
    private static final Logger LOG = Logger.getLogger(ApplicationDatasourceLoader.class);

    public Connection getConnection() throws SQLException {

        Connection conn = null;

        DataSource dataSource = null;

        dataSource = (DataSource) ApplicationContextProvider.getApplicationContext().getBean("dataSource");

        if (dataSource == null) {
            new ApplicationContextProvider();
            dataSource = (DataSource) ApplicationContextProvider.getApplicationContext().getBean("dataSource");
        }

        try {
            conn = dataSource.getConnection();
            return conn;
        } catch (final SQLException e) {
            LOG.error(MainetConstants.ERROR_OCCURED, e);
            return null;
        } catch (final Exception e1) {
            LOG.error(MainetConstants.ERROR_OCCURED, e1);
            return null;
        }

    }
}
