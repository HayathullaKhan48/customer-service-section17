package com.customer.service.section17.repository;

import com.customer.service.section17.entity.CustomerModel;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for pagination and sorting of CustomerModel.
 * Extends PagingAndSortingRepository which already provides
 * findAll(Pageable) and findAll(Sort) methods.
 */
@Repository
public interface PaginationRepository extends PagingAndSortingRepository<CustomerModel, Long> {
}
