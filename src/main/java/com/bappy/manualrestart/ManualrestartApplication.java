package com.bappy.manualrestart;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ManualrestartApplication extends SpringBootServletInitializer {
    static Map<Integer, TestThread> threadMap = new HashMap<>();
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(ManualrestartApplication.class, args);

        //start thread
        TestThread thread = new TestThread();
        thread.setName("testThread");
        ManualrestartApplication.threadMap.put(1, thread);
        thread.start();
    }


    public static void restart() {
        ApplicationArguments args = context.getBean(ApplicationArguments.class);
        System.out.println("==================Restarting..........................");
        Thread thread = new Thread(() -> {
            //closing context(main)
            context.close();
            //closing rest
            for (Map.Entry<Integer, TestThread> t : ManualrestartApplication.threadMap.entrySet()) {
                t.getValue().stop();
                System.out.println("Stopped: " + t.getValue().getName());
            }

            context = SpringApplication.run(ManualrestartApplication.class, args.getSourceArgs());
            //restart
            for (Map.Entry<Integer, TestThread> t : ManualrestartApplication.threadMap.entrySet()) {
                TestThread testThread = new TestThread();
                ManualrestartApplication.threadMap.put(t.getKey(), testThread);//new initialization
                testThread.start();
                System.out.println("Started: " + t.getValue().getName());//new name
                System.out.println("After starting (size): "+ManualrestartApplication.threadMap.entrySet().size());
            }
        });

        thread.setDaemon(false);
        thread.start();
    }

}
