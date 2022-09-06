package com.interview.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RainfallMetaData {
    List<Station> stations;
    String reading_type;
    String reading_unit;
}
