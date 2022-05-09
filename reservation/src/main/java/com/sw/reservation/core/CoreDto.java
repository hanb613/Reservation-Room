package com.sw.reservation.core;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CoreDto {

    private int year;

    private int month;

    private int date;

    private int time;
}
