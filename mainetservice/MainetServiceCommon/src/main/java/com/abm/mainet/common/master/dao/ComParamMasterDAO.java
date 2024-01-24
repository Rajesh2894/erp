package com.abm.mainet.common.master.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.domain.ViewPrefixDetails;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author Pranit.Mhatre
 * @since 29 November,2013 Previously class extends {@link AbstractDAO} because we are using ComParamMas for fatching
 * {@link LookUp} Data from DB but Now we are using {@link ViewPrefixDetails} for the same. So removed {@link AbstractDAO}
 * and @Autowired {@link SessionFactory} Removed unused functions ------ @Date : 05-10-2015.
 */
@Repository
public class ComParamMasterDAO extends AbstractDAO<ViewPrefixDetails> implements IComParamMasterDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<String> getAllStartupPrefix() {

        final Query query = createQuery("Select distinct vd.cpmPrefix from ViewPrefixDetails vd ");

        final List<String> list = query.getResultList();

        if (list != null) {
            return list;
        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public List<ViewPrefixDetails> getViewPrefixDetailsByType(final String cpmType) {
        final StringBuilder queryAppender = new StringBuilder();
        queryAppender.append("Select vd from ViewPrefixDetails vd where vd.cpmType = ?1 ");

        if (cpmType.equals(PrefixConstants.LookUp.HIERARCHICAL)) {

            queryAppender.append("order by vd.comLevel asc");

        }

        final Query query = createQuery(queryAppender.toString());
        query.setParameter(1, cpmType);

        final List<ViewPrefixDetails> list = query.getResultList();

        if (list != null) {
            return list;
        }

        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getNonReplicatePrefix() {

        List<String> prefixs = new ArrayList<>(0);

        final Query query = createQuery("select distinct vd.cpmPrefix from ViewPrefixDetails vd where vd.cpmReplicateFlag =?1 ");
        query.setParameter(1, PrefixConstants.IsLookUp.STATUS.NO);

        prefixs = query.getResultList();

        if ((prefixs != null) && (prefixs.size() > 0)) {
            return prefixs;
        }

        return Collections.EMPTY_LIST;
    }

}
