package com.davidrotabor.websocketair;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/flights")
@CrossOrigin(origins = "http://localhost:3000")
public class FlightController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public FlightController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/updateFlight")
    @SendTo("/topic/flights")
    public FlightInfo updateFlightInfo(FlightInfo flightInfo) throws Exception {
        System.out.println("Actualizando información del vuelo" + flightInfo.getCode());
        System.out.println("Enviando mensaje a /topic/flights: ");
        messagingTemplate.convertAndSend("/topic/flights", flightInfo);
        return new FlightInfo(
                HtmlUtils.htmlEscape(flightInfo.getLatitude()),
                HtmlUtils.htmlEscape(flightInfo.getLongitude()),
                HtmlUtils.htmlEscape(flightInfo.getCurse()),
                HtmlUtils.htmlEscape(flightInfo.getVelocity()),
                HtmlUtils.htmlEscape(flightInfo.getAltitude()),
                HtmlUtils.htmlEscape(flightInfo.getCode())
        );
    }

    public void sendUpdate(FlightInfo flightInfo) {
        messagingTemplate.convertAndSend("/topic/flights", flightInfo);
    }

    private Map<String, FlightInfo> flightData = new ConcurrentHashMap<>();

    @PostMapping("/update")
    public ResponseEntity<String> updateFlight(@RequestBody FlightInfo flight) {
        flightData.put(flight.getCode(), flight);
        messagingTemplate.convertAndSend("/topic/flights", flight);

        return ResponseEntity.ok("Vuelo actualizado correctamente");

    }

    @GetMapping("/{flightCode}")
    public ResponseEntity<FlightInfo> getFlightInfo(@PathVariable String flightCode) {
        FlightInfo flightInfo = flightData.get(flightCode);
        return ResponseEntity.ok(flightInfo);
    }


}
