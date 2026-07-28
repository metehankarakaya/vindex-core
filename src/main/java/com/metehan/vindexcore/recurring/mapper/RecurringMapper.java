package com.metehan.vindexcore.recurring.mapper;

import com.metehan.vindexcore.common.mapper.MoneyMapper;
import com.metehan.vindexcore.recurring.model.Recurring;
import com.metehan.vindexcore.recurring.dto.RecurringDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = MoneyMapper.class)
public interface RecurringMapper {

    @Mapping(target = "amount", source = "amountCent", qualifiedByName = "centsToAmount")
    RecurringDTO toDTO(Recurring entity);

    @Mapping(target = "amountCent", source = "amount", qualifiedByName = "amountToCents")
    @Mapping(target = "nextDueDate", ignore = true)
    Recurring toEntity(RecurringDTO dto);

    List<RecurringDTO> toDtoList(List<Recurring> entities);

}
