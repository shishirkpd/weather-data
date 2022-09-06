package com.interview.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomResponse {
    String locationName;
    String time;
    double amount;
    String status;
}
