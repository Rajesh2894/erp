--liquibase formatted sql
--changeset priya:V20180215121402__AL_role_entitlement_15022018.sql
ALTER TABLE role_entitlement 
ADD PRIMARY KEY (ROLE_ET_ID)  COMMENT '';