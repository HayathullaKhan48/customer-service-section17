package com.customer.service.section17.controller;

import com.customer.service.section17.enums.CustomerStatus;
import com.customer.service.section17.request.CustomerRequest;
import com.customer.service.section17.response.ApiResponse;
import com.customer.service.section17.response.CustomerResponse;
import com.customer.service.section17.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.customer.service.section17.constant.CustomerConstant.*;

/**
 * REST controller for handling Customer-related operations.
 * Provides endpoints for creating, retrieving, updating, and soft-deleting customer records.
 * Each endpoint returns a standardized {@link ApiResponse} object.
 */
@RestController
@RequestMapping("/api/customer/v1")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    private final CustomerService customerService;

    /**
     * Create a new customer record.
     * HTTP Method: POST
     * Endpoint: /api/customer/v1/create
     *
     * @param request Request body containing new customer details.
     * @return ResponseEntity containing ApiResponse with created customer details.
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse(HttpStatus.CREATED.value(), CUSTOMER_CREATED_SUCCESS, response));
    }

    /**
     * Retrieve all customers.
     * HTTP Method: GET
     * Endpoint: /api/customer/v1/getAllData
     *
     * @return ResponseEntity containing ApiResponse with a list of all customers.
     */
    @GetMapping("/getAllData")
    @Operation(
            description = "Get Users Details"
    )
    public ResponseEntity<ApiResponse> getAllCustomers() {
        List<CustomerResponse> response = customerService.getAllCustomersData();
        return ResponseEntity
                .ok(new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), response));

    }

    /**
     * Retrieve a customer by mobile number.
     * HTTP Method: GET
     * Endpoint: /api/customer/v1/getByMobile/{customerMobileNumber}
     *
     * @param customerMobileNumber Customer's mobile number.
     * @return ResponseEntity containing ApiResponse with matching customer details.
     */
    @GetMapping("/getByMobile/{customerMobileNumber}")
    public ResponseEntity<ApiResponse> getByMobile(@PathVariable String customerMobileNumber) {
        CustomerResponse response = customerService.getByCustomerMobileNumber(customerMobileNumber);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), response));
    }

    /**
     * Retrieve a customer by username.
     * HTTP Method: GET
     * Endpoint: /api/customer/v1/getByUserName/{userName}
     *
     * @param userName Customer's username.
     * @return ResponseEntity containing ApiResponse with matching customer details.
     */
    @GetMapping("/getByUserName/{userName}")
    public ResponseEntity<ApiResponse> getByUserName(@PathVariable String userName) {
        CustomerResponse response = customerService.getByCustomerName(userName);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), response));
    }

    /**
     * Retrieve a customer by email address.
     * HTTP Method: GET
     * Endpoint: /api/customer/v1/getByEmailAddress/{emailAddress}
     *
     * @param emailAddress Customer's email address.
     * @return ResponseEntity containing ApiResponse with matching customer details.
     */
    @GetMapping("/getByEmailAddress/{emailAddress}")
    public ResponseEntity<ApiResponse> getByEmail(@PathVariable String emailAddress) {
        CustomerResponse response = customerService.getByEmailAddress(emailAddress);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), response));
    }

    /**
     * Update an existing customer's details by matching mobile number.
     * HTTP Method: PUT
     * Endpoint: /api/customer/v1/update
     *
     * @param request Request body containing updated customer details.
     * @return ResponseEntity containing ApiResponse with updated customer details.
     */
    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(request);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), CUSTOMER_UPDATED_SUCCESS, response));
    }

    /**
     * Softly delete a customer by setting status to INACTIVE using mobile number.
     * The record is not removed from the database.
     * HTTP Method: DELETE
     * Endpoint: /api/customer/v1/delete/{mobileNumber}
     *
     * @param mobileNumber Mobile number of the customer to be soft deleted.
     * @return ResponseEntity containing ApiResponse with updated status.
     */
    @DeleteMapping("/delete/{mobileNumber}")
    public ResponseEntity<ApiResponse> deleteCustomer(@PathVariable String mobileNumber) {
        CustomerResponse response = customerService.deleteCustomer(mobileNumber);
        return ResponseEntity.ok(
                new ApiResponse(HttpStatus.OK.value(), CUSTOMER_DELETED_SUCCESS, response));
    }

    /**
     * Update a customer's mobile number using their username.
     * HTTP Method: PATCH
     * Endpoint: /api/customer/v1/updateMobileNumber/{userName}/{mobileNumber}
     *
     * @param userName     Username of the customer.
     * @param mobileNumber New mobile number.
     * @return ResponseEntity containing ApiResponse with updated customer details.
     */
    @PatchMapping("/updateMobileNumber/{userName}/{mobileNumber}")
    public ResponseEntity<ApiResponse> updateMobileNumber(@PathVariable String userName, @PathVariable String mobileNumber) {
        CustomerResponse response = customerService.updateMobileNumber(userName, mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(HttpStatus.CREATED.value(), HttpStatus.OK.name(), response));
    }

    /**
     * Update a customer's status (e.g., ACTIVE/INACTIVE) using their mobile number.
     * HTTP Method: PATCH
     * Endpoint: /api/customer/v1/status/{mobileNumber}/{status}
     *
     * @param mobileNumber Customer's mobile number.
     * @param status       New status to be set (ACTIVE or INACTIVE).
     * @return ResponseEntity containing ApiResponse with updated customer status.
     */
    @PatchMapping("/status/{mobileNumber}/{status}")
    public ResponseEntity<ApiResponse> updateStatusByMobile(@PathVariable String mobileNumber, @PathVariable CustomerStatus status) {
        CustomerResponse response = customerService.updateStatusByMobile(mobileNumber, status);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), CUSTOMER_STATUS_UPDATED_SUCCESS, response));
    }

    @GetMapping("/searchEndsWith/{suffix}")
    public ResponseEntity<ApiResponse> findByUserNameEndsWith(@PathVariable String suffix) {
        return ResponseEntity.ok(new ApiResponse(200, "OK", customerService.findByUserNameEndingWith(suffix)));
    }

    @GetMapping("/searchStartsWith/{prefix}")
    public ResponseEntity<ApiResponse> findByUserNameStartsWith(@PathVariable String prefix) {
        return ResponseEntity.ok(new ApiResponse(200, "OK", customerService.findByUserNameStartingWith(prefix)));
    }

    @GetMapping("/findByKeyword/{keyword}")
    public ResponseEntity<ApiResponse> findByKeyword(@PathVariable String keyword) {
        return ResponseEntity.ok(new ApiResponse(200, "OK", customerService.findByKeyword(keyword)));
    }

    @GetMapping("/findByUserNamePagedNative/{userName}")
    public ResponseEntity<ApiResponse> findByUserNamePagedNative(@PathVariable String userName,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(new ApiResponse(200, "OK",
                customerService.findByUserNamePagedNative(userName, PageRequest.of(page, size))));
    }

    @GetMapping("/rawByEmail/{email}")
    public ResponseEntity<ApiResponse> rawByEmail(@PathVariable String email) {
        Map<String, Object> map = customerService.findRawByEmail(email);
        return ResponseEntity.ok(new ApiResponse(200, "OK", map));
    }

    @PatchMapping("/updateAddressByUsername/{username}")
    public ResponseEntity<ApiResponse> updateAddress(@PathVariable String username,
                                                     @RequestParam String address) {
        int updated = customerService.updateAddressByUsername(username, address);
        return ResponseEntity.ok(new ApiResponse(200, "Updated rows: " + updated, null));
    }

    @DeleteMapping("/permanentlyDelete")
    public ResponseEntity<ApiResponse> hardDeleteInactive() {
        int deleted = customerService.permanentlyDelete();
        return ResponseEntity.ok(new ApiResponse(200, "Deleted rows: " + deleted, null));
    }

    /**
     * GET customers with only pagination
     */
    @GetMapping("/getCustomer/paging")
    public ResponseEntity<ApiResponse> getCustomersUsingPagination(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        return customerService.getCustomersUsingPagination(pageNo, pageSize);
    }

    /**
     * GET customers with pagination + sorting
     */
    @GetMapping("/getCustomer/paging/sorting")
    public ResponseEntity<ApiResponse> getCustomersUsingPagingAndSorting(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return customerService.getCustomersUsingPagingAndSorting(pageNo, pageSize, sortBy);
    }

    /**
     * GET customers with sorting only
     */
    @GetMapping("/getCustomer/sorting")
    public ResponseEntity<ApiResponse> getCustomersUsingSorting(
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return customerService.getCustomersUsingSorting(sortBy);
    }
}