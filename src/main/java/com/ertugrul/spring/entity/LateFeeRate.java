package com.ertugrul.spring.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LATE_FEE_RATES")

public class LateFeeRate {
    @SequenceGenerator(name = "generator", sequenceName = "LATE_FEE_RATE_ID_SEQ")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", nullable = false)
    private Long id;
    private Double rate;
    private Date firstDate;
    private Date lastDate;
}
