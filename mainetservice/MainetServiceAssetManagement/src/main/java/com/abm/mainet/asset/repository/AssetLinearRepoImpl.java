/**
 * 
 */
package com.abm.mainet.asset.repository;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.asset.domain.AssetLinear;
import com.abm.mainet.common.dao.AbstractDAO;
import com.abm.mainet.common.exception.FrameworkException;

/**
 * @author satish.rathore
 *
 */
@Repository
public class AssetLinearRepoImpl extends AbstractDAO<Long> implements AssetLinearCustomRepo {

   
    /*
     * (non-Javadoc)
     * @see com.abm.mainet.asset.repository.AssetLinearCustomRepo#updateLinearAsset(java.lang.Long, java.lang.Long,
     * java.lang.Long, com.abm.mainet.asset.ui.dto.AssetLinearDTO)
     */
    @Override
    @Transactional
    public void updateLinearAsset(final AssetLinear entity) {

        final Query query = createQuery(buildUpdateDataQuery(entity));
        if (entity.getStartPoint() != null) {
            query.setParameter("startpoint", entity.getStartPoint());
        }
        if (entity.getEndPoint() != null) {
            query.setParameter("endpoint", entity.getEndPoint());
        }
        if (entity.getLength() != null) {
            query.setParameter("length", entity.getLength());
        }
        query.setParameter("lengthunit", entity.getLengthUnit());
        query.setParameter("typeoffset1", entity.getTypeOffset1());
        query.setParameter("offset1", entity.getOffset1());
        query.setParameter("offset1value", entity.getOffset1Value());
        query.setParameter("typeoffset2", entity.getTypeOffset2());
        query.setParameter("offset2", entity.getOffset2());
        query.setParameter("offset2value", entity.getOffset2Value());
        query.setParameter("markdesc", entity.getMarkDesc());
        query.setParameter("marktype", entity.getMarkType());
        query.setParameter("gridstartpoint", entity.getGridStartPoint());
        query.setParameter("gridendpoint", entity.getGridEndPoint());
        query.setParameter("uom", entity.getUom());
        query.setParameter("updatedby", entity.getUpdatedBy());
        query.setParameter("updateddate", entity.getUpdatedDate());
        query.setParameter("lgipmacupd", entity.getLgIpMacUpd());
        query.setParameter("assetlinearid", entity.getAssetLinearId());
        int result = query.executeUpdate();
        if (!(result > 0)) {
            throw new FrameworkException("Asset Service linear Details could not be updated");
        }
    }

    /**
     * @return
     */
    private String buildUpdateDataQuery(final AssetLinear entity) {
        final StringBuilder builder = new StringBuilder();
        builder.append("update AssetLinear al  set ");
        if (entity.getStartPoint() != null) {
            builder.append("al.startPoint=:startpoint ,");
        }
        if (entity.getEndPoint() != null) {
            builder.append(" al.endPoint=:endpoint ,");
        }
        if (entity.getLength() != null) {
            builder.append(" al.length=:length ,");
        }
        builder.append(" al.lengthUnit=:lengthunit ");
        builder.append(", al.typeOffset1=:typeoffset1 ");
        builder.append(", al.offset1=:offset1 ");
        builder.append(", al.offset1Value=:offset1value ");
        builder.append(", al.typeOffset2=:typeoffset2 ");
        builder.append(", al.offset2=:offset2 ");
        builder.append(", al.offset2Value=:offset2value ");
        builder.append(", al.markDesc=:markdesc ");
        builder.append(", al.markType=:marktype ");
        builder.append(", al.gridStartPoint=:gridstartpoint ");
        builder.append(", al.gridEndPoint=:gridendpoint ");
        builder.append(", al.uom=:uom ");
        builder.append(", al.updatedBy=:updatedby ");
        builder.append(", al.updatedDate=:updateddate ");
        builder.append(", al.lgIpMacUpd=:lgipmacupd ");
        builder.append(" where al.assetLinearId=:assetlinearid ");
        return builder.toString();

    }

}
