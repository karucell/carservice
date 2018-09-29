package com.carservice.common;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@Import({CommonBeans.class})
public class TestContextConfiguration {
}
