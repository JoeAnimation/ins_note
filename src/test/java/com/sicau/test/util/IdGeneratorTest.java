package com.sicau.test.util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sicau.ins_note.util.IdGeneratorUtil;

public class IdGeneratorTest {

    @Autowired
    private IdGeneratorUtil idGenerator;

    @Test
    public void testIdGenerator() {
        IdGeneratorUtil snowFlake = new IdGeneratorUtil(1, 3);

        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }
    }
}