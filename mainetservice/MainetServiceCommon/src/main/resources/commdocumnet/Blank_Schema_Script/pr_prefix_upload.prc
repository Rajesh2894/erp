create or replace procedure pr_prefix_upload (p_module varchar2,v_errormsg varchar2) as

  cursor cu_com_mas is
  select * from tb_comparam_mas_temp p where p.cpm_module_name=P_Module /*and nvl(p.cpm_type,'N')<>'H'*/ and p.cpm_status='A';

  cursor cu_com_det(n_cpmid number) is
  select * from tb_comparam_det_temp q where q.cpm_id=n_cpmid and q.cpd_status='A' and q.orgid=81;

  cursor cu_comparent_mas(n_cpmid number) is
  select * from Tb_Comparent_Mas_tmp q where q.cpm_id=n_cpmid and q.com_status='Y' and q.orgid=81;

  cursor cu_comparent_det(n_comid number) is
  select * from Tb_Comparent_det_tmp r where r.com_id=n_comid and r.cod_status='Y' and r.orgid=81;


  cursor cu_orgid is
  select * from tb_organisation o;


  v_rec_mas cu_com_mas%rowtype;
  v_rec_det cu_com_det%rowtype;
  v_rec_parentmas cu_comparent_mas%rowtype;
  v_rec_parentdet cu_comparent_det%rowtype;
  n_cpmid tb_comparam_mas.cpm_id%type;
  n_cpdid tb_comparam_det.cpd_id%type;
  n_comid tb_comparent_mas.com_id%type;
  n_codid tb_comparent_det.cod_id%type;
  v_codvalue tb_comparent_det.cod_value%type;
  n_orgid tb_organisation.orgid%type;
  n_parentid tb_comparent_det.parent_id%type;
  v_rec_org cu_orgid%rowtype;
  n_cntmas number:=0;
  n_cntdet number:=0;
  

begin

 dbms_output.put_line('In Procedure '||P_Module);

open cu_com_mas;
loop
  fetch cu_com_mas into v_rec_mas;
  exit when cu_com_mas%notfound;

          begin
            select count(1) into n_cntmas
              from tb_comparam_mas p where p.cpm_prefix=v_rec_mas.cpm_prefix;

              if n_cntmas>0 then

                            select cpm_id into n_cpmid
                            from tb_comparam_mas p where p.cpm_prefix=v_rec_mas.cpm_prefix;


                                 if nvl(v_rec_mas.cpm_type,'N')='N' then

                                    select count(1) into n_cntdet
                                    from tb_comparam_det p where p.cpm_id=n_cpmid;
                                 else
                                    select count(1) into n_cntdet
                                    from tb_comparent_mas p where p.cpm_id=n_cpmid;
                                 end if;

                            if n_cntdet>0 then
                               continue;
                            else
                              select cpm_id into n_cpmid
                              from tb_comparam_mas p where p.cpm_prefix=v_rec_mas.cpm_prefix;

                            end if;
              end if;
         end;

      if n_cntmas =0 then

      n_cpmid:=fn_java_sq_generation('AUT','TB_COMPARAM_MAS','CPM_ID',NULL,NULL);

           insert into tb_comparam_mas
           (cpm_id,
          cpm_prefix,
          cpm_desc,
          cpm_status,
          user_id,
          lang_id,
          lmoddate,
          cpm_limited_yn,
          cpm_module_name,
          cpm_config,
          cpm_edit,
          lg_ip_mac,
          cpm_replicate_flag,
          cpm_type,
          load_at_startup)
          values
          (n_cpmid,
           v_rec_mas.cpm_prefix,
           v_rec_mas.cpm_desc,
           v_rec_mas.cpm_status,
           v_rec_mas.user_id,
           v_rec_mas.lang_id,
           sysdate,
           v_rec_mas.cpm_limited_yn,
           v_rec_mas.cpm_module_name,
           v_rec_mas.cpm_config,
           v_rec_mas.cpm_edit,
           v_rec_mas.lg_ip_mac,
           v_rec_mas.cpm_replicate_flag,
           v_rec_mas.cpm_type,
           v_rec_mas.load_at_startup);
      end if;

        if nvl(v_rec_mas.cpm_type,'N')='N' then

                open cu_com_det(v_rec_mas.cpm_id);
                loop
                  fetch cu_com_det into v_rec_det;
                  exit when cu_com_det%notfound;


                        select o.orgid into n_orgid
                        from tb_organisation o where o.default_status='Y';

                        insert into tb_comparam_det
                        (cpd_id,
                         orgid,
                         cpd_desc,
                         cpd_value,
                         cpd_status,
                         cpm_id,
                         user_id,
                         lang_id,
                         lmoddate,
                         cpd_default,
                         cpd_desc_mar,
                         cpd_others,
                         lg_ip_mac)
                         values
                         (fn_java_sq_generation('AUT','TB_COMPARAM_DET','CPD_ID',NULL,NULL),
                          n_orgid,
                          v_rec_det.cpd_desc,
                          v_rec_det.cpd_value,
                          v_rec_det.cpd_status,
                          n_cpmid,
                          v_rec_det.user_id,
                          v_rec_det.lang_id,
                          sysdate,
                          v_rec_det.cpd_default,
                          v_rec_det.cpd_desc_mar,
                          v_rec_det.cpd_others,
                          v_rec_det.lg_ip_mac);

            end loop;
            close cu_com_det;
    else
             open  cu_comparent_mas(v_rec_mas.cpm_id);
             loop
             fetch cu_comparent_mas into  v_rec_parentmas;
             exit when cu_comparent_mas%notfound;

                      /*open cu_orgid;
                          loop
                            fetch cu_orgid into v_rec_org;
                            exit when cu_orgid%notfound;*/
                             select o.orgid into n_orgid
                             from tb_organisation o where o.default_status='Y';

                               -- dbms_output.put_line(v_rec_org.orgid||v_rec_org.o_nls_orgname);

                               n_comid:=fn_java_sq_generation('AUT','TB_COMPARENT_MAS','COM_ID',NULL,NULL);

                                INSERT INTO tb_comparent_mas
                                (com_id, orgid, cpm_id, com_desc,
                                 com_value, com_level, com_status,
                                 user_id,
                                 lang_id, lmoddate, updated_by, updated_date,
                                 com_desc_mar,com_replicate_flag)
                         VALUES (n_comid, n_orgid, n_cpmid, v_rec_parentmas.com_desc,
                                 v_rec_parentmas.com_value, v_rec_parentmas.com_level, v_rec_parentmas.com_status,
                                 v_rec_parentmas.user_id,
                                 v_rec_parentmas.lang_id,
                                 sysdate,
                                 null,
                                 null,
                                v_rec_parentmas.com_desc_mar,v_rec_parentmas.com_replicate_flag);

                                  open  cu_comparent_det(v_rec_parentmas.com_id);
                                  loop
                                  fetch cu_comparent_det into  v_rec_parentdet;
                                  exit when cu_comparent_det%notfound;
                                  
                                     if v_rec_parentdet.parent_id is not null then
                                           select m.cod_value into v_codvalue
                                           from tb_comparent_det_tmp m
                                           where m.cod_id=v_rec_parentdet.parent_id;
                                           
                                           if v_codvalue is not null then

                                            dbms_output.put_line('v_codvalue '||v_codvalue);
                                            dbms_output.put_line('cpm_prefix '||v_rec_mas.cpm_prefix);
                                            dbms_output.put_line('v_codvalue '||n_orgid);
                                             
                                             begin 
                                                 select d.cod_id into n_parentid 
                                                 from tb_comparent_det d,tb_comparent_mas c,
                                                 tb_comparam_mas b where b.cpm_id=c.cpm_id and c.com_id=d.com_id
                                                 and d.cod_value=v_codvalue and b.cpm_prefix=v_rec_mas.cpm_prefix
                                                 and d.orgid=n_orgid;
                                             exception
                                                 when others then
                                                   n_parentid:=null;     
                                             end;    
                                             
                                           end if;
                                        end if;  


                               n_codid:=fn_java_sq_generation('AUT','TB_COMPARENT_DET','COD_ID',NULL,NULL);

                                        INSERT INTO tb_comparent_det
                                            (cod_id, orgid, com_id, cod_desc,
                                             cod_value, parent_id,
                                             user_id,
                                             lang_id,
                                             lmoddate, updated_by, updated_date,
                                             cpd_default, cod_status, cod_desc_mar)
                                      VALUES (n_codid, n_orgid,n_comid,
                                              v_rec_parentdet.cod_desc,
                                              v_rec_parentdet.cod_value,
                                               v_rec_parentdet.parent_id,
                                               v_rec_parentdet.user_id,
                                               v_rec_parentdet.lang_id,
                                                sysdate,
                                                null,
                                                null,
                                                v_rec_parentdet.cpd_default,
                                                v_rec_parentdet.cod_status,
                                                v_rec_parentdet.cod_desc_mar);
                                 end loop;
                                 close cu_comparent_det;
                           /* end loop;
                        close cu_orgid;*/

                 end loop;
                 close cu_comparent_mas;

    end if;
      commit;
             -- dbms_output.put_line('Uploaded Prefix '||v_rec_mas.cpm_prefix);

               end loop;
            close cu_com_mas;
              dbms_output.put_line('Completed Module '||P_Module);


exception
    when others then
        rollback;
        dbms_output.put_line('Data Rollbacked');
end;
/
