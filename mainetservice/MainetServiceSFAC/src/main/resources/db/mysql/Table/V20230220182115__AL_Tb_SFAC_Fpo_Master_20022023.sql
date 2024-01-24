--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_20022023.sql
Alter table Tb_SFAC_Fpo_Master
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_200220231.sql
Alter table TB_SFAC_FPO_MASTER_HIST
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_200220232.sql
Alter table Tb_SFAC_Fpo_Master_Det
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_200220233.sql
Alter table TB_SFAC_FPO_DETAIL_HIST
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_200220234.sql
Alter table tb_sfac_fpo_bank_det
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_200220235.sql
Alter table TB_SFAC_FPO_BANK_DET_HIST
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_200220236.sql
Alter table tb_sfac_fpo_administrative_det
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;
--liquibase formatted sql
--changeset Kanchan:V20230220182115__AL_Tb_SFAC_Fpo_Master_200220237.sql
Alter table TB_SFAC_FPO_ADMINISTRATIVE_DET_HIST
Add column APPLICATION_ID bigint(20) Null default null,
Add column APP_STATUS varchar(100) Null default null;