package com.carservice.queue;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import com.carservice.common.CommonBeans;

@TestConfiguration
//@Configuration
@Import({
        CommonBeans.class
})
public class WebTestConfiguration {
}
