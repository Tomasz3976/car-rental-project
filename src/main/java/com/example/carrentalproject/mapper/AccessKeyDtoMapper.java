package com.example.carrentalproject.mapper;

import com.example.carrentalproject.domain.AccessKey;
import com.example.carrentalproject.dto.AccessKeyDto;
import org.springframework.stereotype.Service;

@Service
public class AccessKeyDtoMapper {

        public static AccessKeyDto mapToAccessKeyDto(AccessKey accessKey) {
                return AccessKeyDto.builder()
                        .id(accessKey.getId())
                        .carPackage(accessKey.getCarPackage())
                        .hours(accessKey.getHours())
                        .build();
        }

}
