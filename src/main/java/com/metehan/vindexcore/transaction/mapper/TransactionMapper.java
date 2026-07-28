package com.metehan.vindexcore.transaction.mapper;

import com.metehan.vindexcore.common.mapper.MoneyMapper;
import com.metehan.vindexcore.transaction.model.Transaction;
import com.metehan.vindexcore.transaction.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring", uses = MoneyMapper.class)
public interface TransactionMapper {

    @Mapping(target = "amount", source = "amountCent", qualifiedByName = "centsToAmount")
    TransactionDTO toDTO(Transaction entity);

    @Mapping(target = "amountCent", source = "amount", qualifiedByName = "amountToCents")
    Transaction toEntity(TransactionDTO dto);

    List<TransactionDTO> toDtoList(List<Transaction> entities);

}
