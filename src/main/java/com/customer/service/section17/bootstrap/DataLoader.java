package com.customer.service.section17.bootstrap;

import com.customer.service.section17.entity.CustomerModel;
import com.customer.service.section17.enums.CustomerStatus;
import com.customer.service.section17.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * DataLoader is responsible for inserting 100 dummy customer records
 * into the database when the application starts.
 * - Inserts only if the table is empty (avoids duplicates).
 * - Ensures each customer has a unique email and mobile number.
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        // Insert only if no data exists
        if (customerRepository.count() == 0) {
            /**
            List<CustomerModel> customers = new ArrayList<>();
            Random random = new Random();

            for (int i = 1; i <= 100; i++) {
                CustomerModel customer = new CustomerModel();
                customer.setUserName("User" + i);
                customer.setCustomerAge(18 + random.nextInt(30));
                customer.setCustomerEmailAddress("user" + i + "@gmail.com");
                customer.setCustomerMobileNumber("90000000" + String.format("%03d", i));
                customer.setCustomerAddress("Address " + i + " - Chinnamandem");
                customer.setUserStatus(CustomerStatus.ACTIVE);
                customer.setCreatedDate(LocalDateTime.now());
                customer.setUpdatedDate(LocalDateTime.now());
                customer.setPassword("encryptedPassword" + i);
                customers.add(customer);
            }

            customerRepository.saveAll(customers);
            System.out.println("100 dummy customers inserted into database.");
        } else {
            System.out.println("Skipped inserting dummy data. Records already exist in database.");
             */
        }
    }
}
