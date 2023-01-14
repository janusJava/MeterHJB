package com.seafta.meterhj.domain.persistence.model.account;

import com.seafta.meterhj.domain.persistence.model.meter.Meter;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.account.AccountUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(indexes = {
        @Index(columnList = "email", name = "account_email_index", unique = true)
})
public class Account {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "full_name")
    private String fullName;

    @NotNull
    private OffsetDateTime created;

    @NotNull
    private OffsetDateTime modified;


    @OneToMany(mappedBy = "account",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Set<AccountRole> roles;

    @OneToMany(mappedBy = "account",
               orphanRemoval = true,
               fetch = FetchType.EAGER)
    private Set<Meter> meters;

    public static Account buildUserAccount(@NotNull @Valid AccountCreateRequest request,
                                        @NotNull PasswordEncoder passwordEncoder) {
        AccountRole role;
        role = AccountRole.buildUserRole(AccountRole.RoleType.USER);

        Account account = Account.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .created(OffsetDateTime.now(Clock.systemUTC()))
                .modified(OffsetDateTime.now(Clock.systemUTC()))
                .roles(Collections.singleton(role))
                .build();
        role.setAccount(account);
        return account;
    }
    public Account editAccount(@NotNull @Valid AccountUpdateRequest request) {
        this.fullName = request.getFullName();
        this.modified = OffsetDateTime.now(Clock.systemUTC());
        return this;
    }

    @PrePersist
    private void onPrePersist() {
        created = OffsetDateTime.now(ZoneOffset.UTC);
        modified = created;
    }

    @PreUpdate
    private void onPreUpdate() {
        modified = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
