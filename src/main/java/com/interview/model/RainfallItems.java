package com.interview.model;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RainfallItems {
    Timestamp timestamp;
    List<Reading> readings;
}
