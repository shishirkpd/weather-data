package com.interview.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Station {
    String id;
    String device_id;
    String name;
    Location location;
}
