--liquibase formatted sql
--changeset Kanchan:V20201029143630__AL_TB_AC_GRNTMST_29102020.sql
alter table TB_AC_GRNTMST add column OPENING_BALANCE_AMT decimal(12,2) NOT NULL;
--liquibase formatted sql
--changeset Kanchan:V20201029143630__AL_TB_AC_GRNTMST_291020201.sql
alter table TB_AC_INVMST add column DEPOSITE_AMT decimal(12,2) NOT NULL;
