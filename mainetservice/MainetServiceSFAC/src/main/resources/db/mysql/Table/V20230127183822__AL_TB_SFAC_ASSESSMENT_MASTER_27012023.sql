--liquibase formatted sql
--changeset Kanchan:V20230127183822__AL_TB_SFAC_ASSESSMENT_MASTER_27012023.sql
Alter table TB_SFAC_ASSESSMENT_MASTER
Add column KEY_PARAMETER bigint(20) Null default null,
Add column WEIGHTAGE bigint(20) Null default null,
Add column KEY_PARAMETER_DESC varchar(200) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230127183822__AL_TB_SFAC_ASSESSMENT_MASTER_270120231.sql
Alter table TB_SFAC_ASSESSMENT_MAST_HIST
Add column KEY_PARAMETER bigint(20) Null default null,
Add column WEIGHTAGE bigint(20) Null default null,
Add column KEY_PARAMETER_DESC varchar(200) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230127183822__AL_TB_SFAC_ASSESSMENT_MASTER_270120232.sql
Alter table TB_SFAC_ASSESSMENT_DETAIL
Add column SUB_PARAMETER_DESC varchar(500) Null default null,
Add column MEANS_OF_VERIFICATION_DESC varchar(500) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230127183822__AL_TB_SFAC_ASSESSMENT_MASTER_270120233.sql
Alter table TB_SFAC_ASSESSMENT_DET_HIST
Add column SUB_PARAMETER_DESC varchar(500) Null default null,
Add column MEANS_OF_VERIFICATION_DESC varchar(500) Null default null;
