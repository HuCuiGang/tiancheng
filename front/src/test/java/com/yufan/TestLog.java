package com.yufan;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class TestLog {

    private Logger LOGGER= LoggerFactory.getLogger(TestLog.class);


    @Test
    public void test1(){

        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.error("error");
        LOGGER.warn("warn");

    }

}
