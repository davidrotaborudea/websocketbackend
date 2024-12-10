package com.davidrotabor.websocketair;

import lombok.*;

@Data
@Setter

public class FlightInfo {

    private String latitude;
    private String longitude;
    private String curse;
    private String velocity;
    private String altitude;
    private String code;

    public FlightInfo(String latitude, String longitude, String curse, String velocity, String altitude, String code) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.curse = curse;
        this.velocity = velocity;
        this.altitude = altitude;
        this.code = code;
    }

    public FlightInfo() {
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCurse() {
        return curse;
    }

    public String getVelocity() {
        return velocity;
    }

    public String getAltitude() {
        return altitude;
    }

    public String getCode() {
        return code;
    }
}
