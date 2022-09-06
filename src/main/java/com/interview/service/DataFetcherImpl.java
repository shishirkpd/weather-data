package com.interview.service;

import com.interview.model.CustomResponse;
import com.interview.model.RainfallResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@Service
@Slf4j
public class DataFetcherImpl implements DataFetcher {

    @Value("${spring.data.url}")
    private String url;

    @Value("${spring.data.location}")
    private String location;

    @Override
    public CustomResponse fetchData() {

        log.info("Looking data for the given location: {}", location);

        RestTemplate restTemplate = new RestTemplate();

        var rainfallRes =  restTemplate.getForEntity(url, RainfallResponse.class).getBody();

        var station = rainfallRes.getMetadata().getStations().stream().filter(s -> s.getName().equalsIgnoreCase(location.toLowerCase())).collect(Collectors.toList()).stream().findFirst();

        if(station.isPresent()) {
            var readings = rainfallRes.getItems().stream().flatMap(rl -> rl.getReadings().stream().filter(r -> r.getStation_id().equals(station.get().getDevice_id()))).collect(Collectors.toList()).stream().findFirst();
            var time = rainfallRes.getItems().stream().findFirst().get().getTimestamp().toLocalDateTime();
            var amount = readings.get().getValue();
            return CustomResponse.builder()
                    .locationName(location)
                    .time(time.getHour() + ":" + time.getMinute())
                    .amount(amount)
                    .status(amount > 0 ? "Raining": "Not Raining")
                    .build();
        } else {
            throw new RuntimeException("Given location {} data is not found");
        }
    }
}
