package com.seafta.meterhj.domain.persistence.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "account")
@Table(
        name = "account_role",
        indexes = {@Index(columnList = "account_id", name = "account_role_account_id_index")}
)
@Entity
public class AccountRole {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    @Enumerated(STRING)
    private RoleType name;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;

    public static AccountRole buildUserRole(RoleType type) {
        return AccountRole.builder()
                .name(type)
                .build();
    }

    public enum RoleType {
        USER,
        ADMIN
    }

    public String getName() {
        return name.toString();
    }
}
