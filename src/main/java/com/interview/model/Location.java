package com.interview.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Location {
    double latitude;
    double longitude;
}
