--liquibase formatted sql
--changeset Kanchan:V20221122193825__AL_TB_SFAC_CBBO_MASTER_22112022.sql
Alter table TB_SFAC_CBBO_MASTER drop column IA_ALLOCATION_YEAR;
--liquibase formatted sql
--changeset Kanchan:V20221122193825__AL_TB_SFAC_CBBO_MASTER_221120221.sql
Alter table TB_SFAC_CBBO_MASTER drop column ALLOCATION_CATEGORY;
--liquibase formatted sql
--changeset Kanchan:V20221122193825__AL_TB_SFAC_CBBO_MASTER_221120222.sql
Alter table TB_SFAC_CBBO_MASTER drop column STATE_CATEGORY;
--liquibase formatted sql
--changeset Kanchan:V20221122193825__AL_TB_SFAC_CBBO_MASTER_221120223.sql
Alter table TB_SFAC_CBBO_MASTER drop column ODOP;