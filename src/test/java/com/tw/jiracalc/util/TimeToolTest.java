package com.tw.jiracalc.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

class TimeToolTest {

    @Test
    void getWorkDay() {
        long start = 1579101032811L;
        long end = 1579187432000L;
        Double expect = 1d;

        Double actual = TimeTool.getWorkDay(start, end);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    void test_half_day() {
        long start = 1579101032811L;
        long end = 1579144232000L;
        Double expect = 0.5d;

        Double actual = TimeTool.getWorkDay(start, end);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    void test_cross_one_weekend() {
        long start = 1579101032811L;
        long end = 1579533033000L;
        Double expect = 3d;

        Double actual = TimeTool.getWorkDay(start, end);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    void test_end_at_Sat() {
        long start = 1579101032811L;
        long end =   1579360233100L;
        Double expect = 1d;

        Double actual = TimeTool.getWorkDay(start, end);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @Description("Test round up, 1.012 -> 1.0 while decimal is 1")
    void roundUp() {
        double input = 1.012;
        double expect = 1.0;
        int decimal = 1;

        double actual = TimeTool.roundUp(input, decimal);
        Assertions.assertEquals(expect, actual);
    }

    @Test
    @Description("Test round up, 1.55 -> 1.6 while decimal is 1")
    void roundUp2() {
        double input = 1.55;
        double expect = 1.6;
        int decimal = 1;

        double actual = TimeTool.roundUp(input, decimal);
        Assertions.assertEquals(expect, actual);
    }
}