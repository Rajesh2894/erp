--liquibase formatted sql
--changeset Kanchan:V20230131183314__AL_Tb_Sfac_Equity_Grant_Mast_31012023.sql
Alter table Tb_Sfac_Equity_Grant_Mast ADD Column AUTH_REMARK varchar(500) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230131183314__AL_Tb_Sfac_Equity_Grant_Mast_310120231.sql
Alter table Tb_Sfac_Equity_Grant_Mast Modify column APP_NO bigint(20) not null;
--liquibase formatted sql
--changeset Kanchan:V20230131183314__AL_Tb_Sfac_Equity_Grant_Mast_310120232.sql
Alter table tb_sfac_fpo_mgmt_cost_mast ADD Column AUTH_REMARK varchar(500) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230131183314__AL_Tb_Sfac_Equity_Grant_Mast_310120233.sql
Alter table tb_sfac_fpo_mgmt_cost_mast Modify column APP_NO bigint(20) not null;