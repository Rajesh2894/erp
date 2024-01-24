package com.abm.mainet.common.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.dto.ComplaintGrid;
import com.abm.mainet.common.dto.ComplaintTypeBean;
import com.abm.mainet.common.dto.DepartmentComplaintBean;
import com.abm.mainet.common.repository.ComplaintJpaRepository;

/**
 * @author ritesh.patil
 *
 */
@Repository
@Service
@Transactional(readOnly = true)
public class ComplaintTypeServiceImpl implements IComplaintTypeService {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.complaint.business.service.
     * IComplaintService#save(com.abm.mainetservice.web.masters.complaint.bean. DepartmentComplaintBean)
     */

    private static Logger logger = Logger.getLogger(ComplaintTypeServiceImpl.class);

    @Autowired
    private ComplaintJpaRepository complaintJpaRepository;

    @Override
    public List<ComplaintGrid> findAllComplaintRecords(final Long orgId, final String status) {
        logger.info("findAllComplaintRecords(Long orgId) execution starts orgId="
                + orgId);
        final List<Object[]> objList = complaintJpaRepository.findAllCompRecords(
                orgId, status);
        List<ComplaintGrid> complaintGrids = null;
        ComplaintGrid complaintGrid = null;

        if ((objList != null) && !objList.isEmpty()) {
            complaintGrids = new ArrayList<>();
            for (final Object[] obj : objList) {
                complaintGrid = new ComplaintGrid();
                complaintGrid
                        .setDeptCompId(Long.valueOf(String.valueOf(obj[0])));
                complaintGrid.setDeptName(String.valueOf(obj[1]));
                complaintGrid.setDeptNameReg(String.valueOf(obj[2]));
                complaintGrid.setDeptId(Long.parseLong(String.valueOf(obj[3])));
                complaintGrid.setOrgId(Long.parseLong(String.valueOf(obj[4])));
                complaintGrid.setDeptStatus(String.valueOf(obj[5]));
                complaintGrids.add(complaintGrid);
            }
        }
        logger.info("findAllComplaintRecords(Long orgId) execution ends ");
        return complaintGrids;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(final DepartmentComplaintBean departmentComplaintBean) {

        logger.info("save(DepartmentComplaintBean departmentComplaintBean) execution starts ");
        DepartmentComplaint entity = new DepartmentComplaint();
        BeanUtils.copyProperties(departmentComplaintBean, entity,
                MainetConstants.Complaint.ExludeCopyArrayDEPTCOMPL);
        final Department department = new Department();
        department.setDpDeptid(departmentComplaintBean.getDeptId());
        entity.setStatus(MainetConstants.CommonConstants.Y);
        entity.setCreateDate(new Date());
        entity.setDepartment(department);
        final List<ComplaintTypeBean> ownerMasters = departmentComplaintBean
                .getComplaintTypesList();
        final List<ComplaintType> list = new ArrayList<>();
        ComplaintType complaintType;
        for (final ComplaintTypeBean complaintTypeBean : ownerMasters) {
            complaintType = new ComplaintType();
            BeanUtils
                    .copyProperties(
                            complaintTypeBean,
                            complaintType,
                            MainetConstants.Complaint.ExludeCopyArrayCOMPLAINTSUBTYPE);
            complaintType.setCreateDate(new Date());
            complaintType.setCreatedBy(entity.getCreatedBy());
            complaintType.setDepartmentComplaint(entity);
            complaintType.setOrgId(entity.getOrgId());
            complaintType.setStatus(MainetConstants.CommonConstants.Y);
            complaintType.setIsActive(true);
            list.add(complaintType);
        }
        entity.setComplaintTypes(list);
        entity = complaintJpaRepository.save(entity);
        logger.info("save(DepartmentComplaintBean departmentComplaintBean) execution ends ");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public boolean saveEdit(final DepartmentComplaintBean departmentComplaintBean,
            final List<Long> removeChildIds) {
        logger.info("saveEdit(DepartmentComplaintBean departmentComplaintBean,List<Long> removeChildIds) execution starts ");
        DepartmentComplaint entity = new DepartmentComplaint();
        BeanUtils.copyProperties(departmentComplaintBean, entity);
        final Department department = new Department();
        department.setDpDeptid(departmentComplaintBean.getDeptId());
        entity.setDepartment(department);
        entity.setUpdatedDate(new Date());
        final List<ComplaintTypeBean> ownerMasters = departmentComplaintBean
                .getComplaintTypesList();
        final List<ComplaintType> list = new ArrayList<>();
        ComplaintType complaintType;
        for (final ComplaintTypeBean complaintTypeBean : ownerMasters) {
            if (!removeChildIds.contains(complaintTypeBean.getCompId())) {
                complaintType = new ComplaintType();
                BeanUtils.copyProperties(complaintTypeBean, complaintType);
                complaintType.setDepartmentComplaint(entity);
                if (null != complaintTypeBean.getCompId()) {
                    complaintType.setUpdatedDate(new Date());
                    complaintType.setUpdatedBy(entity.getUpdatedBy());
                } else {
                    complaintType.setCreateDate(new Date());
                    complaintType.setCreatedBy(entity.getUpdatedBy());
                    complaintType.setOrgId(entity.getOrgId());
                    complaintType.setStatus("Y");
                }
                list.add(complaintType);
            }
        }
        entity.setComplaintTypes(list);

        entity = complaintJpaRepository.save(entity);
        if (!removeChildIds.isEmpty()) {
            complaintJpaRepository.deleteRecordDetails(MainetConstants.CommonConstants.N, entity.getUpdatedBy(), removeChildIds);
        }

        logger.info("saveEdit(DepartmentComplaintBean departmentComplaintBean,List<Long> removeChildIds) execution ends ");
        return true;

    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.complaint.business.service.
     * IComplaintService#findAllServicesByDepartment(java.lang.Long, java.lang.Long)
     */
    @Override
    public List<ComplaintTypeBean> findAllComplaintsByOrg(Long orgId) {
        logger.info(" findAllComplaintsByOrg(Long orgId, Long orgId) execution starts orgId="
                + orgId);
        List<ComplaintType> complaintTypes = complaintJpaRepository.findAllComplaintsByOrg(orgId);
        List<ComplaintTypeBean> beanList = new ArrayList<ComplaintTypeBean>();
        ComplaintTypeBean bean = null;
        for (ComplaintType complaint : complaintTypes) {
            bean = new ComplaintTypeBean();
            BeanUtils.copyProperties(complaint, bean);
            bean.setDeptId(complaint.getDepartmentComplaint().getDepartment().getDpDeptid());
            bean.setDeptName(complaint.getDepartmentComplaint().getDepartment().getDpDeptdesc());
            beanList.add(bean);
        }
        return beanList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.complaint.business.service.
     * IComplaintService#getDepartmentWithOutComplaint(java.lang.Long, java.lang.String)
     */
    @Override
    public List<Object[]> getNotcomplainedDepartment(final Long orgId,
            final String status) {
        logger.info("getDepartmentWithOutComplaint(Long orgId, String status) execution starts orgId="
                + orgId + " status" + status);
        final List<Object[]> deptList = complaintJpaRepository
                .getNotcomplainedDepartment(Long.valueOf(orgId), status);
        logger.info(" getDepartmentWithOutComplaint(Long orgId, String status) execution ends ");
        return deptList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.complaint.business.service. IComplaintService#findById(java.lang.Long)
     */
    @Override
    public DepartmentComplaintBean findById(final Long compId, int langId) {

        logger.info("findById(Long compId) execution starts compId= " + compId);
        final DepartmentComplaint departmentComplaint = complaintJpaRepository
                .findRecord(compId);
        final DepartmentComplaintBean departmentComplaintBean = new DepartmentComplaintBean();
        if (departmentComplaint != null) {
            final List<ComplaintTypeBean> complaintTypeBeans = new ArrayList<>();
            ComplaintTypeBean complaintTypeBean;
            departmentComplaintBean.setDeptId(departmentComplaint
                    .getDepartment().getDpDeptid());
            departmentComplaintBean.setOrgId(departmentComplaint
                    .getOrgId());
            departmentComplaintBean
                    .setDeptName(langId != MainetConstants.ENGLISH ? departmentComplaint.getDepartment().getDpNameMar()
                            : departmentComplaint.getDepartment().getDpDeptdesc());
            BeanUtils.copyProperties(departmentComplaint, departmentComplaintBean);
            for (final ComplaintType complaintType : departmentComplaint.getComplaintTypes()) {
                complaintTypeBean = new ComplaintTypeBean();
                BeanUtils.copyProperties(complaintType, complaintTypeBean);
                complaintTypeBeans.add(complaintTypeBean);
            }
            departmentComplaintBean.setComplaintTypesList(complaintTypeBeans);
        }
        logger.info("findById(Long compId) execution ends ");
        return departmentComplaintBean;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.complaint.business.service. IComplaintService#getDepartmentsByOrgId(java.lang.Long)
     */
    @Override
    public List<DepartmentComplaint> getcomplainedDepartment(
            final Long orgId) {
        List<DepartmentComplaint> departmnentComplaintsList = new ArrayList<>();
        departmnentComplaintsList = complaintJpaRepository
                .getcomplainedDepartment(orgId);
        return departmnentComplaintsList;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.complaint.business.service.
     * IComplaintService#findDepartmentComplaintByDepartmentId(java.lang.Long)
     */
    @Override
    public DepartmentComplaint findComplainedDepartmentByDeptId(
            final Long depId, final Long orgId) {
        DepartmentComplaint departmnentComplaint = new DepartmentComplaint();
        departmnentComplaint = complaintJpaRepository
                .findDepartmentComplaintByDepartmentId(depId, orgId);
        return departmnentComplaint;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.masters.complaint.business.service. IComplaintService#findComplaintTypeById(java.lang.Long)
     */
    @Override
    public ComplaintType findComplaintTypeById(
            final Long compId) {

        ComplaintType complaintType = new ComplaintType();
        complaintType = complaintJpaRepository.findComplaintTypeById(compId);
        return complaintType;
    }

    /*
     * (non-Javadoc)
     * @see com.abm.mainet.common.service.IComplaintService#isApplicaitonPendingForComplaint(java.lang.Long, java.lang.Long,
     * java.lang.Long)
     */
    @Override
    public boolean isApplicaitonPendingForComplaintType(Long comId) {
    	Integer count = 0;
    	/*D#130421 - check if workflow is active or not for given subtype*/
        count = complaintJpaRepository.countPendingAppForComplaintType(comId); 
        
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isComplaintsActiveForDepartment(Long deptCompId) {
        DepartmentComplaint deartmentComp = complaintJpaRepository.findOne(deptCompId);
        Stream<ComplaintType> filter = deartmentComp.getComplaintTypes().stream().filter(comp -> {
            return comp.getIsActive() && comp.getStatus().equals(MainetConstants.Y_FLAG);
        });
        return filter.findFirst().isPresent();
    }

    @Override
    @Transactional
    public void inactiveComplaintDepartment(Long deptCompId) {
        complaintJpaRepository.inactiveComplaintDepartment(deptCompId);
        complaintJpaRepository.inactiveAllComplaintsByComplaintDepartment(deptCompId);
    }

}
