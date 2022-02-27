package org.example.beans.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CityDTO {
    private short cityId;
    private String countryName;
    private String cityName;
}
