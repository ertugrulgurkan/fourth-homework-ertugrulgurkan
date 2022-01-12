package com.ertugrul.spring.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "PAYMENTS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Payment {
    @SequenceGenerator(name = "generator", sequenceName = "PAYMENT_ID_SEQ")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", nullable = false)
    private Long id;
    private Long userId;
    private Long debtId;
    private Double debtAmount;
    private Double totalDebtAmount;
    private Date paymentDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment payment = (Payment) o;
        return id != null && Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
