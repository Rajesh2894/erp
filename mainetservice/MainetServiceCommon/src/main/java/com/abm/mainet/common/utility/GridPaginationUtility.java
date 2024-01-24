package com.abm.mainet.common.utility;

import javax.persistence.Query;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;

public class GridPaginationUtility {

    private GridPaginationUtility() {
        throw new IllegalAccessError("GridPaginationUtility class");
    }

    public static StringBuilder doGridPgination(final StringBuilder query, final PagingDTO pagingDTO,
            final GridSearchDTO gridSearchDTO,
            final String coloumnName, final String reference) {

        if (gridSearchDTO.getSearchOper() != null) {
            switch (gridSearchDTO.getSearchOper()) {
            case MainetConstants.EQ:
                query.append(" and " + reference + coloumnName + "=" + "'" + gridSearchDTO.getSearchString() + "'");
                break;
            case MainetConstants.NE:
                query.append(" and " + reference + coloumnName + "<>" + "'" + gridSearchDTO.getSearchString() + "'");
                break;
            case MainetConstants.CN:
                query.append(" and " + reference + coloumnName + " like '%" + gridSearchDTO.getSearchString() + "%'");
                break;
            }
        }
        return query;
    }

    public static Query doGridPaginationLimit(final Query query, final PagingDTO pagingDTO) {

        query.setMaxResults(pagingDTO.getRows());

        if (pagingDTO.getPage() > 1) {
            query.setFirstResult(pagingDTO.getRows() * (pagingDTO.getPage() - 1));
        }
        return query;
    }

    public static StringBuilder doGridOrderBy(final StringBuilder query, final PagingDTO pagingDTO,
            final String coloumnName, final String reference) {
        if (MainetConstants.ASC.equals(pagingDTO.getSord()) && !MainetConstants.ROW_ID.equals(pagingDTO.getSidx())) {
            query.append(" order by " + reference
                    + coloumnName + " asc ");
        } else {
            query.append(" order by " + reference
                    + coloumnName + " desc ");
        }

        return query;
    }

}
