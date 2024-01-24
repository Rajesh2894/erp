--liquibase formatted sql
--changeset Kanchan:V20211006184512__AL_TB_CMT_COUNCIL_MEM_MAST_06102021.sql
alter table TB_CMT_COUNCIL_MEM_MAST modify column COU_EDU_ID bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20211006184512__AL_TB_CMT_COUNCIL_MEM_MAST_061020211.sql
alter table TB_CMT_COUNCIL_MEM_MAST modify column COU_CAST_ID bigint(12) NULL;
--liquibase formatted sql
--changeset Kanchan:V20211006184512__AL_TB_CMT_COUNCIL_MEM_MAST_061020212.sql
alter table TB_CMT_COUNCIL_MEM_MAST modify column COU_MOBNO bigint(10) NULL;
--liquibase formatted sql
--changeset Kanchan:V20211006184512__AL_TB_CMT_COUNCIL_MEM_MAST_061020213.sql
alter table TB_CMT_COUNCIL_MEM_MAST modify column COU_DOB datetime NULL;
--liquibase formatted sql
--changeset Kanchan:V20211006184512__AL_TB_CMT_COUNCIL_MEM_MAST_061020214.sql
alter table TB_CMT_COUNCIL_MEM_MAST modify column COU_ELECDATE datetime NULL;
--liquibase formatted sql
--changeset Kanchan:V20211006184512__AL_TB_CMT_COUNCIL_MEM_MAST_061020215.sql
alter table TB_CMT_COUNCIL_MEM_MAST modify column COU_OATHDATE datetime NULL;
