package com.company.javacafe1week;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestTest {

    @Test
    void hello() {
        com.company.javacafe1week.Test test = new com.company.javacafe1week.Test();
        Assertions.assertEquals(test.hello(), "Hello Method!");
    }
}