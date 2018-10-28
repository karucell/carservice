package com.carservice.queue.resource;

public class ResourcePaths {

    private static final String ROOT = "/queue";

    public static final String ADD_MAINTENANCE = ROOT+"/add";
    public static final String START_MAINTENANCE = ROOT+"/start/{maintenanceId}";
    public static final String COMPLETE_MAINTENANCE = ROOT+"/complete/{maintenanceId}";
    public static final String FETCH_MAINTENANCES = ROOT+"/";

}
