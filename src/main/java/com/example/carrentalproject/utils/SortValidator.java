package com.example.carrentalproject.utils;

import org.springframework.data.domain.Sort;

public class SortValidator {

        private SortValidator() {
        }

        public static Sort.Direction sortDirection(Sort.Direction sort) {
                return sort != null ? sort : Sort.Direction.ASC;
        }

}
