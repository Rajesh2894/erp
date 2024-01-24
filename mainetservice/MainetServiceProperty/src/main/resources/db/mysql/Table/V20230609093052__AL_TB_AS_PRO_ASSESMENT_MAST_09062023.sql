--liquibase formatted sql
--changeset Kanchan:V20230609093052__AL_TB_AS_PRO_ASSESMENT_MAST_09062023.sql
ALTER TABLE TB_AS_PRO_ASSESMENT_MAST
MODIFY COLUMN PRO_ASS_PINCODE INT(11) NULL default null,
MODIFY COLUMN PRO_ASS_CORR_PINCODE INT(11) NULL default null;
--liquibase formatted sql
--changeset Kanchan:V20230609093052__AL_TB_AS_PRO_ASSESMENT_MAST_090620231.sql
ALTER TABLE TB_AS_PRO_MAST_HIST
MODIFY COLUMN PRO_ASS_PINCODE INT(11) NULL default null,
MODIFY COLUMN PRO_ASS_CORR_PINCODE INT(11) NULL default null;
--liquibase formatted sql
--changeset Kanchan:V20230609093052__AL_TB_AS_PRO_ASSESMENT_MAST_090620232.sql
ALTER TABLE tb_as_assesment_mast
MODIFY COLUMN MN_ASS_pincode INT(11) NULL default null,
MODIFY COLUMN MN_ASS_corr_pincode INT(11) NULL default null;
--liquibase formatted sql
--changeset Kanchan:V20230609093052__AL_TB_AS_PRO_ASSESMENT_MAST_090620233.sql
ALTER TABLE TB_AS_ASSESMENT_MAST_HIST
MODIFY COLUMN MN_ASS_pincode INT(11) NULL default null,
MODIFY COLUMN MN_ASS_corr_pincode INT(11) NULL default null;