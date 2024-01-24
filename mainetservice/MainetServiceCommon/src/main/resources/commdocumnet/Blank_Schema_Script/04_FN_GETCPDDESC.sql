CREATE OR REPLACE FUNCTION "FN_GETCPDDESC" (n_id number, v_data varchar2, n_orgid number) return varchar2 is
   v_value varchar2(1000);
begin
-- v_data = 'E' --> English  --> English Description from tb_comparam_det
-- v_data = 'R' --> Regional --> Marathi Description from tb_comparam_det
-- v_data = 'V' --> Value    --> Getting Cpd_Value   from tb_comparam_det
-- v_data = 'O' --> Value    --> Getting Cpd_Others  from tb_comparam_det

  select trim(to_char(decode(v_data,'E',cpd_desc,'R',nvl(cpd_desc_mar,cpd_desc),'V',cpd_value,'O', cpd_others,null)))  --- added 'O' by pratibha on 06-02-2010
        --- trim, to_char is added by ankur on 19/10/2010 because all 3 fields are nvarchar2 so some times it create problems.
     into v_value
     from tb_comparam_det
    where cpd_status = 'A'
      and orgid      = n_orgid
      and cpd_id     = n_id;

   return v_value;
exception
  when others then
    return null;
end;
