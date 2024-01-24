--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST1.sql
alter table tb_as_assesment_mast change column MN_ASS_OLDPROPNO MN_ASS_OLDPROPNO VARCHAR(20) NULL COMMENT '	OLD PROPERTY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST2.sql
alter table tb_as_assesment_mast change column MN_plot_no_cs MN_plot_no_cs VARCHAR(50) NULL COMMENT '	CSN_KHASARA NO	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST3.sql
alter table tb_as_assesment_mast change column MN_survey_number MN_survey_number VARCHAR(25) NULL COMMENT '	SURVEY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST4.sql
alter table tb_as_assesment_mast change column MN_khata_no MN_khata_no VARCHAR(50) NULL COMMENT '	KHATA NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST5.sql
alter table tb_as_assesment_mast change column MN_toji_no MN_toji_no VARCHAR(50) NULL COMMENT '	TOJI NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST6.sql
alter table tb_as_assesment_mast change column MN_plot_no MN_plot_no VARCHAR(50) NULL COMMENT '	PLOT NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST7.sql
alter table tb_as_assesment_mast change column MN_ASS_STREET_NO MN_ASS_STREET_NO VARCHAR(500) NULL COMMENT '	STREET NUMBER_NAME	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST8.sql
alter table tb_as_assesment_mast change column MN_village_mauja MN_village_mauja VARCHAR(50) NULL COMMENT '	VILLAGE_MAUJA NAME	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST9.sql
alter table tb_as_mast_hist change column MN_ASS_OLDPROPNO MN_ASS_OLDPROPNO VARCHAR(20) NULL COMMENT '	OLD PROPERTY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST10.sql
alter table tb_as_mast_hist change column MN_plot_no_cs MN_plot_no_cs VARCHAR(50) NULL COMMENT '	CSN_KHASARA NO	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST11.sql
alter table tb_as_mast_hist change column MN_survey_number MN_survey_number VARCHAR(25) NULL COMMENT '	SURVEY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST12.sql
alter table tb_as_mast_hist change column MN_khata_no MN_khata_no VARCHAR(50) NULL COMMENT '	KHATA NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST13.sql
alter table tb_as_mast_hist change column MN_toji_no MN_toji_no VARCHAR(50) NULL COMMENT '	TOJI NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST14.sql
alter table tb_as_mast_hist change column MN_plot_no MN_plot_no VARCHAR(50) NULL COMMENT '	PLOT NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST15.sql
alter table tb_as_mast_hist change column MN_ASS_STREET_NO MN_ASS_STREET_NO VARCHAR(500) NULL COMMENT '	STREET NUMBER_NAME	';
--liquibase formatted sql
--changeset nilima:V20180903133001_AL_TB_AS_ASSESMENT_MAST16.sql
alter table tb_as_mast_hist change column MN_village_mauja MN_village_mauja VARCHAR(50) NULL COMMENT '	VILLAGE_MAUJA NAME	';