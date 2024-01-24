--liquibase formatted sql
--changeset nilima:V20171130192532__AL_TB_CARE_REQUEST_30112017.SQL
Alter TAble tb_care_request add COLUMN `LAST_DATE_OF_ACTION` datetime;

--liquibase formatted sql
--changeset nilima:V20171130192532__AL_TB_CARE_REQUEST_301120171.SQL
Alter Table tb_workflow_request add COLUMN `LAST_DATE_OF_ACTION` datetime ;
 
 --liquibase formatted sql
--changeset nilima:V20171130192532__AL_TB_CARE_REQUEST_301120172.SQL
Alter Table tb_workflow_request add COLUMN `LAST_DECESION` Varchar (200) ;