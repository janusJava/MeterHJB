package com.seafta.meterhj.domain.mapper.account;

import com.seafta.meterhj.domain.dto.account.AccountDetails;
import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.account.AccountUpdatedSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-12T17:37:00+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountSnapshot toAccountSnapshot(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountSnapshot accountSnapshot = new AccountSnapshot();

        accountSnapshot.setAccountId( account.getId() );
        accountSnapshot.setEmail( account.getEmail() );
        accountSnapshot.setPasswordHash( account.getPasswordHash() );
        accountSnapshot.setFullName( account.getFullName() );
        Set<Meter> set = account.getMeters();
        if ( set != null ) {
            accountSnapshot.setMeters( new HashSet<Meter>( set ) );
        }

        return accountSnapshot;
    }

    @Override
    public AccountDetails toAccountDetails(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountDetails accountDetails = new AccountDetails();

        accountDetails.setAccountId( account.getId() );
        accountDetails.setEmail( account.getEmail() );
        accountDetails.setFullName( account.getFullName() );
        accountDetails.setCreated( account.getCreated() );

        return accountDetails;
    }

    @Override
    public AccountUpdatedSnapshot toAccountUpdatedSnapshot(Account account) {
        if ( account == null ) {
            return null;
        }

        AccountUpdatedSnapshot accountUpdatedSnapshot = new AccountUpdatedSnapshot();

        accountUpdatedSnapshot.setAccountId( account.getId() );
        accountUpdatedSnapshot.setEmail( account.getEmail() );
        accountUpdatedSnapshot.setFullName( account.getFullName() );
        accountUpdatedSnapshot.setCreated( account.getCreated() );
        accountUpdatedSnapshot.setModified( account.getModified() );

        return accountUpdatedSnapshot;
    }
}
