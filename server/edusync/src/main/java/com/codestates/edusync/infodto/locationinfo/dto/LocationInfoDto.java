package com.codestates.edusync.infodto.locationinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
@Setter
public class LocationInfoDto {
    @NotNull
    private String address;

    @Nullable
    private Float latitude;

    @Nullable
    private Float longitude;
}
