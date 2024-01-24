package com.abm.mainet.intranet.dto.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class IntranetDTO implements Serializable{
	
private static final long serialVersionUID = 2110704719241314572L;

    private Long inId;
	private String docName;
	private String docDesc;
	private Long deptId;
	private Long docCateType;
	private Date docFromDate;
	private Date DocToDate;
	private Long docCatOrder;
	private Long docOrderNo;
	private String docStatus;
	
	private String deptDesc;
	private String docCatDesc;
	private String atdFname;
	private String atdPath;
	private String saveMode;

	List<DocumentDetailsVO> docList = new ArrayList<>();
	private List<AttachDocs> fetchIntDocList = new ArrayList<>();
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocDesc() {
		return docDesc;
	}
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getDocCateType() {
		return docCateType;
	}
	public void setDocCateType(Long docCateType) {
		this.docCateType = docCateType;
	}
	public Date getDocFromDate() {
		return docFromDate;
	}
	public void setDocFromDate(Date docFromDate) {
		this.docFromDate = docFromDate;
	}
	public Date getDocToDate() {
		return DocToDate;
	}
	public void setDocToDate(Date docToDate) {
		DocToDate = docToDate;
	}
	public Long getDocCatOrder() {
		return docCatOrder;
	}
	public void setDocCatOrder(Long docCatOrder) {
		this.docCatOrder = docCatOrder;
	}
	public Long getDocOrderNo() {
		return docOrderNo;
	}
	public void setDocOrderNo(Long docOrderNo) {
		this.docOrderNo = docOrderNo;
	}
	public String getDocStatus() {
		return docStatus;
	}
	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}
	public List<DocumentDetailsVO> getDocList() {
		return docList;
	}
	public void setDocList(List<DocumentDetailsVO> docList) {
		this.docList = docList;
	}
	public Long getInId() {
		return inId;
	}
	public void setInId(Long inId) {
		this.inId = inId;
	}
	public List<AttachDocs> getFetchIntDocList() {
		return fetchIntDocList;
	}
	public void setFetchIntDocList(List<AttachDocs> fetchIntDocList) {
		this.fetchIntDocList = fetchIntDocList;
	}
	public String getAtdFname() {
		return atdFname;
	}
	public void setAtdFname(String atdFname) {
		this.atdFname = atdFname;
	}
	public String getAtdPath() {
		return atdPath;
	}
	public void setAtdPath(String atdPath) {
		this.atdPath = atdPath;
	}
	public String getDeptDesc() {
		return deptDesc;
	}
	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}
	public String getDocCatDesc() {
		return docCatDesc;
	}
	public void setDocCatDesc(String docCatDesc) {
		this.docCatDesc = docCatDesc;
	}
	public String getSaveMode() {
		return saveMode;
	}
	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}
	
	
}
