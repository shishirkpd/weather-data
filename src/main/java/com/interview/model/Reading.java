package com.interview.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Reading {
    String station_id;
    double value;
}
