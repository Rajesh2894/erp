package com.abm.mainet.common.utility;

import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.type.StringType;

public class ABMOracleDialect extends Oracle10gDialect {

    public ABMOracleDialect() {
        super();
        // Register mappings
        registerHibernateType(Types.NVARCHAR, StringType.INSTANCE.getName());
    }

}