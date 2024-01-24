package com.abm.mainet.common.ui.model;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.TimeZoneEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLabelDTO;
import com.abm.mainet.cfc.scrutiny.service.ScrutinyService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.AbstractDTO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.master.service.ICommonHelpDocsService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.DateEditor;
import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;

@Component
@Scope(value = "session")
public abstract class AbstractModel implements Serializable, InitializingBean, BeanNameAware {

	private static final long serialVersionUID = 8114991987457434129L;
	protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	private String beanName;
	private BindingResult bindingResult;
	private long apmApplicationId;
	private String resubmissionURL;
	private String filterType;
	private String successMessage;
	private Long taskId;
	private Boolean isDMS;
	private CommonHelpDocs commonHelpDoc;
	private ApplicationFormChallanDTO challanDTO;
	private Map<Long, Map<String, Object>> scrutinyTempDAOObject = new HashMap<>(0);
	private final StandardEvaluationContext standardEvalContext = new StandardEvaluationContext(this);
	private List<MultipartFile> multipartFiles = new ArrayList<>(0);
	private final FileNetApplicationClient fileNetApplicationClient = FileNetApplicationClient.getInstance();
	private boolean download = false;
	private double amountCharged;
	private String filePath;
	private String redirectURL;
	List<String> financialYear = new ArrayList<>(0);
	private String countryDesc;
	private String stateDesc;

	private String talukaDesc;
	private String townDesc;
	private String districtDesc;
	private String eventName;
	private String clientIpAddress;
	private String dmsPath;
	
	private List<CFCAttachment> scrutinyDocs = new ArrayList<>(3);
	 
	private ScrutinyLabelDTO scrutinyLabelDTO;
	 
	private String wokflowDecision;
	    
	private String loiGenFlag;
    
    private String scrutinyDecisionRemark="";
    
    private String forwardToEmployee ="";
    
    private List<String> forwardToDepartment = new ArrayList<>();
    
    private List<String> forwardToServiceId = new ArrayList<>();
    
    private String parentComment;
    
    private List<String> childComment;
	
	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	
	// Added Changes As per told by Rajesh Sir For Sms and Email
	private AbstractDTO abstractDTO;

	private static final String[] IP_HEADER_CANDIDATES = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	private Validator validator;
	
	@Resource
    private ScrutinyService scrutinyService;

	public <T> void validateConstraints(T bean, Class<T> beanClass, BindingResult bindResult) {
		Set<ConstraintViolation<T>> violations = validator.validate(bean);
		if (violations.size() == 0) {
			return;
		}
		for (ConstraintViolation<T> violation : violations) {
			bindResult.addError(new ObjectError(violation.getRootBeanClass().getName(), violation.getMessage()));
		}
	}

	public AbstractDTO getAbstractDTO() {
		return abstractDTO;
	}

	public ScrutinyLabelDTO getScrutinyLabelDTO() {
		return scrutinyLabelDTO;
	}

	public void setScrutinyLabelDTO(ScrutinyLabelDTO scrutinyLabelDTO) {
		this.scrutinyLabelDTO = scrutinyLabelDTO;
	}

	public void setAbstractDTO(AbstractDTO abstractDTO) {
		this.abstractDTO = abstractDTO;
		this.abstractDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	}

	public String getUploadedFiles() {
		final StringBuilder fileNames = new StringBuilder();
		MultipartFile file;

		final ListIterator<MultipartFile> listIterator = multipartFiles.listIterator();

		while (listIterator.hasNext()) {
			file = listIterator.next();

			fileNames.append(file.getOriginalFilename());

			if (listIterator.hasNext()) {
				fileNames.append(MainetConstants.operator.COMMA);
			}
		}

		return fileNames.toString();
	}

	public List<MultipartFile> getMultipartFiles() {
		return multipartFiles;
	}

	public void setMultipartFiles(final List<MultipartFile> multipartFiles) {
		this.multipartFiles = multipartFiles;
	}

	public ApplicationSession getAppSession() {
		return ApplicationSession.getInstance();
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}

	protected void initializeModel() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initializeModel();
	}

	public String getBeanName() {
		return beanName;
	}

	public long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(final long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	@Override
	public void setBeanName(final String beanName) {
		this.beanName = beanName;
	}

	public UserSession getUserSession() {
		return UserSession.getCurrent();
	}

	public boolean hasValidationErrors() {
		return (bindingResult != null) && bindingResult.hasErrors();
	}

	/**
	 * To validate entity bean object by using {@link ConstraintValidator} class.
	 * 
	 * @param entity
	 *            the entity object to be validated.
	 * @param validatorClass
	 *            the validator class which will perform spring validation on given
	 *            entity object.
	 */
	public final <TValidator extends BaseEntityValidator<TEntity>, TEntity extends Object> void validateBean(
			final TEntity entity, final Class<TValidator> validatorClass) {
		if (bindingResult == null) {
			return;
		}

		final TValidator validator = ApplicationContextProvider.getApplicationContext().getBean(validatorClass);
		final Collection<String> errors = validator.isValid(entity);

		for (final String error : errors) {
			bindingResult.addError(new ObjectError(MainetConstants.BLANK, error));
		}
	}

	/**
	 * To get {@link BindingResult} object with validator bean and request object to
	 * perform spring binding operation.
	 * 
	 * @param request
	 *            the {@link HttpServletRequest} object for binding result.
	 * @return {@link BindingResult} object.
	 */
	public BindingResult bind(final HttpServletRequest request) {
		final ServletRequestDataBinder binder = new ServletRequestDataBinder(this,
				MainetConstants.CommonConstants.COMMAND);
		registerCustomEditors(binder);

		binder.bind(request);
		binder.validate();
		setClientIpAddress(request);
		bindingResult = binder.getBindingResult();

		request.setAttribute(MainetConstants.CommonConstants.COMMAND, this);
		request.setAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.CommonConstants.COMMAND, bindingResult);
		return bindingResult;
	}

	public void setClientIpAddress(HttpServletRequest request) {
		
		String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        
		setClientIpAddress(ipAddress);
	}

	public String getClientIpAddress() {
		return clientIpAddress;
	}

	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
	}

	/**
	 * To register custom date editor class with given {@link WebDataBinder} for
	 * perform date validation on {@link BindingResult} object.
	 * 
	 * @param binder
	 *            the {@link WebDataBinder} object for add custom date editor class.
	 */
	public static void registerCustomEditors(final WebDataBinder binder) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(MainetConstants.DATE_HOUR_FORMAT);

		binder.registerCustomEditor(Date.class, null, new DateEditor(dateFormat, true));
		binder.registerCustomEditor(TimeZone.class, new TimeZoneEditor());
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	/**
	 * To prepare {@link JQGridResponse} object.
	 * 
	 * @param httpServletRequest
	 *            the {@link HttpServletRequest} object for binding result.
	 * @param page
	 *            the {@link String} literal containing current page.
	 * @param rows
	 *            the {@link String} literal containing total rows.
	 * @param entityList
	 *            the {@link List} of <code>TEntity</code> object which contains
	 *            actual data to prepare.
	 * @return {@link JQGridResponse} object for given list.
	 */

	public <TEntity extends Serializable> JQGridResponse<TEntity> paginate(final HttpServletRequest httpServletRequest,
			final String page, final String rows, final List<TEntity> objects) {

		final JQGridResponse<TEntity> jqgridResponse = new JQGridResponse<>();

		final int gridPage = Integer.parseInt(page);
		final int gridRows = Integer.parseInt(rows);

		int totalPages = 0;
		int totalCount = 0;

		if (objects != null) {
			totalCount = objects.size();
		}

		if (totalCount > 0) {
			if ((totalCount % gridRows) == 0) {
				totalPages = totalCount / gridRows;
			} else {
				totalPages = (totalCount / gridRows) + 1;
			}
		} else {
			totalPages = 0;
		}

		jqgridResponse.setRows(objects);
		jqgridResponse.setPage(gridPage);
		jqgridResponse.setRecords(objects.size());
		jqgridResponse.setTotal(totalPages);

		return jqgridResponse;
	}

	/**
	 * To get {@link List} of given look up prefix code for hierarchical
	 * lookup&acute;s only.
	 * <p>
	 * In case of if pass prefix code is non-hierarchical then it will return
	 * non-hierarchical object also. So in such case no need to specify whether
	 * given prefix is hierarchical or not.
	 * </p>
	 * 
	 * @param lookUpCode
	 *            the {@link String} literal containing hierarchical lookup prefix
	 *            code.
	 * @return {@link List} list object containing {@link LookUp} objects if found
	 *         otherwise empty list object.
	 */
	public List<LookUp> getLookUpList(final String lookUpCode) {
		final List<LookUp> lookupList = getAppSession()
				.getHierarchicalLookUp(getUserSession().getOrganisation(), lookUpCode).get(lookUpCode);
		if ((lookupList == null) || lookupList.isEmpty()) {
			logger.warn("prefix not found for:[prefix -->" + lookUpCode + "]");
		}
		return lookupList;
	}

	/**
	 * To get {@link List} of given look up prefix code for hierarchical
	 * lookup&acute;s only.
	 * <p>
	 * In case of if pass prefix code is non-hierarchical then it will return
	 * non-hierarchical object also. So in such case no need to specify whether
	 * given prefix is hierarchical or not.
	 * </p>
	 * 
	 * @param lookUpCode
	 *            the {@link String} literal containing hierarchical lookup prefix
	 *            code.
	 * @return {@link List} list object containing {@link LookUp} objects if found
	 *         otherwise empty list object.
	 */
	public List<LookUp> getLookUpList(final String lookUpCode, final Organisation org) {
		final List<LookUp> lookupList = getAppSession().getHierarchicalLookUp(org, lookUpCode).get(lookUpCode);
		if ((lookupList == null) || lookupList.isEmpty()) {
			logger.error("prefix not found for:[prefix -->" + lookUpCode + "]");
		}
		return lookupList;
	}

	public List<LookUp> getCitizenLookUpList(final String lookUpCode) {
		final List<LookUp> lookupList = getAppSession()
				.getHierarchicalLookUp(ApplicationSession.getInstance().getSuperUserOrganization(), lookUpCode)
				.get(lookUpCode);
		if ((lookupList == null) || lookupList.isEmpty()) {
			logger.error("prefix not found for:[prefix -->" + lookUpCode + "]");
		}
		return lookupList;
	}

	/**
	 * To get {@link Map} of given parent prefix code and parent id which containing
	 * deep level child lookUp object&acute;s.
	 * <p>
	 * It is also possible to get data till end level of lookUp object sub
	 * information by passing parent id for those child lookUp object&acute;s.
	 * </p>
	 * 
	 * @param parentCode
	 *            the {@link String} literal containing parent looUp code.
	 * @param parentId
	 *            the {@link Long} literal containing parent id.
	 * @return {@link Map} map object containing <code>key</code> as parent lookUp
	 *         id and <code>value</code> as {@link List} of {@link LookUp} objects
	 *         for given parent id and code if found otherwise empty map object.
	 */
	public List<LookUp> getLookUp(final String parentCode, final long parentId) {
		return getAppSession().getHierarchicalForSubDetails(getUserSession().getOrganisation(), parentCode, parentId);
	}

	public LookUp getNonHierarchicalLookUpObject(final long lookUpId) {
		return getAppSession().getNonHierarchicalLookUp(getUserSession().getOrganisation().getOrgid(), lookUpId);
	}

	public LookUp getHierarchicalLookUpObject(final long lookUpId) {
		return getAppSession().getHierarchicalLookUp(getUserSession().getOrganisation(), lookUpId);
	}

	/**
	 * To get list of lookUp object which containing label information about
	 * particular prefix code.
	 * 
	 * @param prefixCode
	 *            the {@link String} object containing prefix code.
	 * @return {@link List} of {@link LookUp} object containing label information.
	 */
	public List<LookUp> getLookupLabel(final String prefixCode) {
		final List<LookUp> lookUps = getLookUpList(prefixCode);
		if ((lookUps == null) || lookUps.isEmpty()) {
			logger.error("prefix not found for:[prefix -->" + prefixCode + "]");
		}
		return lookUps;
	}

	/**
	 * To get lookUp object containing non-hierarchical object for given prefix
	 * code.
	 * 
	 * @param prefixCode
	 *            the {@link String} object containing prefix code.
	 * @param type
	 *            the {@link String} literal containing type of prefix code.
	 * @return {@link LookUp} object for given prefix code if type is
	 *         non-hierarchical otherwise <code>null</code>.
	 */
	public final LookUp getLookupLabel(final String prefixCode, final String type) {
		if ((type == null) || type.equals(MainetConstants.MENU.N)) {
			return this.getLookupLabel(prefixCode).get(0);
		}
		return null;
	}

	/******************
	 * add by umashanker for Web service
	 **************************************/
	/*
	 * public List<LookUp> getWSLookupLabel(final String prefixCode, final
	 * Organisation organisation) { return this.getWSLookUpList(prefixCode,
	 * organisation); } public List<LookUp> getWSLookUpList(final String lookUpCode,
	 * final Organisation organisation) { return getWSLookUp(lookUpCode,
	 * organisation).get(lookUpCode); } private Map<String, List<LookUp>>
	 * getWSLookUp(final String lookUpCode, final Organisation organisation) {
	 * return getAppSession().getHierarchicalLookUp(organisation, lookUpCode); }
	 */

	/*****************************************************************************************************/
	/*
	 * public List<LookUp> getWSLookUpList(final String parentCode, final String
	 * childCode, final Organisation organisation) { return getWSLookUp(parentCode,
	 * childCode, organisation).get(childCode); } private Map<String, List<LookUp>>
	 * getWSLookUp(final String parentCode, final String childCode, final
	 * Organisation organisation) { return
	 * getAppSession().getHierarchicalForDetails(organisation, parentCode,
	 * childCode); }
	 */
	/******************************************************************************/

	/*
	 * public List<LookUp> getWSLookUp(final String parentCode, final long parentId,
	 * final Organisation organisation) { return
	 * getAppSession().getHierarchicalForSubDetails(organisation, parentCode,
	 * parentId); }
	 */
	/**
	 * To get level data for non-hierarchical prefix only.
	 * 
	 * @param prefixCode
	 *            the non-hierarchical prefix code.
	 * @return {@link List} of lookUp object.
	 */
	public final List<LookUp> getLevelData(final String prefixCode) {
		return getLookUpList(prefixCode);
	}

	public final List<LookUp> getLevelData(final String prefixCode, final Organisation org) {
		return getLookUpList(prefixCode, org);
	}

	public final List<LookUp> getSortedLevelData(final String prefixCode) {
		final List<LookUp> list = getLevelData(prefixCode);
		if ((null != list) && !list.isEmpty()) {
			Collections.sort(list);
		}
		return list;

	}

	public final List<LookUp> getSortedLevelData(final String prefixCode, final int level) {
		final List<LookUp> list = getLevelData(prefixCode, level);
		if ((null != list) && !list.isEmpty()) {
			Collections.sort(list);
		}
		return list;

	}

	/**
	 * To get any level data i.e, from 1<sup>st</sup> to 5<sup>th</sup> level for
	 * hierarchical prefix only.
	 * 
	 * @param prefixCode
	 *            the hierarchical prefix code.
	 * @param level
	 * @return {@link List} of {@link LookUp} object if level is from 1 to 5
	 *         otherwise empty list.
	 */
	public List<LookUp> getLevelData(final String prefixCode, final int level) {
		List<LookUp> lookUps = null;
		if (level <= 0) {
			lookUps = new ArrayList<>();
		} else if (level == 1) {
			lookUps = getAppSession().getLookUpsByLevel(UserSession.getCurrent().getOrganisation().getOrgid(),
					prefixCode, level);
		} else {
			final String propertyPathPrefix = findPropertyPathPrefix(prefixCode);
			Long parentId = null;

			if (propertyPathPrefix != null) {
				parentId = evaluateExpression(propertyPathPrefix + String.valueOf(level - 1), Long.class);
			}
			if (parentId == null) {
				lookUps = new ArrayList<>(0);
			} else {
				lookUps = getAppSession().getChildLookUpsFromParentId(parentId);
			}
		}
		if ((lookUps == null) || lookUps.isEmpty()) {
			logger.error("prefix not found for:[prefix -->" + prefixCode + " and level " + level + "]");
		}
		return lookUps;
	}

	public final List<LookUp> getSecondLevelData(final String prefixCode, final int level) {
		if (level <= 0) {
			return new ArrayList<>();
		}

		else {

			return getAppSession().getLookUpsByLevel(getUserSession().getOrganisation().getOrgid(), prefixCode, level);

		}
	}

	/******* add by umashanker for start get Level data for Web Service ********/
	/*
	 * public final List<LookUp> getWSLevelData(final String prefixCode, final int
	 * level, final Organisation organisation) { if (level <= 0) { return new
	 * ArrayList<>(); } else if (level == 1) { final LookUp lookUp =
	 * getWSLookupLabel(prefixCode, organisation).get(0); return
	 * getWSLookUpList(prefixCode, lookUp.getLookUpCode(), organisation); } else {
	 * final String propertyPathPrefix = findPropertyPathPrefix(prefixCode); Long
	 * parentId = null; if (propertyPathPrefix != null) { parentId =
	 * evaluateExpression(propertyPathPrefix + String.valueOf(level - 1),
	 * Long.class); } final LookUp parentObject =
	 * getHierarchicalLookUpObject(parentId != null ? parentId : 0); return
	 * getWSLookUp(parentObject.getLookUpCode(), parentObject.getLookUpId(),
	 * organisation); } }
	 */

	/******* end for getleveldata for Web Service ********/
	/**
	 * To get evaluate given expression string value using spring parser to get
	 * {@link Expression} object.
	 * 
	 * @param expressionString
	 *            the {@link String} literal containing expression to be evaluated.
	 * @param desiredResultType
	 *            the {@link Class} type which is actual return type of expression
	 *            object.
	 * @return {@link Expression} object for the given expression string.
	 */
	private final <T> T evaluateExpression(final String expressionString, final Class<T> desiredResultType) {
		final ExpressionParser parser = new SpelExpressionParser();
		return parser.parseExpression(expressionString).getValue(standardEvalContext, desiredResultType);
	}

	/**
	 * To get bean property path expression conjunction with entity name for the
	 * given prefix code.
	 * <p>
	 * Each model class need to be implement this method to get appropriate property
	 * path otherwise will get <code>null</code> value.
	 * </p>
	 * 
	 * @param parentCode
	 *            the {@link String} literal containing prefix code.
	 * @return {@link String} Property path prefix for passed parent code. E.g.
	 *         "ChargeMaster.CmInfoType", "ChargeMaster.CmChrgType";
	 */
	protected String findPropertyPathPrefix(final String parentCode) {
		return null;
	}

	/**
	 * To iterate given lookUp list and find matching parent id to get child lookUp
	 * object list.
	 * 
	 * @param parentList
	 *            the {@link List} object containing parent lookUp list.
	 * @param parentId
	 *            the {@link Long} literal containing parent identifier.
	 * @return {@link List} list of {@link LookUp} object containing child for given
	 *         parent identifier.
	 */
	protected List<LookUp> getLookUps(final List<LookUp> parentList, final Long parentId) {
		if ((parentId != null) && (parentId > 0) && (parentList != null)) {
			for (final LookUp lookUp : parentList) {
				if (lookUp.getLookUpId() == parentId) {
					return this.getLookUp(lookUp.getLookUpCode(), lookUp.getLookUpId());
				}
			}
		}

		return new ArrayList<>();
	}

	/**
	 * To initialize bean property values with some default values for each property
	 * of entity for given prefix code.
	 * 
	 * @param parentCode
	 *            the {@link String} literal containing prefix code.
	 */
	protected final void initializeLookupFields(final String parentCode) {
		boolean foundProperty = false;
		int level = 1;
		String propertyName;

		final BeanWrapper wrapper = new BeanWrapperImpl(this);
		final String propertyPrefix = findPropertyPathPrefix(parentCode);

		do {
			propertyName = propertyPrefix + String.valueOf(level);
			foundProperty = wrapper.isWritableProperty(propertyName);

			if (foundProperty) {
				final LookUp lookUp = getDefaultLookup(parentCode, level);

				if (lookUp != null) {
					wrapper.setPropertyValue(propertyName, lookUp.getLookUpId());
				}

				++level;
			}
		} while (foundProperty);
	}

	/**
	 * To get default lookUp object which has default value as 'Y' among the list of
	 * {@link LookUp} list for the given prefix code.
	 * 
	 * @param prefixCode
	 *            the {@link String} literal containing prefix code.
	 * @param level
	 *            the current level.
	 * @return {@link LookUp} object which has default value as 'Y' if found
	 *         otherwise <code>null</code>
	 */
	private final LookUp getDefaultLookup(final String prefixCode, final int level) {
		final List<LookUp> data = getLevelData(prefixCode, level);

		if (data != null) {
			for (final LookUp lookUp : data) {
				if (MainetConstants.MENU.Y.equalsIgnoreCase(lookUp.getDefaultVal())) {
					return lookUp;
				}
			}
		}

		return null;
	}

	/**
	 * To get TEntity object from the entity list for the the given rowId.
	 * 
	 * @param list
	 *            the {@link List} of entity.
	 * @param rowId
	 *            the long literal containing row id value.
	 * @return TEntity object if found for given row id otherwise <code>null</code>
	 */
	public final <TEntity extends BaseEntity> TEntity findEntity(final Collection<TEntity> list, final long rowId) {
		if (list == null) {
			throw new NullArgumentException(MainetConstants.COMMON_ENTITY_FIELD_CONSTANT.LIST);
		}

		for (final TEntity entity : list) {
			if (entity.getRowId() == rowId) {
				return entity;
			}
		}

		throw new FrameworkException("Unable to find entity with rowId " + rowId + " in the passed collection of "
				+ list.getClass().getGenericSuperclass());
	}

	/**
	 * Copy source entity object to given list of entity.
	 * 
	 * @param list
	 *            the {@link List} of entity objects.
	 * @param source
	 *            the TEntity object which is to be copy.
	 */
	public static final <TEntity extends BaseEntity> void replaceEntity(final List<TEntity> list,
			final TEntity source) {
		final ListIterator<TEntity> listIterator = list.listIterator();
		TEntity entity;

		while (listIterator.hasNext()) {
			entity = listIterator.next();

			if (entity.getRowId() == source.getRowId()) {
				listIterator.set(source);
				break;
			}
		}
	}

	public void addValidationError(final String error) {
		if (bindingResult != null) {
			bindingResult.addError(new ObjectError(MainetConstants.BLANK, error));
		}
	}

	/**
	 * To get empty collection list.
	 * 
	 * @return {@link List} object with empty.
	 */
	public final List<? extends Object> emptyList() {
		return Collections.emptyList();
	}

	/**
	 * @Method To get ULB name from super organization.
	 * @return {@link LookUp}
	 */
	public LookUp getULBName() {
		final Organisation organisation = getUserSession().getOrganisation();
		final LookUp lookUp = new LookUp();

		if (organisation != null) {
			lookUp.setDescLangFirst(organisation.getONlsOrgname());
			lookUp.setDescLangSecond(organisation.getONlsOrgnameMar());
		}

		return lookUp;
	}

	/**
	 * @Method To get header logs details from "TIM" prefix.
	 * @return List of {@link LookUp} for Tool Bar Image
	 */
	public List<LookUp> getHeaderDetails() {
		return Collections.emptyList(); // getLookUpList(PrefixConstants.LookUp.TOOL_BAR_IMAGE);
	}

	public List<DepartmentLookUp> getDepartmentLookUp() {
		final List<DepartmentLookUp> departmentllokup = getAppSession()
				.getDepartments(UserSession.getCurrent().getOrganisation());
		if ((departmentllokup != null) && !departmentllokup.isEmpty()) {
			Collections.sort(departmentllokup);
		}
		return departmentllokup;

	}

	/**
	 * To get {@link DepartmentLookUp} object which contains department information.
	 * 
	 * @param dpetId
	 *            the long literal containing department identifier.
	 * @param organisation
	 *            the {@link Organisation} object.
	 * @return {@link DepartmentLookUp} object for given department identifier.
	 */
	public DepartmentLookUp getDepartmentLookUp(final long deptId) {
		return getAppSession().getDepartment(deptId, UserSession.getCurrent().getOrganisation());
	}

	/**
	 * To perform file upload related action using given {@link MultipartFile}
	 * objects.
	 * <p>
	 * Also provide facility to do other action.
	 * </p>
	 * 
	 * @param multipartFiles
	 *            the {@link List} containing {@link MultipartFile} objects.
	 */
	public void doUploading() throws Exception {
		// TODO Perform upload specific action as per the requirement.
		// Need to override this method where the uploading of document is
		// perform.
	}

	/**
	 * To perform user authorization validation.
	 * 
	 * @param rowId
	 * @param rowId
	 * @return <code>true</code> if access by valid user otherwise
	 *         <code>false</code>.
	 */
	public final boolean doAuthorization(final long rowId) {
		return hasAccess(rowId);
	}

	/**
	 * To check whether use has access to update the link information.
	 * <p>
	 * The model class need to implement this method to perform valid operation for
	 * the link.
	 * </p>
	 * By default method return <code>true</code>.
	 * 
	 * @return <code>true</code> if has valid right access otherwise
	 *         <code>false</code>.
	 */
	public boolean hasAccess(final long rowId) {
		return true;
	}

	public Employee getNoUserEmployee() {
		final String loginName = getAppSession().getMessage("citizen.noUser.loginName");
		final Organisation organisation = getUserSession().getOrganisation();
		return getEmployeeByLoginName(loginName, organisation);
	}

	public Employee getEmployeeByLoginName(final String emploginname, final Organisation organisation) {
		return iEmployeeService.getEmployeeByLoginName(emploginname, organisation, MainetConstants.IsDeleted.ZERO);
	}

	/**
	 *
	 * @param type
	 * @param str
	 * @return
	 */
	public String getDigest(final String type, final String str) {

		final byte[] hashseq = str.getBytes();
		final StringBuffer hexString = new StringBuffer();
		try {
			final MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			final byte messageDigest[] = algorithm.digest();
			String hex;

			for (final byte element : messageDigest) {
				hex = Integer.toHexString(0xFF & element);
				if (hex.length() == 1) {
					hexString.append(MainetConstants.Common_Constant.Isdeleted);
				}
				hexString.append(hex);
			}
		} catch (final NoSuchAlgorithmException nsae) {
			logger.error(nsae);
		}

		return hexString.toString();
	}

	public String getActiveClass() {
		return MainetConstants.BLANK;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(final String filePath) {
		this.filePath = filePath;
	}

	public String getResubmissionURL() {
		return resubmissionURL;
	}

	public void setResubmissionURL(final String resubmissionURL) {
		this.resubmissionURL = resubmissionURL;
	}

	/**
	 * this is a common method used to populate service related data, this method
	 * must be overridden if scrutiny process is required for respective service.
	 * override this method in respective Service Model and set all application
	 * related data to the model which are mapped to jsp.
	 * 
	 * @param applicationId
	 */
	public void populateApplicationData(final long applicationId) {
		// must be overridden by Service implementor
		throw new FrameworkException(
				"populateApplicationData() method is not overridden in SubClass " + this.getClass());
	}

	public LookUp getLookupByValueAndPrefix(final String value, final String prefix) {
		List<LookUp> lookups = CommonMasterUtility.getLookUps(prefix, UserSession.getCurrent().getOrganisation());

		if (lookups != null) {
			for (final LookUp lookUp : lookups) {
				if (lookUp.getLookUpCode().equals(value)) {
					return lookUp;
				}
			}
		}

		return null;
	}

	public boolean isCitizen() {

		if (UserSession.getCurrent().getEmployee().getEmplType() != null) {
			for (final LookUp lookUp : CommonMasterUtility.getLookUps(PrefixConstants.NEC.PARENT,
					UserSession.getCurrent().getOrganisation())) {
				if (lookUp.getLookUpCode().equals(PrefixConstants.NEC.CITIZEN)
						&& (lookUp.getLookUpId() == UserSession.getCurrent().getEmployee().getEmplType())) {
					return true;
				}
			}
		} else {
			return false;
		}

		return false;
	}

	/**
	 * @return Short Code for UserType who is logged in Use this method in order to
	 *         find out type of user which is logged in. like whether it is Citizen
	 *         or any Agency or the Department Login
	 *
	 */

	public String getLoggedInUserType() {

		String loggedInType = MainetConstants.BLANK;
		final List<LookUp> lookUps = getLookUpList(PrefixConstants.NEC.PARENT);
		final List<LookUp> citizenlookUps = getCitizenLookUpList(PrefixConstants.NEC.PARENT);
		boolean matchfound = false;

		for (final LookUp lookUp : citizenlookUps) {
			if ((UserSession.getCurrent().getEmployee().getEmplType() != null)
					&& (UserSession.getCurrent().getEmployee().getEmplType() == lookUp.getLookUpId())) {
				loggedInType = lookUp.getLookUpCode();
				matchfound = true;
				break;
			}
		}

		if (!matchfound) {
			for (final LookUp lookUp : lookUps) {
				if ((UserSession.getCurrent().getEmployee().getEmplType() != null)
						&& (UserSession.getCurrent().getEmployee().getEmplType() == lookUp.getLookUpId())) {
					loggedInType = lookUp.getLookUpCode();
					break;
				}
			}

		}
		return loggedInType;
	}

	public void viewResubmissionApplication(final long applicationId, final List<String> menuParams) {
		if ((menuParams != null) && !menuParams.isEmpty()) {
			setResubmissionURL(menuParams.get(0));
		}
	}

	public void getDataForResubmission(final long applId, final String URl) {

	}

	public boolean isDownload() {
		return download;
	}

	public void setDownload(final boolean download) {
		this.download = download;
	}

	public Map<Long, Map<String, Object>> getScrutinyTempDAOObject() {
		return scrutinyTempDAOObject;
	}

	public void setScrutinyTempDAOObject(final Map<Long, Map<String, Object>> scrutinyTempDAOObject) {
		this.scrutinyTempDAOObject = scrutinyTempDAOObject;
	}

	public double getAmountCharged() {
		return amountCharged;
	}

	public void setAmountCharged(final double amountCharged) {
		this.amountCharged = amountCharged;
	}

	public final FileNetApplicationClient getFileNetClient() {
		return fileNetApplicationClient;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(final String filterType) {
		this.filterType = filterType;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(final String successMessage) {
		this.successMessage = successMessage;
	}

	/**
	 * @return the cfcApplicationAddress
	 */

	public String getRedirectURL() {
		return redirectURL;
	}

	public void setRedirectURL(final String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String encryptData(final String text) throws IllegalBlockSizeException {
		return EncryptionAndDecryption.encrypt(text);
	}

	public String decryptData(final String text) throws IllegalBlockSizeException {
		return EncryptionAndDecryption.decrypt(text);
	}

	public String getCountryDesc() {
		return countryDesc;
	}

	public void setCountryDesc(final String countryDesc) {
		this.countryDesc = countryDesc;
	}

	public String getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(final String stateDesc) {
		this.stateDesc = stateDesc;
	}

	public String getTalukaDesc() {
		return talukaDesc;
	}

	public void setTalukaDesc(final String talukaDesc) {
		this.talukaDesc = talukaDesc;
	}

	public String getDistrictDesc() {
		return districtDesc;
	}

	public void setDistrictDesc(final String districtDesc) {
		this.districtDesc = districtDesc;
	}

	public String getTownDesc() {
		return townDesc;
	}

	public void setTownDesc(final String townDesc) {
		this.townDesc = townDesc;
	}

	public final List<LookUp> getAlphaNumericSortedLevelData(final String prefixCode, final int level) {

		List<LookUp> list = null;
		if (level <= 0) {
			list = new ArrayList<>();
		} else if (level == 1) {
			list = getAppSession().getLookUpsByLevel(UserSession.getCurrent().getOrganisation().getOrgid(), prefixCode,
					level);

		} else {
			final String propertyPathPrefix = findPropertyPathPrefix(prefixCode);
			;
			Long parentId = null;

			if (propertyPathPrefix != null) {
				parentId = evaluateExpression(propertyPathPrefix + String.valueOf(level - 1), Long.class);
			}

			if (parentId != null) {
				list = getAppSession().getChildLookUpsFromParentId(parentId);
			}
		}

		if (list == null) {
			list = new ArrayList<>(0);
		}
		if (!list.isEmpty()) {
			Collections.sort(list, LookUp.alphanumericComparator);

		}
		return list;
	}

	/*
	 * used in lookupFieldSet
	 */
	public final List<LookUp> getAlphaNumericSortedLevelData(final String prefixCode, final int level,
			final String pathPrefix) {

		List<LookUp> list = null;
		if (level <= 0) {
			list = new ArrayList<>();
		} else if (level == 1) {
			list = getAppSession().getLookUpsByLevel(UserSession.getCurrent().getOrganisation().getOrgid(), prefixCode,
					level);

		} else {
			final String propertyPathPrefix = pathPrefix;
			Long parentId = null;

			if (propertyPathPrefix != null) {
				parentId = evaluateExpression(propertyPathPrefix + String.valueOf(level - 1), Long.class);
			}

			if (parentId != null) {
				list = getAppSession().getChildLookUpsFromParentId(parentId);
			}
		}

		if (list == null) {
			list = new ArrayList<>(0);
		}
		if (!list.isEmpty()) {
			Collections.sort(list, LookUp.alphanumericComparator);

		}
		return list;
	}

	public ApplicationFormChallanDTO getChallanDTO() {
		return challanDTO;
	}

	public void setChallanDTO(final ApplicationFormChallanDTO challanDTO) {
		this.challanDTO = challanDTO;
	}

	public List<LookUp> getServiceLookUpList(final String lookUpCode) {
		return CommonMasterUtility.getListLookup(lookUpCode, getUserSession().getOrganisation());
	}

	public final List<LookUp> getServiceLevelData(final String prefixCode) {
		return getServiceLookUpList(prefixCode);
	}

	public final List<LookUp> getServiceSortedLevelData(final String prefixCode) {
		final List<LookUp> list = getServiceLookUpList(prefixCode);
		if ((null != list) && !list.isEmpty()) {
			Collections.sort(list);
		}
		return list;

	}

	public String getConnectioNo(final long applicationId, final long serviceId) {
		// must be overridden by Service implementor
		throw new FrameworkException("getConnectioNo() method is not overridden in SubClass " + this.getClass());
	}

	public PagingDTO createPagingDTO(final HttpServletRequest request) {

		final PagingDTO pagingDTO = new PagingDTO();

		final String page = request.getParameter(MainetConstants.CommonConstants.PAGE);
		final String rows = request.getParameter(MainetConstants.CommonConstants.ROWS);
		final String sidx = request.getParameter(MainetConstants.SIDX);
		final String sord = request.getParameter(MainetConstants.SORD);

		if ((page != null) && !page.isEmpty()) {
			pagingDTO.setPage(Integer.valueOf(page));
		}
		if ((rows != null) && !rows.isEmpty()) {
			pagingDTO.setRows(Integer.valueOf(rows));
		}
		if ((sidx != null) && !sidx.isEmpty()) {
			pagingDTO.setSidx(sidx);
		}
		if ((sord != null) && !sord.isEmpty()) {
			pagingDTO.setSord(sord);
		}
		return pagingDTO;
	}

	public GridSearchDTO createGridSearchDTO(final HttpServletRequest request) {

		final GridSearchDTO gridSearchDTO = new GridSearchDTO();

		final String searchField = request.getParameter(MainetConstants.SEARCH_FIELD);
		final String searchOper = request.getParameter(MainetConstants.SEARCH_OPER);
		final String searchString = request.getParameter(MainetConstants.SEARCH_STRING);

		if ((searchField != null) && !searchField.isEmpty()) {
			gridSearchDTO.setSearchField(searchField);
		}
		if ((searchOper != null) && !searchOper.isEmpty()) {
			gridSearchDTO.setSearchOper(searchOper);
		}
		if ((searchString != null) && !searchString.isEmpty()) {
			gridSearchDTO.setSearchString(searchString);
		}
		return gridSearchDTO;
	}

	public <TEntity extends Serializable> JQGridResponse<TEntity> paginate(final HttpServletRequest httpServletRequest,
			final String page, final String rows, final int count, final List<TEntity> objects) {

		final JQGridResponse<TEntity> jqgridResponse = new JQGridResponse<>();

		final int gridPage = Integer.parseInt(page);
		final int gridRows = Integer.parseInt(rows);

		int totalPages = 0;
		int totalCount = 0;

		if (objects != null) {
			totalCount = count;
		}

		if (totalCount > 0) {
			if ((totalCount % gridRows) == 0) {
				totalPages = totalCount / gridRows;
			} else {
				totalPages = (totalCount / gridRows) + 1;
			}
		} else {
			totalPages = 0;
		}

		jqgridResponse.setRows(objects);
		jqgridResponse.setPage(gridPage);
		jqgridResponse.setRecords(count);
		jqgridResponse.setTotal(totalPages);

		return jqgridResponse;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Boolean getIsDMS() {
		if (MainetConstants.Common_Constant.YES
				.equals(ApplicationSession.getInstance().getMessage("dms.configure"))) {
			return true;
		} else {
			return false;
		}
	}

	public void setCommonHelpDocs(final String url) {
		ICommonHelpDocsService commonHelpDocsService = ApplicationContextProvider.getApplicationContext()
				.getBean(ICommonHelpDocsService.class);
		this.setCommonHelpDoc(
				commonHelpDocsService.getUploadedFileByDept(url, UserSession.getCurrent().getOrganisation()));

	}

	public CommonHelpDocs getCommonHelpDoc() {
		return commonHelpDoc;
	}

	public void setCommonHelpDoc(CommonHelpDocs commonHelpDoc) {
		this.commonHelpDoc = commonHelpDoc;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	/**
	 * This method have been used for showing offline payment mode based on prefix
	 * value Y or N of OPF under COS prefix. if prefix value will be Y offline mode
	 * will be visible.
	 * 
	 * @return
	 */
	public String getOfflinePayModeByPrefix() {
		String defaultOfflineMode = MainetConstants.Y_FLAG;
		LookUp lookup = getLookupByValueAndPrefix(PrefixConstants.OFFLINE_MODE.OFFLINE_MODE,
				PrefixConstants.OFFLINE_MODE.PAYMENT_MODE);
		if (lookup != null) {
			defaultOfflineMode = lookup.getOtherField();
		}
		return defaultOfflineMode;
	}
	
	 public void populateScrutinyViewData(final long applicationId, final long serviceId, final UserSession userSession) {

	        this.getScrutinyDocs().clear();;
	        final Long gmId = UserSession.getCurrent().getEmployee().getGmid();
	        Long currentLevel=iWorkflowTaskService.findByTaskId(this.taskId).getCurrentEscalationLevel();
	        setScrutinyLabelDTO(scrutinyService.populateScrutinyLabelData(applicationId,
	                UserSession.getCurrent().getEmployee().getEmpId(), gmId, UserSession.getCurrent().getOrganisation().getOrgid(),
	                serviceId, UserSession.getCurrent().getLanguageId(), UserSession.getCurrent().getScrutinyCommonParamMap().get(MainetConstants.SCRUTINY_COMMON_PARAM.REFERENCE_ID),currentLevel));
	        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.FINYEAR,
	                getUserSession().getFinancialPeriodShortForm());
	        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.LANGID,
	                getUserSession().getLanguageId() + MainetConstants.BLANK);
	        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.SCRUTINY_COMMON_PARAM.USERID,
	                getUserSession().getEmployee().getEmpId() + MainetConstants.BLANK);
	        getUserSession().getScrutinyCommonParamMap().put(MainetConstants.ORGID,
	                UserSession.getCurrent().getOrganisation().getOrgid() + MainetConstants.BLANK);
	        getScrutinyLabelDTO().setTaskId(Long.valueOf(getUserSession().getScrutinyCommonParamMap().get(MainetConstants.SCRUTINY_COMMON_PARAM.TASK_ID)));
	       // getUserSession().setScrutinyLabelDTO(getScrutinyLabelDTO());
	        
	    }
	
	public String getDmsPath() {
		return dmsPath;
	}

	public void setDmsPath(String dmsPath) {
		this.dmsPath = dmsPath;
	}

	public List<CFCAttachment> getScrutinyDocs() {
		return scrutinyDocs;
	}

	public void setScrutinyDocs(List<CFCAttachment> scrutinyDocs) {
		this.scrutinyDocs = scrutinyDocs;
	}

	public String getWokflowDecision() {
		return wokflowDecision;
	}

	public void setWokflowDecision(String wokflowDecision) {
		this.wokflowDecision = wokflowDecision;
	}

	public String getLoiGenFlag() {
		return loiGenFlag;
	}

	public void setLoiGenFlag(String loiGenFlag) {
		this.loiGenFlag = loiGenFlag;
	}

	public String getScrutinyDecisionRemark() {
		return scrutinyDecisionRemark;
	}

	public void setScrutinyDecisionRemark(String scrutinyDecisionRemark) {
		this.scrutinyDecisionRemark = scrutinyDecisionRemark;
	}

	public String getForwardToEmployee() {
		return forwardToEmployee;
	}

	public void setForwardToEmployee(String forwardToEmployee) {
		this.forwardToEmployee = forwardToEmployee;
	}

	public List<String> getForwardToDepartment() {
		return forwardToDepartment;
	}

	public void setForwardToDepartment(List<String> forwardToDepartment) {
		this.forwardToDepartment = forwardToDepartment;
	}

	public List<String> getForwardToServiceId() {
		return forwardToServiceId;
	}

	public void setForwardToServiceId(List<String> forwardToServiceId) {
		this.forwardToServiceId = forwardToServiceId;
	}

	public String getParentComment() {
		return parentComment;
	}

	public void setParentComment(String parentComment) {
		this.parentComment = parentComment;
	}

	public List<String> getChildComment() {
		return childComment;
	}

	public void setChildComment(List<String> childComment) {
		this.childComment = childComment;
	}

}
