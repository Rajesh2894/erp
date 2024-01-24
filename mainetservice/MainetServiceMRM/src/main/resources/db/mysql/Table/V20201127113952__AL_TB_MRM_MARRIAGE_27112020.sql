--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_27112020.sql
  alter table TB_MRM_MARRIAGE add primary key (MAR_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120201.sql
  alter table TB_MRM_MARRIAGE_HIST add primary key (MAR_ID_H);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120202.sql
  alter table TB_MRM_MARRIAGE_HIST add foreign key (MAR_ID) references TB_MRM_MARRIAGE (MAR_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120203.sql
  alter table TB_MRM_HUSBAND add primary key (HUSBAND_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120204.sql
  alter table TB_MRM_HUSBAND add foreign key (MAR_ID) references  TB_MRM_MARRIAGE (MAR_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120205.sql
  alter table TB_MRM_HUSBAND_HIST add primary key (HUSBAND_ID_H);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120206.sql
  alter table TB_MRM_HUSBAND_HIST add foreign key (HUSBAND_ID) references TB_MRM_HUSBAND (HUSBAND_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120207.sql
  alter table TB_MRM_HUSBAND_HIST add foreign key (MAR_ID) references TB_MRM_MARRIAGE (MAR_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120208.sql
  alter table TB_MRM_WIFE add primary key (WIFE_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_271120209.sql
  alter table TB_MRM_WIFE add foreign key (MAR_ID) references  TB_MRM_MARRIAGE (MAR_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202010.sql
  alter table TB_MRM_WIFE_HIST add primary key (WIFE_ID_H);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202011.sql
  alter table TB_MRM_WIFE_HIST add foreign key (WIFE_ID) references TB_MRM_WIFE (WIFE_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202012.sql
  alter table TB_MRM_WIFE_HIST add foreign key (MAR_ID) references TB_MRM_MARRIAGE (MAR_ID) ;  
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202013.sql
  alter table TB_MRM_WITNESS_DET add primary key (WITNESS_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202014.sql
  alter table TB_MRM_WITNESS_DET add foreign key (MAR_ID) references TB_MRM_MARRIAGE (MAR_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202015.sql
  alter table TB_MRM_WITNESS_DET_HIST add primary key (WITNESS_ID_H);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202016.sql
  alter table TB_MRM_WITNESS_DET_HIST add foreign key (WITNESS_ID) references  TB_MRM_WITNESS_DET (WITNESS_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202017.sql
  alter table TB_MRM_WITNESS_DET_HIST add foreign key (MAR_ID) references TB_MRM_MARRIAGE (MAR_ID);
  --liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202018.sql
  alter table TB_MRM_APPOINTMENT add primary key (APPOINTMENT_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202019.sql
  alter table TB_MRM_APPOINTMENT add foreign key (MAR_ID) references TB_MRM_MARRIAGE (MAR_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202020.sql
  alter table TB_MRM_APPOINTMENT_HIST add primary key (APPOINTMENT_ID_H);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202021.sql
  alter table TB_MRM_APPOINTMENT_HIST add foreign key (APPOINTMENT_ID) references  TB_MRM_APPOINTMENT (APPOINTMENT_ID);
--liquibase formatted sql
--changeset Kanchan:V20201127113952__AL_TB_MRM_MARRIAGE_2711202022.sql
  alter table TB_MRM_APPOINTMENT_HIST add foreign key (MAR_ID) references  TB_MRM_MARRIAGE (MAR_ID);


