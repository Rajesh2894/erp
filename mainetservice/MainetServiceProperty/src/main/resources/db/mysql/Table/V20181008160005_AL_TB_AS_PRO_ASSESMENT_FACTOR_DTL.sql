--liquibase formatted sql
--changeset nilima:V20181008160005_AL_TB_AS_PRO_ASSESMENT_FACTOR_DTL1.sql
alter table TB_AS_PRO_ASSESMENT_FACTOR_DTL change column pro_assf_factor pro_assf_factor bigint(20) NULL COMMENT '	Factors (PREFIX ''FCT'')	';
--liquibase formatted sql
--changeset nilima:V20181008160005_AL_TB_AS_PRO_ASSESMENT_FACTOR_DTL2.sql
alter table tb_as_assesment_factor_dtl change column MN_assf_factor MN_assf_factor bigint(20) NULL;
--liquibase formatted sql
--changeset nilima:V20181008160005_AL_TB_AS_PRO_ASSESMENT_FACTOR_DTL3.sql
alter table tb_as_prop_factor change column PF_FACTOR PF_FACTOR bigint(20) NULL;
--liquibase formatted sql
--changeset nilima:V20181008160005_AL_TB_AS_PRO_ASSESMENT_FACTOR_DTL4.sql
alter table TB_AS_PRO_FACTOR_DTL_HIST change column pro_assf_factor pro_assf_factor bigint(20) NULL COMMENT '	Factors (PREFIX ''FCT'')	';
--liquibase formatted sql
--changeset nilima:V20181008160005_AL_TB_AS_PRO_ASSESMENT_FACTOR_DTL5.sql
alter table TB_AS_FACTOR_DTL_HIST change column MN_assf_factor MN_assf_factor bigint(20) NULL;


