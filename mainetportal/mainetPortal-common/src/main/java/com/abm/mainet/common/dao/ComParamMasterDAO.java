package com.abm.mainet.common.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

//import com.abm.mainet.common.care.masters.TbComparamDetEntityCommon;
//import com.abm.mainet.common.care.masters.TbComparamMasEntityCommon;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.util.LookUp;

/**
 * @author Pranit.Mhatre
 * @since 29 November,2013 Previously class extends {@link AbstractDAO} because we are using ComParamMas for fetching
 * {@link LookUp} Data from DB but Now we are using {@link ViewPrefixDetails} for the same. So removed {@link AbstractDAO}
 * and @Autowired {@link SessionFactory} Removed unused functions ------ @Date : 05-10-2015.
 */
@SuppressWarnings("unchecked")
@Repository
public class ComParamMasterDAO extends AbstractDAO<ViewPrefixDetails> implements IComParamMasterDAO {

    @Override
    public List<String> getAllStartupPrefix() {
        List<String> list = null;
        final Query query = createQuery("Select distinct vd.cpmPrefix from ViewPrefixDetails vd ");

        list = query.getResultList();

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

   
    @Override
    public List<ViewPrefixDetails> getViewPrefixDetailsByType(final String cpmType) {

        final StringBuilder queryAppender = new StringBuilder();
        List<ViewPrefixDetails> list = null;
        queryAppender.append("Select vd from ViewPrefixDetails vd where vd.cpmType = ?1 ");

        if (cpmType.equals(MainetConstants.LookUp.HIERARCHICAL)) {
            queryAppender.append("order by vd.comLevel asc");

        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, cpmType);

        list = query.getResultList();

        if (list == null) {
            list = new ArrayList<>();
        }

        return list;
    }

    @Override
    public List<String> getNonReplicatePrefix() {

        List<String> prefixs = null;


        final Query query = createQuery("select distinct vd.cpmPrefix from ViewPrefixDetails vd where vd.cpmReplicateFlag =?1 ");
        query.setParameter(1, MainetConstants.IsLookUp.STATUS.NO);

        prefixs = query.getResultList();

        if (prefixs == null) {
            prefixs = new ArrayList<>();
        }

        return prefixs;
    }

}
