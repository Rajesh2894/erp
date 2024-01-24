package com.abm.mainet.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationContextProvider;

@Repository
public class SupplementaryPayBillEntryDAOImpl implements ISupplementaryPayBillEntryDAO {

	private static final Logger LOGGER = Logger.getLogger(SupplementaryPayBillEntryDAOImpl.class);

	
	@Override
	public Long checkIsValidEmployee(String empId, String ulbCode) {
		Long empExists = null;
		DataSource dataSource = null;
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			dataSource = (DataSource) ApplicationContextProvider.getApplicationContext().getBean("hrmsServiceMySqlDS");
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("select count(*) from tblemployeemaster where employee_id =? and ulb_code =?");
			statement.setString(1, empId);
			statement.setString(2, ulbCode);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				empExists = (Long) rs.getObject(1);
			}
		} catch (Exception ex) {
			LOGGER.error("Exception while validating Employee " + ex.getMessage());
		} finally {
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
			LOGGER.info("CallableStatement and Connection closed...");
		}
		return empExists;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Object> getEmployeeData(String empId, String payMonth, String payYear, String ulbCode, String suppType) {

		final List<Object> outParamList = new ArrayList<>();
		DataSource dataSource = null;
		Connection connection = null;
		CallableStatement callableStatement = null;
		try {
			dataSource = (DataSource) ApplicationContextProvider.getApplicationContext().getBean("hrmsServiceMySqlDS");
			connection = dataSource.getConnection();
			LOGGER.info("Connection Established With HRMS Schema ...");

			callableStatement = connection.prepareCall("{call Get_Supplementary_Employee_Data(?,?,?,?,?)}");
			callableStatement.setString(1, empId);
			callableStatement.setString(2, payMonth);
			callableStatement.setString(3, payYear);
			callableStatement.setString(4, ulbCode);
			callableStatement.registerOutParameter(5, Types.VARCHAR);
			callableStatement.execute();
			LOGGER.info("Procedure Executed Successfully...");

			String employeeDetails = callableStatement.getString(5);
			LOGGER.info("Procedure OP:  " + employeeDetails);
			if (null != employeeDetails && !employeeDetails.isEmpty()) {
				final String array[] = employeeDetails.split(MainetConstants.operator.COMMA);
				for (final String string : array)
					outParamList.add(string);
			}

		} catch (final Exception ex) {
			LOGGER.error("Exception while calling the Procedure " + ex.getMessage());
		} finally {
			DbUtils.closeQuietly(callableStatement);
			DbUtils.closeQuietly(connection);
			LOGGER.info("CallableStatement and Connection closed...");
		}
		return outParamList;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<Object> getEmployeePayDetails(final Object[] ipValues, final int[] sqlTypes) {
		final int inCount = ipValues.length;
        final List<Object> outParamList = new ArrayList<>();
        final StringBuilder procedureString = new StringBuilder("call Process_Supplementary_Salary_Bill(");

        for (int i = 0; i < (ipValues.length + sqlTypes.length); ++i) {
            if (i > 0)
                procedureString.append(",");
            procedureString.append("?");
        }
        procedureString.append(")");

		DataSource dataSource = null;
		Connection connection = null;
		CallableStatement callableStatement = null;
		try {
			dataSource = (DataSource) ApplicationContextProvider.getApplicationContext().getBean("hrmsServiceMySqlDS");
			connection = dataSource.getConnection();
			LOGGER.info("Connection Established With HRMS Schema ...");

            callableStatement = connection.prepareCall(procedureString.toString());
            for (int i = 0; i < sqlTypes.length; i++) {
                final int j = inCount + (i + 1);
                callableStatement.registerOutParameter(j, sqlTypes[i]);
            }
            for (int i = 0; i < ipValues.length; i++) {
                callableStatement.setObject((i + 1), ipValues[i]);
            }
            callableStatement.execute();
            LOGGER.info("Procedure Executed Successfully...");
            for (int i = (inCount + 1); i <= (inCount + sqlTypes.length); i++) {
                final Object object = callableStatement.getObject(i);
                outParamList.add(object);
            }
            LOGGER.info("Procedure OP:  " + outParamList.toString());
        } catch (final SQLException ex) {
        	LOGGER.error("Exception while calling the Procedure " + ex.getMessage());
		} finally {
			DbUtils.closeQuietly(callableStatement);
			DbUtils.closeQuietly(connection);
			LOGGER.info("CallableStatement and Connection closed...");
		}
        return outParamList;
	}

}
