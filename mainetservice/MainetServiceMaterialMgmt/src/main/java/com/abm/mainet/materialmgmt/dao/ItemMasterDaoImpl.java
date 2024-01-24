package com.abm.mainet.materialmgmt.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;	

import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.materialmgmt.domain.ItemMasterEntity;

@Repository
public class ItemMasterDaoImpl extends AbstractDAO<ItemMasterEntity> implements ItemMasterDao {

	@SuppressWarnings("unchecked")
	@Override
    @Transactional
	public List<ItemMasterEntity> findByAllGridSearchData(Long category, Long type, Long itemgroup, Long itemsubgroup,
			String name, Long orgId) {
		
		String queryString = "select distinct te from ItemMasterEntity te JOIN FETCH te.itemMasterConversionEntity where te.orgId =:orgId";
        if (category != null) {
            queryString += " and te.category =:category";
        }

        if (type != null) {
            queryString += " and te.type =:type";
        }
        if (itemgroup != null) {
            queryString += " and te.itemGroup =:itemgroup";
        }
        if (itemsubgroup != null) {
            queryString += " and te.itemSubGroup =:itemSubgroup";
        }
        if ((name != null) && !name.isEmpty()) {
            queryString += " and te.name =:name";
        }
        queryString += " order by 1 desc";

        final Query query = createQuery(queryString);

        query.setParameter("orgId",
                orgId);

        if (category != null) {
            query.setParameter("category",
            		category);
        }
        if (type != null) {
            query.setParameter("type",
            		type);
        }
        if (itemgroup != null) {
            query.setParameter("itemgroup",
            		itemgroup);
        }
        if (itemsubgroup != null) {
            query.setParameter("itemSubgroup",
            		itemsubgroup);
        }

        if ((name != null) && !name.isEmpty()) {
            query.setParameter("name",
            		name);
        }
        return query.getResultList();
	}

}
