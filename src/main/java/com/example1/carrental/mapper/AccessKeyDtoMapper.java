package com.example1.carrental.mapper;

import com.example1.carrental.domain.AccessKey;
import com.example1.carrental.dto.AccessKeyDto;
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
