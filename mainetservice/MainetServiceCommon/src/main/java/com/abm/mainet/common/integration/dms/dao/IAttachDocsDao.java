/**
 *
 */
package com.abm.mainet.common.integration.dms.dao;

import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public interface IAttachDocsDao {
    public boolean updateRecord(List<Long> ids, Long empId, String flag);

}
