CREATE TABLE TB_TAX_BUDGET_CODE
(  taxb_id         BIGINT NOT NULL COMMENT 'primary key',
   tax_id          BIGINT NOT NULL COMMENT 'foregin key (TB_TAX_MAS)',
   orgid           BIGINT COMMENT 'orgnisation id',
   created_by      BIGINT COMMENT 'user id who created the record',
   created_date    DATETIME COMMENT 'record creation date',
   updated_by      INT COMMENT 'user id who update the data',
   updated_date    DATETIME COMMENT 'date on which data is going to update',
   budgetcode_id   BIGINT NOT NULL COMMENT 'foregin key (TB_AC_BUDGETCODE_MAS)',
   taxb_active     CHAR(1) COMMENT 'flag to identify whether the record is deleted or not. ''y'' for deleted (inactive) and ''n'' for not deleted (active) record.',
   lg_ip_mac       VARCHAR(100) COMMENT 'client machine?s login name | ip address | physical address',
   lg_ip_mac_upd   VARCHAR(100) COMMENT 'updated client machine?s login name | ip address | physical address');