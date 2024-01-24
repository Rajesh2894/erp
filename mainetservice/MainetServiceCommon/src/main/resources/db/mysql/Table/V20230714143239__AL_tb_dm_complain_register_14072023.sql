--liquibase formatted sql
--changeset PramodPatil:V20230714143239__AL_tb_dm_complain_register_14072023.sql
alter table tb_dm_complain_register add column no_of_veh_Damaged varchar(500) null default null;


--liquibase formatted sql
--changeset PramodPatil:V20230714143239__AL_tb_dm_complain_register_140720231.sql
alter table tb_dm_complain_register modify column call_close_remark varchar(800) null default null;