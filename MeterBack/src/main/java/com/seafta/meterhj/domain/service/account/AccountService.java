package com.seafta.meterhj.domain.service.account;


import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdatePasswordRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdateRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountSnapshot createAccount(@NotNull @Valid AccountCreateRequest request);
    AccountDetails getAccount(@NotNull UUID accountId);
    AccountUpdatedSnapshot updateAccount(@NotNull UUID accountId, @NotNull @Valid AccountUpdateRequest request);
    Account getAccountByUsername(@NotNull String username);
    List<AccountDetails> getAccounts();
    void deleteAccount(@NotNull UUID accountId);
    void changePassword(@NotNull UUID accountId, @NotNull @Valid AccountUpdatePasswordRequest request);
    String getNameByAccountId(UUID id);

}
