package com.abm.mainet.cms.service;

import java.util.List;

import org.springframework.security.web.csrf.CsrfToken;

import com.abm.mainet.cms.domain.VIEWQuickLink;
import com.abm.mainet.cms.dto.VIEWQuickLinkDTO;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author pabitra.raulo
 *
 */
public interface IVIEWQuickLinkService {

    List<VIEWQuickLink> getQuickLinkList(Organisation org);

    String getQuickLink(Organisation org, CsrfToken token, int languageId);

    String getQuickLinkforSamrtcity(Organisation org, CsrfToken token);

	VIEWQuickLinkDTO getQuickLinkListData(Organisation org);

	List<VIEWQuickLink> getQuickLinkListSuda(Organisation org);

}
