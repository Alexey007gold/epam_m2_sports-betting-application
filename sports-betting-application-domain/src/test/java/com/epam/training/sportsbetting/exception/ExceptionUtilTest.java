package com.epam.training.sportsbetting.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ExceptionUtilTest {

    @Test
    void shouldThrowAnExceptionWithoutNeedToDeclareItOnUncheckCall() {
        assertThrows(Exception.class, () -> ExceptionUtil.uncheck(this::throwingMethod, null));
    }

    public Object throwingMethod(Object arg) throws Exception {
        throw new Exception();
    }
}