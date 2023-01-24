package com.fpt.edu.herofundbackend.util;

import com.fpt.edu.herofundbackend.constant.SystemConstant;
import com.fpt.edu.herofundbackend.exception.MyBadRequestException;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Log4j2
public class DateUtils {

    private static final String PATTERN_d_MM_yyyy = "d/MM/yyyy";
    private static final String PATTERN_yyyy_MM_d = "yyyy-MM-d";

    public static LocalDate stringToLocalDateDMY(String date){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_d_MM_yyyy);
            return LocalDate.parse(date, formatter);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new MyBadRequestException(SystemConstant.Message.DATE_ERROR_FORMAT);
        }
    }

    public static LocalDate stringToLocalDateYMD(String date){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_yyyy_MM_d);
            return LocalDate.parse(date, formatter);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new MyBadRequestException(SystemConstant.Message.DATE_ERROR_FORMAT);
        }
    }

    public static LocalDateTime stringToLocalDateTimeStartDate(String strDate){
        return LocalDateTime.of(Objects.requireNonNull(stringToLocalDateDMY(strDate)), LocalTime.MIDNIGHT);
    }

    public static LocalDateTime stringToLocalDateTimeEndDate(String strDate){
        return LocalDateTime.of(Objects.requireNonNull(stringToLocalDateDMY(strDate)), LocalTime.MAX);
    }
}
