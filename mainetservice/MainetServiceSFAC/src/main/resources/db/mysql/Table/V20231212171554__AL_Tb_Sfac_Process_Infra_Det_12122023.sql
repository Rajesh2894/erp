--liquibase formatted sql
--changeset PramodPatil:V20231212171554__AL_Tb_Sfac_Process_Infra_Det_12122023.sql
alter table Tb_Sfac_Process_Infra_Det add column PRO_FROM_DATE datetime null DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20231212171554__AL_Tb_Sfac_Process_Infra_Det_121220231.sql
alter table Tb_Sfac_Process_Infra_Det_hist add column PRO_FROM_DATE datetime null DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20231212171554__AL_Tb_Sfac_Process_Infra_Det_121220232.sql
alter table Tb_Sfac_Process_Infra_Det add column PRO_TO_DATE datetime null DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20231212171554__AL_Tb_Sfac_Process_Infra_Det_121220233.sql
alter table Tb_Sfac_Process_Infra_Det_hist add column PRO_TO_DATE datetime null DEFAULT NULL;