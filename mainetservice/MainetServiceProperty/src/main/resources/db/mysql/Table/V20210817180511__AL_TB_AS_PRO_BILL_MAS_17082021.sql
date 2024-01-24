--liquibase formatted sql
--changeset Kanchan:V20210817180511__AL_TB_AS_PRO_BILL_MAS_17082021.sql
alter table TB_AS_PRO_BILL_MAS modify column group_prop_no varchar(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210817180511__AL_TB_AS_PRO_BILL_MAS_170820211.sql
alter table TB_AS_PRO_BILL_MAS modify column parent_prop_no varchar(20) NULL;
--liquibase formatted sql
--changeset Kanchan:V20210817180511__AL_TB_AS_PRO_BILL_MAS_170820212.sql
alter table TB_AS_PRO_BILL_MAS modify column GROUP_MN_NO varchar(16) NUll;
