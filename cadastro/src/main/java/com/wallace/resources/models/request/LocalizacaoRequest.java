package com.wallace.resources.models.request;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class LocalizacaoRequest {

    @NotNull
    @Schema(description = "Latitude do restaurante", required = true)
    private Double latitude;

    @NotNull
    @Schema(description = "Longitude do restaurante", required = true)
    private Double longitude;

    public LocalizacaoRequest() {
    }

    public LocalizacaoRequest(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
