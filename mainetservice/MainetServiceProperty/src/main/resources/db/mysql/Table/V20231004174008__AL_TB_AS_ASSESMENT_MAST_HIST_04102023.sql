--liquibase formatted sql
--changeset PramodPatil:V20231004174008__AL_TB_AS_ASSESMENT_MAST_HIST_04102023.sql
ALTER TABLE  TB_AS_ASSESMENT_MAST_HIST ADD COLUMN H_STATUS CHAR(1) null DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20231004174008__AL_TB_AS_ASSESMENT_MAST_HIST_041020231.sql
ALTER TABLE  TB_AS_ASSESMENT_OWNER_DTL_HIST ADD COLUMN H_STATUS CHAR(1) null DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20231004174008__AL_TB_AS_ASSESMENT_MAST_HIST_041020232.sql
ALTER TABLE  TB_AS_ASSESMENT_FACTOR_DTL_HIST ADD COLUMN H_STATUS CHAR(1) null DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20231004174008__AL_TB_AS_ASSESMENT_MAST_HIST_041020233.sql
ALTER TABLE  TB_AS_ASSESMENT_ROOM_DETAIL_HIST ADD COLUMN H_STATUS CHAR(1) null DEFAULT NULL;

--liquibase formatted sql
--changeset PramodPatil:V20231004174008__AL_TB_AS_ASSESMENT_MAST_HIST_041020234.sql
ALTER TABLE  TB_AS_ASSESMENT_DETAIL_HIST ADD COLUMN H_STATUS CHAR(1) null DEFAULT NULL;