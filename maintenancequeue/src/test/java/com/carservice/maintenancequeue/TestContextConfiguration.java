package com.carservice.maintenancequeue;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import com.carservice.common.CommonBeans;

@ContextConfiguration
@ComponentScan(basePackageClasses = {CommonBeans.class})
public class TestContextConfiguration {
}
