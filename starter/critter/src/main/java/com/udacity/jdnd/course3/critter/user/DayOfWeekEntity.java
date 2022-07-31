package com.udacity.jdnd.course3.critter.user;

import lombok.Data;

import javax.persistence.*;
import java.time.DayOfWeek;

@Entity(name = "day_of_week")
@Data
public class DayOfWeekEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
}
