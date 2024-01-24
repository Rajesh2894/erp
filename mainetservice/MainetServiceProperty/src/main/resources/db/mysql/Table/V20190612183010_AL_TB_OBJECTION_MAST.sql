--liquibase formatted sql
--changeset nilima:V20190612183010_AL_TB_OBJECTION_MAST1.sql
alter table tb_objection_mast change column `APM_APPLICATION_ID` `APM_APPLICATION_ID` bigint(16) DEFAULT NULL COMMENT 'Application Id';
--liquibase formatted sql
--changeset nilima:V20190612183010_AL_TB_OBJECTION_MAST2.sql
alter table tb_dup_bill change column `REF_ID` `REF_ID` varchar(20) DEFAULT NULL;
--liquibase formatted sql
--changeset nilima:V20190612183010_AL_TB_OBJECTION_MAST3.sql
alter table tb_dup_bill add column `BM_NO` varchar(50) DEFAULT NULL AFTER DUP_BILL;
