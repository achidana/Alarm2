package com.alarm.kalpan.alarmapplication;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by JohnRedmon on 4/19/18.
 * Tests self made verification
 */

public class VerifyTest {
    private VerifyActivity va = new VerifyActivity();
    @Test
    public void correctNumberNoPlus() throws Exception{
        assertEquals(true, va.validateNumber("11234567890"));
    }

    @Test
    public void correctNumberWithPlus() throws Exception{
        assertEquals(true, va.validateNumber("+11234567890"));
    }

    @Test
    public void incorrectNonnumericNoPlus() throws Exception{
        assertEquals(false, va.validateNumber("abcdefg"));
    }

    @Test
    public void incorrectNumberNonnumericWithPlus() throws Exception{
        assertEquals(false, va.validateNumber("+abcdefg"));
    }

    @Test
    public void incorrectNumberToLong() throws Exception{
        assertEquals(false, va.validateNumber("123456789012345667"));
    }

    @Test
    public void incorrectNumberToShort() throws Exception{
        assertEquals(false, va.validateNumber("123"));
    }
}
