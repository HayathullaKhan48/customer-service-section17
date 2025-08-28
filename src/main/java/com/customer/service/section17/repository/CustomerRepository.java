package com.customer.service.section17.repository;

import com.customer.service.section17.entity.CustomerModel;
import com.customer.service.section17.enums.CustomerStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * CustomerRepository acts as the Data Access Layer for interacting with the `CustomerModel` table.
 * <p>
 * It extends {@link JpaRepository} to inherit basic CRUD operations (Create, Read, Update, Delete)
 * without needing boilerplate SQL or manual queries.
 * <p>
 * Benefits:
 * - Automatically provides methods like save(), findAll(), findById(), delete(), etc.
 * - Supports custom finder methods based on naming conventions.
 * - Reduces manual coding by leveraging Spring Data JPA features.
 */
@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {

    /**
     * Finds a customer by their mobile number.
     * <p>
     * - Returns an {@link Optional} to avoid `NullPointerException` if no match is found.
     * - The method name follows Spring Data JPA naming conventions, so no manual query is required.
     *
     * @param customerMobileNumber The unique mobile number of the customer.
     * @return Optional containing CustomerModel if found, else empty.
     */
    Optional<CustomerModel> findByCustomerMobileNumber(String customerMobileNumber);

    /**
     * Finds a customer by their username.
     *
     * @param userName The unique username of the customer.
     * @return Optional containing CustomerModel if found, else empty.
     */
    Optional<CustomerModel> findByUserName(String userName);

    /**
     * Finds a customer by their email address.
     *
     * @param customerEmailAddress The unique email address of the customer.
     * @return Optional containing CustomerModel if found, else empty.
     */
    Optional<CustomerModel> findByCustomerEmailAddress(String customerEmailAddress);

    /**
     * Checks whether a customer with the given username exists.
     * <p>
     * - Used for validation before creating a new customer to avoid duplicates.
     *
     * @param userName The username to check.
     * @return true if exists, false otherwise.
     */
    boolean existsByUserName(String userName);

    /**
     * Checks whether a customer with the given email address exists.
     *
     * @param customerEmailAddress The email address to check.
     * @return true if exists, false otherwise.
     */
    boolean existsByCustomerEmailAddress(String customerEmailAddress);

    /**
     * Checks whether a customer with the given mobile number exists.
     *
     * @param customerMobileNumber The mobile number to check.
     * @return true if exists, false otherwise.
     */
    boolean existsByCustomerMobileNumber(String customerMobileNumber);

    @Query("SELECT c FROM CustomerModel c WHERE c.customerEmailAddress = ?1")
    Optional<CustomerModel> findByEmailAddressJPQL(String emailAddress);

    @Query("SELECT c FROM CustomerModel c WHERE c.userName LIKE %?1")
    List<CustomerModel> findByUserNameEndingWith(String suffix);

    @Query("SELECT c FROM CustomerModel c WHERE c.userName LIKE ?1%")
    List<CustomerModel> findByUserNameStartingWith(String prefix);

    @Query("SELECT c FROM CustomerModel c WHERE LOWER(c.userName) LIKE %:q% OR LOWER(c.customerEmailAddress) LIKE %:q%")
    List<CustomerModel> searchByKeyword(@Param("q") String lowercaseQuery);

    @Query("SELECT c FROM CustomerModel c WHERE c.userName = :username OR c.customerEmailAddress = :email")
    Optional<CustomerModel> findByUsernameOrEmail(@Param("username") String username,
                                                  @Param("email") String email);

    @Query(value = "SELECT * FROM customer_details_section12 WHERE customer_mobile_number = ?1",
            nativeQuery = true)
    Optional<CustomerModel> findByMobileNative(String mobile);

    @Query(value = "SELECT * FROM customer_details_section12 WHERE user_name = ?1",
            countQuery = "SELECT COUNT(*) FROM customer_details_section12 WHERE user_name = ?1",
            nativeQuery = true)
    Page<CustomerModel> findByUserNamePagedNative(String userName, Pageable pageable);

    @Query(value = "SELECT * FROM customer_details_section12 WHERE customer_email_address = ?1",
            nativeQuery = true)
    Map<String, Object> findRawByEmail(String email);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE CustomerModel c SET c.customerAddress = :address, c.updatedDate = CURRENT_TIMESTAMP " +
            "WHERE c.userName = :username")
    int updateCustomerAddress(@Param("username") String username, @Param("address") String address);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("UPDATE CustomerModel c SET c.userStatus = :status, c.updatedDate = CURRENT_TIMESTAMP " +
            "WHERE c.customerMobileNumber = :mobile")
    int updateStatusByMobile(@Param("mobile") String mobile, @Param("status") CustomerStatus status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query("DELETE FROM CustomerModel c WHERE c.userStatus = 'INACTIVE'")
    int deleteInactiveCustomers();

}