--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST1.sql
alter table tb_as_pro_assesment_mast change column PRO_ASS_OLDPROPNO PRO_ASS_OLDPROPNO VARCHAR(20) NULL COMMENT '	OLD PROPERTY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST2.sql
alter table tb_as_pro_assesment_mast change column TPP_PLOT_NO_CS TPP_PLOT_NO_CS VARCHAR(50) NULL COMMENT '	CSN_KHASARA NO	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST3.sql
alter table tb_as_pro_assesment_mast change column TPP_SURVEY_NUMBER TPP_SURVEY_NUMBER VARCHAR(25) NULL COMMENT '	SURVEY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST4.sql
alter table tb_as_pro_assesment_mast change column TPP_KHATA_NO TPP_KHATA_NO VARCHAR(50) NULL COMMENT '	KHATA NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST5.sql
alter table tb_as_pro_assesment_mast change column TPP_TOJI_NO TPP_TOJI_NO VARCHAR(50) NULL COMMENT '	TOJI NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST6.sql
alter table tb_as_pro_assesment_mast change column TPP_PLOT_NO TPP_PLOT_NO VARCHAR(50) NULL COMMENT '	PLOT NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST7.sql
alter table tb_as_pro_assesment_mast change column PRO_ASS_STREET_NO PRO_ASS_STREET_NO VARCHAR(500) NULL COMMENT '	STREET NUMBER_NAME	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST8.sql
alter table tb_as_pro_assesment_mast change column TPP_VILLAGE_MAUJA TPP_VILLAGE_MAUJA VARCHAR(50) NULL COMMENT '	VILLAGE_MAUJA NAME	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST9.sql
alter table tb_as_pro_mast_hist change column PRO_ASS_OLDPROPNO PRO_ASS_OLDPROPNO VARCHAR(20) NULL COMMENT '	OLD PROPERTY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST10.sql
alter table tb_as_pro_mast_hist change column TPP_PLOT_NO_CS TPP_PLOT_NO_CS VARCHAR(50) NULL COMMENT '	CSN_KHASARA NO	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST11.sql
alter table tb_as_pro_mast_hist change column TPP_SURVEY_NUMBER TPP_SURVEY_NUMBER VARCHAR(25) NULL COMMENT '	SURVEY NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST12.sql
alter table tb_as_pro_mast_hist change column TPP_KHATA_NO TPP_KHATA_NO VARCHAR(50) NULL COMMENT '	KHATA NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST13.sql
alter table tb_as_pro_mast_hist change column TPP_TOJI_NO TPP_TOJI_NO VARCHAR(50) NULL COMMENT '	TOJI NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST14.sql
alter table tb_as_pro_mast_hist change column TPP_PLOT_NO TPP_PLOT_NO VARCHAR(50) NULL COMMENT '	PLOT NUMBER	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST15.sql
alter table tb_as_pro_mast_hist change column PRO_ASS_STREET_NO PRO_ASS_STREET_NO VARCHAR(500) NULL COMMENT '	STREET NUMBER_NAME	';
--liquibase formatted sql
--changeset nilima:V20180903133000_AL_TB_AS_PRO_ASSESMENT_MAST16.sql
alter table tb_as_pro_mast_hist change column TPP_VILLAGE_MAUJA TPP_VILLAGE_MAUJA VARCHAR(50) NULL COMMENT '	VILLAGE_MAUJA NAME	';