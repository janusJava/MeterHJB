package com.seafta.meterhj.domain.mapper.account;

import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "accountId", source = "id")
    AccountSnapshot toAccountSnapshot(Account account);

    @Mapping(target = "accountId", source = "id")
    AccountDetails toAccountDetails(Account account);

    @Mapping(target = "accountId", source = "id")
    AccountUpdatedSnapshot toAccountUpdatedSnapshot(Account account);
}
