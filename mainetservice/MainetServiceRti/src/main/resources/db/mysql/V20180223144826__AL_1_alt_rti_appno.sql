--liquibase formatted sql
--changeset ajit:V20180223144826__AL_1_alt_rti_appno.sql
ALTER TABLE tb_rti_application MODIFY APM_APPLICATION_ID bigint(16);

--changeset ajit:V20180223144826__AL_1_alt_rti_appno_2.sql
ALTER TABLE tb_rti_application MODIFY LOIAPPLICABLE varchar(5);

--changeset ajit:V20180223144826__AL_1_alt_rti_appno_3.sql
ALTER TABLE tb_rti_application MODIFY PARTIAL_INFO_FLAG  bigint(16);

--changeset ajit:V20180223144826__AL_1_alt_rti_appno_4.sql
ALTER TABLE tb_rti_media_details MODIFY MEDIA_TYPE bigint(16);

--changeset ajit:V20180223144826__AL_1_alt_rti_appno_5.sql
ALTER TABLE tb_rti_media_details MODIFY MEDIA_AMOUNT bigint(16);

--changeset ajit:V20180223144826__AL_1_alt_rti_appno_6.sql
ALTER TABLE tb_rti_media_details MODIFY LANG_ID bigint(16);

--changeset ajit:V20180223144826__AL_1_alt_rti_appno_7.sql
ALTER TABLE tb_rti_media_details drop column CARE_REQ_ID;

--changeset ajit:V20180223144826__AL_1_alt_rti_appno_8.sql
ALTER TABLE tb_rti_media_details ADD COLUMN UPL_FILE_NAME varchar(100),
                                 ADD COLUMN UPL_FILE_PATH varchar(100); 

                                                                 