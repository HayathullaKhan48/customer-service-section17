package com.customer.service.section17.service.impl;

import com.customer.service.section17.entity.CustomerModel;
import com.customer.service.section17.enums.CustomerStatus;
import com.customer.service.section17.exceptions.CustomerAlreadyExistsException;
import com.customer.service.section17.exceptions.CustomerNotExistsException;
import com.customer.service.section17.mapper.CustomerMapper;
import com.customer.service.section17.repository.CustomerRepository;
import com.customer.service.section17.repository.PaginationRepository;
import com.customer.service.section17.request.CustomerRequest;
import com.customer.service.section17.response.ApiResponse;
import com.customer.service.section17.response.CustomerResponse;
import com.customer.service.section17.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.customer.service.section17.constant.CustomerConstant.CUSTOMER_ALREADY_EXISTS;
import static com.customer.service.section17.constant.CustomerConstant.CUSTOMER_NOT_EXISTS;

/**
 * Implementation of {@link CustomerService} that contains
 * the business logic for managing customer data.
 * <p>
 * This service:
 * <ul>
 *   <li>Performs validations before persisting/updating customer data.</li>
 *   <li>Handles duplicate checks for username, email, and mobile number.</li>
 *   <li>Uses soft deletion by changing {@link CustomerStatus} instead of deleting records.</li>
 * </ul>
 * <p>
 * All database interactions are handled through {@link CustomerRepository}.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    /** Repository for accessing and modifying customer data. */
    private final CustomerRepository customerRepository;
    private final PaginationRepository paginationRepository;

    /**
     * Creates a new customer.
     * <ul>
     *   <li>Validates uniqueness of username, email, and mobile number.</li>
     *   <li>Sets default status to {@code ACTIVE}.</li>
     *   <li>Sets creation and update timestamps.</li>
     * </ul>
     *
     * @param request The customer creation request payload.
     * @return The created customer as a {@link CustomerResponse}.
     * @throws CustomerAlreadyExistsException if duplicate fields are found.
     */
    @Override
    @Transactional
    public CustomerResponse createCustomer(CustomerRequest request) {
        List<String> duplicates = new ArrayList<>();
        if (customerRepository.existsByUserName(request.getUserName())) {
            duplicates.add("userName");
        }
        if (customerRepository.existsByCustomerEmailAddress(request.getCustomerEmailAddress())) {
            duplicates.add("emailAddress");
        }
        if (customerRepository.existsByCustomerMobileNumber(request.getCustomerMobileNumber())) {
            duplicates.add("mobileNumber");
        }

        if (!duplicates.isEmpty()) {
            String message = "Duplicate fields: " + String.join(", ", duplicates) + " _ " + CUSTOMER_ALREADY_EXISTS;
            throw new CustomerAlreadyExistsException(message);
        }
        CustomerModel model = CustomerMapper.toCustomerModel(request);
        model.setUserStatus(CustomerStatus.ACTIVE);
        model.setCreatedDate(LocalDateTime.now());
        model.setUpdatedDate(LocalDateTime.now());

        CustomerModel saved = customerRepository.saveAndFlush(model);
        return CustomerMapper.toCustomerResponse(saved);
    }

    /**
     * Retrieves all customers from the database.
     *
     * @return A list of {@link CustomerResponse}.
     */
    @Override
    public List<CustomerResponse> getAllCustomersData() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a customer by mobile number.
     *
     * @param mobileNumber The customer's mobile number.
     * @return The customer as a {@link CustomerResponse}.
     * @throws CustomerNotExistsException if the customer does not exist.
     */
    @Override
    public CustomerResponse getByCustomerMobileNumber(String mobileNumber) {
        CustomerModel model = customerRepository.findByCustomerMobileNumber(mobileNumber)
                .orElseThrow(() -> new CustomerNotExistsException(mobileNumber + " " + CUSTOMER_NOT_EXISTS));
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Retrieves a customer by username.
     *
     * @param CustomerName The customer's username.
     * @return The customer as a {@link CustomerResponse}.
     * @throws CustomerNotExistsException if the customer does not exist.
     */
    @Override
    public CustomerResponse getByCustomerName(String CustomerName) {
        CustomerModel model = customerRepository.findByUserName(CustomerName)
                .orElseThrow(() -> new CustomerNotExistsException(CustomerName + " " + CUSTOMER_NOT_EXISTS));
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Retrieves a customer by email address.
     *
     * @param emailAddress The customer's email address.
     * @return The customer as a {@link CustomerResponse}.
     * @throws CustomerNotExistsException if the customer does not exist.
     */
    @Override
    public CustomerResponse getByEmailAddress(String emailAddress) {
        CustomerModel model = customerRepository.findByCustomerEmailAddress(emailAddress)
                .orElseThrow(() -> new CustomerNotExistsException(emailAddress + " " + CUSTOMER_NOT_EXISTS));
        return CustomerMapper.toCustomerResponse(model);
    }

    /**
     * Updates a customer's details using their mobile number.
     * <p>Note: Mobile number and status are not updated here.</p>
     *
     * @param request The updated customer details.
     * @return The updated customer as a {@link CustomerResponse}.
     * @throws CustomerNotExistsException if the customer does not exist.
     */
    @Override
    @Transactional
    public CustomerResponse updateCustomer(CustomerRequest request) {
        CustomerModel model = customerRepository.findByCustomerMobileNumber(request.getCustomerMobileNumber())
                .orElseThrow(() -> new CustomerNotExistsException(request.getCustomerMobileNumber() + " " + CUSTOMER_NOT_EXISTS));

        model.setUserName(request.getUserName());
        model.setCustomerAge(request.getCustomerAge());
        model.setCustomerAddress(request.getCustomerAddress());
        model.setCustomerEmailAddress(request.getCustomerEmailAddress());
        model.setUpdatedDate(LocalDateTime.now());

        return CustomerMapper.toCustomerResponse(customerRepository.saveAndFlush(model));
    }

    /**
     * Soft deletes a customer by setting their status to {@code INACTIVE}.
     *
     * @param mobile The customer's mobile number.
     * @return The updated customer as a {@link CustomerResponse}.
     * @throws CustomerNotExistsException if the customer does not exist.
     */
    @Override
    @Transactional
    public CustomerResponse deleteCustomer(String mobile) {
        CustomerModel model = customerRepository.findByCustomerMobileNumber(mobile)
                .orElseThrow(() -> new CustomerNotExistsException(mobile + " " + CUSTOMER_NOT_EXISTS));

        model.setUserStatus(CustomerStatus.INACTIVE);
        model.setUpdatedDate(LocalDateTime.now());

        return CustomerMapper.toCustomerResponse(customerRepository.saveAndFlush(model));
    }

    /**
     * Updates a customer's mobile number using their username.
     *
     * @param userName     The customer's username.
     * @param mobileNumber The new mobile number.
     * @return The updated customer as a {@link CustomerResponse}.
     * @throws CustomerNotExistsException if the customer does not exist.
     * @throws CustomerAlreadyExistsException if the mobile number is already taken.
     */
    @Override
    @Transactional
    public CustomerResponse updateMobileNumber(String userName, String mobileNumber) {
        CustomerModel model = customerRepository.findByUserName(userName)
                .orElseThrow(() -> new CustomerNotExistsException(userName + " " + CUSTOMER_NOT_EXISTS));

        customerRepository.findByCustomerMobileNumber(mobileNumber).ifPresent(existing -> {
            throw new CustomerAlreadyExistsException(mobileNumber + " " + CUSTOMER_ALREADY_EXISTS);
        });

        model.setCustomerMobileNumber(mobileNumber);
        model.setUpdatedDate(LocalDateTime.now());

        return CustomerMapper.toCustomerResponse(customerRepository.saveAndFlush(model));
    }

    /**
     * Updates a customer's status using their mobile number.
     *
     * @param mobileNumber The customer's mobile number.
     * @param status       The new {@link CustomerStatus}.
     * @return The updated customer as a {@link CustomerResponse}.
     * @throws CustomerNotExistsException if the customer does not exist.
     */
    @Override
    @Transactional
    public CustomerResponse updateStatusByMobile(String mobileNumber, CustomerStatus status) {
        CustomerModel model = customerRepository.findByCustomerMobileNumber(mobileNumber)
                .orElseThrow(() -> new CustomerNotExistsException(mobileNumber + " " + CUSTOMER_NOT_EXISTS));
        model.setUserStatus(status);
        model.setUpdatedDate(LocalDateTime.now());
        return CustomerMapper.toCustomerResponse(customerRepository.saveAndFlush(model));
    }

    @Override
    public List<CustomerResponse> findByUserNameEndingWith(String suffix) {
        return customerRepository.findByUserNameEndingWith(suffix).stream().map(CustomerMapper::toCustomerResponse).toList();
    }

    @Override
    public List<CustomerResponse> findByUserNameStartingWith(String prefix) {
        return customerRepository.findByUserNameStartingWith(prefix)
                .stream()
                .map(CustomerMapper::toCustomerResponse)
                .toList();
    }

    @Override
    public List<CustomerResponse> findByKeyword(String keyword) {
        return customerRepository
                .searchByKeyword(keyword.toLowerCase())
                .stream()
                .map(CustomerMapper::toCustomerResponse)
                .toList();
    }

    @Override
    public Page<CustomerResponse> findByUserNamePagedNative(String userName, Pageable pageable) {
        return customerRepository.findByUserNamePagedNative(userName, pageable)
                .map(CustomerMapper::toCustomerResponse);
    }

    @Override
    public Map<String, Object> findRawByEmail(String email) {
        return customerRepository.findRawByEmail(email);
    }

    @Override
    @Transactional
    public int updateAddressByUsername(String username, String newAddress) {
        return customerRepository.updateCustomerAddress(username, newAddress);
    }

    @Override
    @Transactional
    public int permanentlyDelete() {
        return customerRepository.deleteInactiveCustomers();
    }

    @Override
    public ResponseEntity<ApiResponse> getCustomersUsingPagination(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CustomerModel> page = paginationRepository.findAll(pageable);
        List<CustomerResponse> responses = page.getContent()
                .stream()
                .map(CustomerMapper::toCustomerResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(),
                        "Customers fetched successfully with pagination",
                        responses));
    }

    /**
     * Fetch customers with pagination and sorting.
     */
    @Override
    public ResponseEntity<ApiResponse> getCustomersUsingPagingAndSorting(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<CustomerModel> page = paginationRepository.findAll(pageable);
        List<CustomerResponse> responses = page.getContent()
                .stream()
                .map(CustomerMapper::toCustomerResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(),
                        "Customers fetched successfully with pagination and sorting",
                        responses));
    }

    /**
     * Fetch customers with sorting only (all records sorted).
     */
    @Override
    public ResponseEntity<ApiResponse> getCustomersUsingSorting(String sortBy) {
        List<CustomerResponse> responses = ((List<CustomerModel>) paginationRepository.findAll(Sort.by(sortBy)))
                .stream()
                .map(CustomerMapper::toCustomerResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(),
                        "Customers fetched successfully with sorting",
                        responses));
    }

}
