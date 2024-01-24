--liquibase formatted sql
--changeset Kanchan:V20210531130848__AL_TB_EIP_FEEDBACK_31052021.sql
ALTER TABLE TB_EIP_FEEDBACK
ADD category_type_name varchar(100);
--liquibase formatted sql
--changeset Kanchan:V20210531130848__AL_TB_EIP_FEEDBACK_310520211.sql
ALTER TABLE TB_EIP_FEEDBACK_HIST
ADD catagory_type bigint(12);
--liquibase formatted sql
--changeset Kanchan:V20210531130848__AL_TB_EIP_FEEDBACK_310520212.sql
ALTER TABLE TB_EIP_FEEDBACK_HIST
ADD category_type_name varchar(100);
