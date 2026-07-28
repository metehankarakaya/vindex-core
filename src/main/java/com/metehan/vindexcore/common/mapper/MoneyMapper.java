package com.metehan.vindexcore.common.mapper;

import org.mapstruct.Named;

import java.math.BigDecimal;

public class MoneyMapper {

    @Named("centsToAmount")
    public static BigDecimal centsToAmount(Long cents) {
        return cents == null ? null : BigDecimal.valueOf(cents, 2);
    }

    @Named("amountToCents")
    public static Long amountToCents(BigDecimal amount) {
        return amount == null ? null : amount.movePointRight(2).longValueExact();
    }

}
