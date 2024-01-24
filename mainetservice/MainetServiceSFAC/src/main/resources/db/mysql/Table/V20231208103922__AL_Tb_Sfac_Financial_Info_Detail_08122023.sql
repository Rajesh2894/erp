--liquibase formatted sql
--changeset PramodPatil:V20231208103922__AL_Tb_Sfac_Financial_Info_Detail_08122023.sql
alter table Tb_Sfac_Financial_Info_Detail add column MONTH_NO bigint(20) null;

--liquibase formatted sql
--changeset PramodPatil:V20231208103922__AL_Tb_Sfac_Financial_Info_Detail_081220231.sql
alter table Tb_Sfac_Financial_Info_Detail_hist add column MONTH_NO bigint(20) null;