--liquibase formatted sql
--changeset Kanchan:V20211014115746__AL_tb_as_assesment_mast_14102021.sql
ALTER TABLE tb_as_assesment_mast  ADD COLUMN PM_SERVICE_STATUS bigint(20) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20211014115746__AL_tb_as_assesment_mast_141020211.sql
ALTER TABLE tb_as_assesment_mast  ADD COLUMN BILL_MET_CNG_FLAG VARCHAR(10) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20211014115746__AL_tb_as_assesment_mast_141020212.sql
ALTER TABLE TB_AS_ASSESMENT_MAST_HIST  ADD COLUMN SERVICE_STATUS bigint(20) NULL default NULL;
--liquibase formatted sql
--changeset Kanchan:V20211014115746__AL_tb_as_assesment_mast_141020213.sql
ALTER TABLE TB_AS_ASSESMENT_MAST_HIST  ADD COLUMN BILL_MET_CNG_FLAG VARCHAR(10) NULL default NULL;



