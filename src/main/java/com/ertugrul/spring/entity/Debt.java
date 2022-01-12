package com.ertugrul.spring.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "DEBTS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Debt implements BaseEntity {
    @SequenceGenerator(name = "generator", sequenceName = "DEBT_ID_SEQ")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", nullable = false)
    private Long id;
    private Long userId;
    private Double amount;
    private Date expiryDate;
    private DebtType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Debt userDebt = (Debt) o;
        return id != null && Objects.equals(id, userDebt.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public enum DebtType {
    }
}