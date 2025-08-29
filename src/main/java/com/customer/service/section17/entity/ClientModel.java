package com.customer.service.section17.entity;

import com.customer.service.section17.enums.CustomerStatus;
import com.customer.service.section17.request.ClientAddress;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@Entity
@Table(name = "client_details")
public class ClientModel {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long clientId;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_password")
    private String clientPassword;

    @Column(name = "client_age")
    private Integer clientAge;

    @Column(name = "client_mobile_number")
    private String clientMobileNumber;

    @Column(name = "client_email_address")
    private String clientEmailAddress;

    @Embedded
    private ClientAddress clientAddress;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @Column(name = "otp")
    private String clientOtp;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "created_by")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "updated_by")
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
