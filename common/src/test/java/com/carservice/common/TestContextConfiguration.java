package com.carservice.common;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
@ComponentScan(basePackageClasses = {CommonBeans.class})
public class TestContextConfiguration {
}
