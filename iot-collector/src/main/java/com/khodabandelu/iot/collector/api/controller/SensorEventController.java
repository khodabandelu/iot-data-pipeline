package com.khodabandelu.iot.collector.api.controller;


import com.khodabandelu.iot.collector.api.dto.AggregateSensorEventResponse;
import com.khodabandelu.iot.collector.api.dto.BaseResponse;
import com.khodabandelu.iot.collector.api.queries.FindOneAggregateQuery;
import com.khodabandelu.iot.collector.service.SensorEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sensorEvent")
@RequiredArgsConstructor
public class SensorEventController {
    private final SensorEventService sensorEventService;

    /**
     * {@code GET  /api/sensorEvent} : call aggregate function based on sensor events data.
     *
     * @param query is query params to fetch aggregate value
     * @return aggregate value
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<BaseResponse> aggregateEvents(FindOneAggregateQuery query) {
        try {
            var aggregateValue = this.sensorEventService.aggregateEvents(query);
            return new ResponseEntity<>(new AggregateSensorEventResponse("list task request completed successfully!", aggregateValue), HttpStatus.OK);
        } catch (Exception e) {
            var safeErrMessage = "Error while processing request to create task ";
            return new ResponseEntity<>(new BaseResponse(safeErrMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
