--liquibase formatted sql
--changeset PramodPatil:V20231212140657__AL_Tb_Sfac_Storage_Info_Detail_12122023.sql
alter table Tb_Sfac_Storage_Info_Detail add column STOR_FROM_DATE datetime null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231212140657__AL_Tb_Sfac_Storage_Info_Detail_121220231.sql
alter table tb_sfac_storage_info_detail_hist add column STOR_FROM_DATE datetime null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231212140657__AL_Tb_Sfac_Storage_Info_Detail_121220232.sql
alter table Tb_Sfac_Storage_Info_Detail add column STOR_TO_DATE datetime null default null;

--liquibase formatted sql
--changeset PramodPatil:V20231212140657__AL_Tb_Sfac_Storage_Info_Detail_121220233.sql
alter table tb_sfac_storage_info_detail_hist add column STOR_TO_DATE datetime null default null;