package co.com.pragma.api.mapper;

import co.com.pragma.model.loanRequest.LoanRequest;
import co.com.pragma.model.loanRequest.dto.LoanRequestIntDto;
import co.com.pragma.model.loanRequest.dto.LoanRequestOutDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanRequestDtoMapper {
    LoanRequestOutDto toDto(LoanRequest loanRequest);
    LoanRequestIntDto toIntDto(LoanRequest loanRequest);
    List<LoanRequestOutDto> toDtoList(List<LoanRequest> users);
    LoanRequest toLoanRequestFromIntDto(LoanRequestIntDto loanRequestDto);
}
