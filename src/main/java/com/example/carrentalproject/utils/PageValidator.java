package com.example.carrentalproject.utils;

public class PageValidator {

        private PageValidator() {
        }

        public static Integer pageNumber(Integer page) {
                return page == null || page <= 0 ? 1 : page;
        }

}
