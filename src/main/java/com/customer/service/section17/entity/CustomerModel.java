package com.customer.service.section17.entity;

import com.customer.service.section17.enums.CustomerStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * CustomerModel - Entity class for storing customer details in the database.
 * This class:
 *  - Represents a table in the database (`customer_details_section12`)
 *  - Uses JPA annotations for ORM (Object-Relational Mapping)
 *  - Uses Lombok annotations to remove boilerplate getter/setter code
 *  - Tracks creation and update timestamps automatically
 */
@Entity
@Table(name = "customer_details_section12")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "userName", unique = true)
    private String userName;

    @Column(name = "customerAge")
    private Integer customerAge;

    @Column(name = "customerMobileNumber", unique = true)
    private String customerMobileNumber;

    @Column(name = "customerEmailAddress", unique = true)
    private String customerEmailAddress;

    @Column(name = "customerAddress")
    private String customerAddress;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "userStatus")
    private CustomerStatus userStatus;

    @Column(name = "createdDate")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updatedDate")
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}