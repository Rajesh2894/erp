package com.abm.mainet.common.integration.metrology.service;



import java.net.URI;
import java.nio.charset.Charset;

import javax.jws.WebService;
import javax.ws.rs.Path;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;

import io.swagger.annotations.Api;

/**
 * @author rajesh.das
 * @Since 12-dec-2021
 */
@WebService(endpointInterface = "com.abm.mainet.common.integration.metrology.service.IMetrologyApisService")
@Api(value = "/imetrologyservice")
@Path("/imetrologyservice")
@Service
public class MetrologyApisServiceImpl implements IMetrologyApisService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MetrologyApisServiceImpl.class);

	@Override
	public Object getNowCastDistrictDataById(Long id) throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.nowcast.dist.data.id") + id;
		LOGGER.info("url  " + url);
		try {
			final Object response =getMetrologyData(url);

			if (response != null) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();
		}
		return MainetConstants.AssetManagement.THROW_EX;
	}

	@Override
	public Object getAllDistrictNowCastWarning() throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.nowcast.dist.data");
		LOGGER.info("url  " + url);
		try {
			final Object response =getMetrologyData(url);

			if ((response != null)) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();
		}
		return MainetConstants.AssetManagement.THROW_EX;
	}

	@Override
	public Object getWeatherForecastForSevenDaysById(Long id) throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.weather.forcast.day.id") + id;
		LOGGER.info("url  " + url);
		
		try {
			final Object response = getMetrologyData(url);

			if (response != null) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();
		}
		return MainetConstants.AssetManagement.THROW_EX;
	}

	@Override
	public Object getAllWeatherForecastForSevenDays() throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.weather.forcast.day.all");
		LOGGER.info("url  " + url);
		try {
			final Object response =getMetrologyData(url);

			if ((response != null)) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();
		}
		return MainetConstants.AssetManagement.THROW_EX;

	}

	@Override
	public Object getDistrictWiseWarningById(String id) throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.dist.wise.warning") + id;
		LOGGER.info("url  " + url);
		try {
			final Object response =getMetrologyData(url);

			if (response != null) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();
		}
		return MainetConstants.AssetManagement.THROW_EX;
	}

	@Override
	public Object getStationWiseNowCastDataById(String id) throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.nowcast.station.wise.data") + id;
		LOGGER.info("url  for getStationWiseNowCastDataById" + url);
		try {
			final Object response =getMetrologyData(url);

			if (response != null) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();
		}
		return MainetConstants.AssetManagement.THROW_EX;
	}

	@Override
	public Object getCurrentWeatherDataByStationId(String id) throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.current.weather.station.wise.data")
				+ id;
		LOGGER.info("url  for getCurrentWeatherDataByStationId" + url);
		try {
			final Object response =getMetrologyData(url);

			if (response != null) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();
		}
		return MainetConstants.AssetManagement.THROW_EX;
	}

	@Override
	public Object getCurrentWeatherData() throws Exception {
		String url = ApplicationSession.getInstance().getMessage("iccc.metrology.current.weather.data");
		LOGGER.info(url);
		try {
		
			final Object response =getMetrologyData(url);

			if ((response != null)) {
				return response;
			}
		} catch (Exception e) {
			return e.toString();	
		}
		return MainetConstants.AssetManagement.THROW_EX;
	}

	public Object getMetrologyData(String completeUri) throws Exception {
		String jsonMsg  = null;
    	URI serverURI = null;
        try {      	         
            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
            
            serverURI = new URI(completeUri);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(MainetConstants.UTF_8)));
            LOGGER.info("Metrology call :: URI = {}  | INPUT = {}", completeUri);
            final ResponseEntity<Object> postResponse = restTemplate.getForEntity(serverURI, Object.class);     
            LOGGER.info("Metrology call OUTPUT :: {}",  postResponse.getBody());
            return postResponse.getBody();
        } catch (final Exception e) {
        	 throw e;
        }

	}

}
