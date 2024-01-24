--liquibase formatted sql
--changeset Kanchan:V20230410192619__AL_Tb_SFAC_FPO_Master_10042023.sql
Alter table Tb_SFAC_FPO_Master
modify column FPO_TAN_NO varchar(10) null default null,
modify column FPO_PAN_NO varchar(10) null default null;
--liquibase formatted sql
--changeset Kanchan:V20230410192619__AL_Tb_SFAC_FPO_Master_100420231.sql
Alter table Tb_SFAC_FPO_Master_hist
modify column FPO_TAN_NO varchar(10) null default null,
modify column FPO_PAN_NO varchar(10) null default null;