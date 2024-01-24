--liquibase formatted sql
--changeset Kanchan:V20230220112027__AL_TB_ADH_MAS_20022023.sql
ALTER TABLE TB_ADH_MAS
MODIFY COLUMN ADH_ZONE1 bigint(20) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220112027__AL_TB_ADH_MAS_200220231.sql
ALTER TABLE TB_ADH_MAS_HIST
MODIFY COLUMN ADH_ZONE1 bigint(20) Null default null;