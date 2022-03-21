package org.miage.bankservice.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 674535276789589376L;

    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "balance")
    private double balance;

    public void debitAccount(double amount) {
        balance -= amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;
        return accountId != null && Objects.equals(accountId, account.accountId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
