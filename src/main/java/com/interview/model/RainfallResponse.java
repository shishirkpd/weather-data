package com.interview.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RainfallResponse {
    RainfallMetaData metadata;
    List<RainfallItems> items;
    ApiInfo api_info;
}
