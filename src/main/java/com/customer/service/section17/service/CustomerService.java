package com.customer.service.section17.service;

import com.customer.service.section17.enums.CustomerStatus;
import com.customer.service.section17.request.CustomerRequest;
import com.customer.service.section17.response.ApiResponse;
import com.customer.service.section17.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

/**
 * CustomerService defines the contract for all customer-related operations.
 * <p>
 * This interface is implemented by {@code CustomerServiceImpl} and acts as a
 * bridge between the controller layer (handling API requests) and the data layer
 * (repositories). It ensures that all business logic for customer management
 * follows a consistent structure.
 */
public interface CustomerService {

    /**
     * Creates a new customer in the system.
     *
     * @param request the customer details from the request body.
     * @return the created customer's full details as a {@link CustomerResponse}.
     */
    CustomerResponse createCustomer(CustomerRequest request);

    /**
     * Retrieves all customers from the system.
     *
     * @return a list of all customers as {@link CustomerResponse} objects.
     */
    List<CustomerResponse> getAllCustomersData();

    /**
     * Retrieves a customer by their mobile number.
     *
     * @param customerMobileNumber the mobile number of the customer to search for.
     * @return the matching customer's details as {@link CustomerResponse}.
     */
    CustomerResponse getByCustomerMobileNumber(String customerMobileNumber);

    /**
     * Retrieves a customer by their username.
     *
     * @param name the username of the customer.
     * @return the matching customer's details as {@link CustomerResponse}.
     */
    CustomerResponse getByCustomerName(String name);

    /**
     * Retrieves a customer by their email address.
     *
     * @param email the email address of the customer.
     * @return the matching customer's details as {@link CustomerResponse}.
     */
    CustomerResponse getByEmailAddress(String email);

    /**
     * Updates an existing customer's details.
     * The record to be updated is determined based on the mobile number provided in the request.
     *
     * @param request the updated customer details.
     * @return the updated customer's details as {@link CustomerResponse}.
     */
    CustomerResponse updateCustomer(CustomerRequest request);

    /**
     * Performs a soft delete by marking the customer's status as {@link CustomerStatus#INACTIVE}.
     * No record is physically deleted from the database.
     *
     * @param mobile the mobile number of the customer to deactivate.
     * @return the updated customer's details with status set to INACTIVE.
     */
    CustomerResponse deleteCustomer(String mobile);

    /**
     * Updates a customer's mobile number using their username as the identifier.
     *
     * @param userName      the username of the customer.
     * @param MobileNumber  the new mobile number to assign.
     * @return the updated customer's details as {@link CustomerResponse}.
     */
    CustomerResponse updateMobileNumber(String userName, String MobileNumber);

    /**
     * Updates a customer's status based on their mobile number.
     *
     * @param mobileNumber the customer's mobile number.
     * @param status       the new {@link CustomerStatus} to be assigned.
     * @return the updated customer's details as {@link CustomerResponse}.
     */
    CustomerResponse updateStatusByMobile(String mobileNumber, CustomerStatus status);

    List<CustomerResponse> findByUserNameEndingWith(String suffix);

    List<CustomerResponse> findByUserNameStartingWith(String prefix);

    List<CustomerResponse> findByKeyword(String keyword);

    Page<CustomerResponse> findByUserNamePagedNative(String userName, Pageable pageable);

    Map<String, Object> findRawByEmail(String email);

    int updateAddressByUsername(String username, String newAddress);

    int permanentlyDelete();

    ResponseEntity<ApiResponse> getCustomersUsingPagination(int pageNo, int pageSize);

    ResponseEntity<ApiResponse> getCustomersUsingPagingAndSorting(int pageNo, int pageSize, String sortBy);

    ResponseEntity<ApiResponse> getCustomersUsingSorting(String sortBy);
}
