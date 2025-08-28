package com.customer.service.section17.enums;

/**
 * Enum representing the current status of a customer.
 *
 * <p>This is used to track whether a customer is active in the system or
 * has been deactivated (soft deleted). Instead of removing the record from
 * the database, the system will change the status to {@code INACTIVE}.
 *
 * <p>Enum values:
 * <ul>
 *     <li>{@link #ACTIVE} - The customer is currently active and can use services.</li>
 *     <li>{@link #INACTIVE} - The customer is deactivated their record remains in the database but is not active.</li>
 * </ul>
 *
 * <p>Note: In the entity class, this enum is stored as a string in the database
 * using {@code @Enumerated(EnumType.STRING)} so that database values are human-readable
 * (e.g., "ACTIVE" instead of numeric codes).
 */
public enum CustomerStatus {
    ACTIVE,
    INACTIVE
}