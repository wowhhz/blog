package com.acefet.blog.service.impl;


import com.acefet.blog.service.OSInfoService;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OSInfoServiceImpl implements OSInfoService {
    private static OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    //CPU占用情况
    public static int cpuLoad() {
        double cpuLoad = osmxb.getSystemCpuLoad();
        int percentCpuLoad = (int) (cpuLoad * 100);
        return percentCpuLoad;
    }


    //内存占用情况
    public static int memoryLoad() {
        double totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();

        double value = freePhysicalMemorySize / totalvirtualMemory;
        int percentMemoryLoad = (int) ((1 - value) * 100);
        return percentMemoryLoad;
    }

    //磁盘占用情况
    public static int diskLoad(){
        File[] roots = File.listRoots();
        long totalSpace = 0,totalFreeSpace = 0;
        for (File file : roots) {
            totalSpace+=file.getTotalSpace();
            totalFreeSpace+=file.getFreeSpace();
        }
        double diskLoad =  (double)totalFreeSpace / (double)totalSpace;
        int percentDiskLoad = (int) ((1-diskLoad)*100);
        return percentDiskLoad;
    }

    @Override
    public Map getOSInfo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map map = new HashMap();
        map.put("cpuUseRate",cpuLoad());
        map.put("memoryUseRate",memoryLoad());
        map.put("diskUseRate",diskLoad());
        map.put("dateTime",sdf.format(new Date()));
        return map;
    }


    public static void main(String[] args) {
        long timeInterval = 1000;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int cpuLoad = cpuLoad();
                    int menoryLoad = memoryLoad();
                    int diskLoad = diskLoad();
                    System.out.println(cpuLoad + "," + menoryLoad+","+diskLoad);
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

    }
}
