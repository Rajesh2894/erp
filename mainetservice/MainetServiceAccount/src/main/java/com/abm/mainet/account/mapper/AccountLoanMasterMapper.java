package com.abm.mainet.account.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.abm.mainet.account.domain.AccountLoanMasterEntity;
import com.abm.mainet.account.dto.AccountLoanMasterDto;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class AccountLoanMasterMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public AccountLoanMasterMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public AccountLoanMasterEntity mapAccountLoanMasterDtoToAccountLoanMaster(
			AccountLoanMasterDto accountLoanMasterDto) {
		if (accountLoanMasterDto == null) {
			return null;
		}
		accountLoanMasterDto.getAccountLoanDetList().forEach(det -> {
			det.setLnmas(accountLoanMasterDto);
		});
		return map(accountLoanMasterDto, AccountLoanMasterEntity.class);
	}

	public AccountLoanMasterDto mapAccountLoanMasterToAccountLoanMasterDto(
			AccountLoanMasterEntity accountLoanMasterEntity) {
		if (accountLoanMasterEntity == null) {
			return null;
		}
		return map(accountLoanMasterEntity, AccountLoanMasterDto.class);
	}

	public List<AccountLoanMasterDto> mapAccountLoanMasterListToAccountLoanMasterDtoList(
			List<AccountLoanMasterEntity> accountLoanMasterEntityList) {
		if (accountLoanMasterEntityList == null) {
			return null;
		}
		return map(accountLoanMasterEntityList, AccountLoanMasterDto.class);
	}

	public List<AccountLoanMasterEntity> mapAccountLoanMasterDtoListToAccountLoanMasterList(
			List<AccountLoanMasterDto> accountLoanMasterDtoList) {
		if (accountLoanMasterDtoList == null) {
			return null;
		}
		return map(accountLoanMasterDtoList, AccountLoanMasterEntity.class);
	}

}
