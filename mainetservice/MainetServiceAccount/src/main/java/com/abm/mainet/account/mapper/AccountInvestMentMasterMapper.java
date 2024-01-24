package com.abm.mainet.account.mapper;

import java.util.List;


import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.domain.AccountInvestmentMasterEntity;
import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class AccountInvestMentMasterMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public AccountInvestMentMasterMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public AccountInvestmentMasterEntity mapAccountInvestmentMasterDtoToAccountInvestmentMaster(
			AccountInvestmentMasterDto accountInvestmentMasterDto) {
		if (accountInvestmentMasterDto == null) {
			return null;
		}
		return map(accountInvestmentMasterDto, AccountInvestmentMasterEntity.class);
	}

	public AccountInvestmentMasterDto mapAccountInvestmentMasterToAccountInvestmentMasterDto(
			AccountInvestmentMasterEntity accountInvestmentMasterEntity) {
		if (accountInvestmentMasterEntity == null) {
			return null;
		}
		return map(accountInvestmentMasterEntity, AccountInvestmentMasterDto.class);
	}

	
	public List<AccountInvestmentMasterDto> mapAccountInvestmentMasterListToAccountInvestmentMasterDtoList(List<AccountInvestmentMasterEntity> accountInvestmentMasterList) {
		if (accountInvestmentMasterList == null) {
			return null;
		}
		return map(accountInvestmentMasterList, AccountInvestmentMasterDto.class);
	}

	public List<AccountInvestmentMasterEntity> mapAccountInvestmentMasterDtoListToAccountInvestmentMasterList(
			List<AccountInvestmentMasterDto> accountInvestmentMasterDtoList) {
		if (accountInvestmentMasterDtoList == null) {
			return null;
		}
		return map(accountInvestmentMasterDtoList, AccountInvestmentMasterEntity.class);

	}

}
