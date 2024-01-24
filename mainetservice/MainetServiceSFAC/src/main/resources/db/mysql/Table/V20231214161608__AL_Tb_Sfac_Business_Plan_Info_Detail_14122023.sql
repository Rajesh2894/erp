--liquibase formatted sql
--changeset PramodPatil:V20231214161608__AL_Tb_Sfac_Business_Plan_Info_Detail_14122023.sql
alter table Tb_Sfac_Business_Plan_Info_Detail add column BP_TARGET bigint(20) null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231214161608__AL_Tb_Sfac_Business_Plan_Info_Detail_141220231.sql
alter table Tb_Sfac_Audited_Balance_Sheet_Info_Detail add column MCA_PORTAL_DATE datetime null default null;