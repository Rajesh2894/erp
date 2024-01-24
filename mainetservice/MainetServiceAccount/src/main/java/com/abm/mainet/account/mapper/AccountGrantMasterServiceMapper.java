package com.abm.mainet.account.mapper;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.abm.mainet.account.domain.AccountGrantMasterEntity;
import com.abm.mainet.account.dto.AccountGrantMasterDto;
import com.abm.mainet.common.utility.AbstractServiceMapper;

@Component
public class AccountGrantMasterServiceMapper extends AbstractServiceMapper {

	private ModelMapper modelMapper;

	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public AccountGrantMasterServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public AccountGrantMasterEntity mapAccountGrantMasterDtoToAccountGranttMasterEntity(
			AccountGrantMasterDto accountGrantMasterDto) {
		if (accountGrantMasterDto == null) {
			return null;
		}
		return map(accountGrantMasterDto, AccountGrantMasterEntity.class);
	}

	public AccountGrantMasterDto mapAccountGrantMasterEntityToAccountGrantMasterDto(
			AccountGrantMasterEntity accountGrantMasterEntity) {
		if (accountGrantMasterEntity == null) {
			return null;
		}
		return map(accountGrantMasterEntity, AccountGrantMasterDto.class);
	}

	public List<AccountGrantMasterDto> mapAccountInvestmentMasterListToAccountInvestmentMasterDtoList(
			List<AccountGrantMasterEntity> accountGrantMasterEntityList) {
		if (accountGrantMasterEntityList == null) {
			return null;
		}
		return map(accountGrantMasterEntityList, AccountGrantMasterDto.class);
	}

	public List<AccountGrantMasterEntity> mapAccountInvestmentMasterDtoListToAccountInvestmentMasterList(
			List<AccountGrantMasterDto> accountGrantMasterDtoList) {
		if (accountGrantMasterDtoList == null) {
			return null;
		}
		return map(accountGrantMasterDtoList, AccountGrantMasterEntity.class);

	}

}
