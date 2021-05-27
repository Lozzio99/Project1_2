package group17.Graphics.Assist;

import com.sun.management.OperatingSystemMXBean;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.Set;
//import java.lang.management.OperatingSystemMXBean;


/**
 * * * FUTURE feature * * *
 * <p>
 * <p>
 * class to handle possible overloading of the memory,
 * such as heap out of memory or managing subprocesses
 */
public class PerformanceWindow extends JPanel {
    private static final JTextArea text = new JTextArea();
    private static final JScrollPane pane = new JScrollPane(text);


    /**
     * Instantiates a new Performance window.
     */
    public PerformanceWindow() {
        this.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        this.setLayout(new GridLayout(2, 1, 20, 20));
        //this.add(new JPanel());
        //this.add(new JPanel());
    }

    /**
     * Print usage.
     *
     * @param runtime the runtime
     */
    public static void printUsage(Runtime runtime) {
        long total, free, used;
        int mb = 1024 * 1024;
        total = runtime.totalMemory();
        free = runtime.freeMemory();
        used = total - free;
        log("Total Memory: " + total / mb + "MB");
        log("\tMemory Used: " + used / mb + "MB");
        log("\tMemory Free: " + free / mb + "MB");
        log("\tPercent Used: " + ((double) used / (double) total) * 100 + "%");
        log("\tPercent Free: " + ((double) free / (double) total) * 100 + "%");
    }

    /**
     * Log.
     *
     * @param message the message
     */
    public static void log(Object message) {
        text.append("\t\t" + message + "\n");
    }

    /**
     * Calc cpu int.
     *
     * @param cpuStartTime     the cpu start time
     * @param elapsedStartTime the elapsed start time
     * @param cpuCount         the cpu count
     * @return the int
     */
    public static int calcCPU(long cpuStartTime, long elapsedStartTime, int cpuCount, long threadId) {
        long end = System.nanoTime();
        long totalAvailCPUTime = cpuCount * (end - elapsedStartTime);
        long totalUsedCPUTime = ManagementFactory.getThreadMXBean().getThreadCpuTime(threadId) - cpuStartTime;
        log("Total CPU Time:" + totalUsedCPUTime + " ns.");
        log("Total Avail CPU Time:" + totalAvailCPUTime + " ns.");
        float per = ((float) totalUsedCPUTime * 100) / (float) totalAvailCPUTime;
        log(per);
        return (int) per;
    }

    /**
     * Test physical memory.
     */
    public static void testPhysicalMemory() {
        /* PHYSICAL MEMORY USAGE */
        log("\n**** Sizes in Mega Bytes ****\n");
        OperatingSystemMXBean os = (OperatingSystemMXBean)
                ManagementFactory.getOperatingSystemMXBean();
        long physicalMemorySize = os.getTotalMemorySize();
        int mb = 1024 * 1024;
        int gb = 1024 * 1024 * 1024;
        log("PHYSICAL MEMORY DETAILS \n");
        log("\ttotal physicalMemory memory : " + physicalMemorySize / mb + "MB ");
        long physicalfreeMemorySize = os.getFreeMemorySize();
        log("\ttotal free physicalMemory memory : " + physicalfreeMemorySize / mb + "MB");
        /* DISC SPACE DETAILS */
        File diskPartition = new File("C:");
        File diskPartition1 = new File("D:");
        File diskPartition2 = new File("E:");
        long totalCapacity = diskPartition.getTotalSpace() / gb;
        long totalCapacity1 = diskPartition1.getTotalSpace() / gb;
        double freePartitionSpace = diskPartition.getFreeSpace() / gb;
        double freePartitionSpace1 = diskPartition1.getFreeSpace() / gb;
        double freePartitionSpace2 = diskPartition2.getFreeSpace() / gb;
        double usablePatitionSpace = diskPartition.getUsableSpace() / gb;
        log("**** Sizes in Giga Bytes ****\n");
        log("DISC SPACE DETAILS \n");
        log("\tTotal C partition size : " + totalCapacity + "GB");
        log("\tUsable Space : " + usablePatitionSpace + "GB");
        log("\tFree Space in drive C: : " + freePartitionSpace + "GB");
        log("\tFree Space in drive D:  : " + freePartitionSpace1 + "GB");
        log("\tFree Space in drive E: " + freePartitionSpace2 + "GB");
        if (freePartitionSpace <= totalCapacity % 10 || freePartitionSpace1 <= totalCapacity1 % 10)
            log(" !!!! ALERT- PC OUT OF MEMORY !!!!");
        else
            log("no alert");
    }

    /**
     * Test mx bean threads.
     */
    public static void testMXBeanThreads() {
        log("\n **THREADS DETAILS  ** \n");
        long[] ids = ManagementFactory.getThreadMXBean().getAllThreadIds();
        ThreadInfo[] threads = ManagementFactory.getThreadMXBean().getThreadInfo(ids, true, true, 20);
        for (ThreadInfo t : threads) {
            try {
                log(t.getThreadName());
                log("Daemon: " + t.isDaemon());
                log("Priority: " + t.getPriority());
                log("Lock: " + t.getLockName());
                log("State: " + t.getThreadState());
                log("~~~~~~~~~~~~~~~~~~~~~~");
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    /**
     * Test runtime memory.
     */
    public static void testRuntimeMemory() {


        int cpuCount = ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
        log("\n \n CPU USAGE DETAILS \n\n");
        long[] ids = ManagementFactory.getThreadMXBean().getAllThreadIds();
        for (long id : ids) {
            try {
                log("THREAD : " + ManagementFactory.getThreadMXBean().getThreadInfo(id).getThreadName() + "\t");
                long startCPUTime = ManagementFactory.getThreadMXBean().getThreadCpuTime(id);
                long start = System.nanoTime();
                float cpuPercent = calcCPU(startCPUTime, start, cpuCount, id);
                log("CPU USAGE : " + cpuPercent + " % ");
                log("~~~~~~~~~~~~~~~~~~~~~~");
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    /**
     * Test and clean runtime garbage.
     */
    public static void freeRuntimeGarbage() {
        Runtime runtime;
        byte[] bytes;
        log("\n **MEMORY DETAILS  ** \n");
        runtime = Runtime.getRuntime();     // Print initial memory usage.
        printUsage(runtime);
        log("Allocating 1 MB");
        bytes = new byte[1024 * 1024];  // Allocate a 1 Megabyte and print memory usage
        printUsage(runtime);
        log("Freeing up space");
        bytes = null;
        runtime.gc();   // Invoke garbage collector to reclaim the allocated memory.
        // Wait 5 seconds to give garbage collector a chance to run
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            log(e.getMessage());
        }
        printUsage(runtime);
    }

    private static void testRuntimeThreads() {
        log("\n **THREADS DETAILS  ** \n");
        String s = Thread.currentThread().getThreadGroup().toString();
        log(s);
        Set<Thread> allThreads = Thread.getAllStackTraces().keySet();
        loopThreads(allThreads);
    }

    private static void loopThreads(Set<Thread> set) {
        for (Thread t : set) {
            try {
                log(t.getName());
                log("Daemon: " + t.isDaemon());
                log("Priority: " + t.getPriority());
                log("Group: " + t.getThreadGroup());
                log("Group count: " + t.getThreadGroup().activeCount());
                log("State: " + t.getState());
                log("~~~~~~~~~~~~~~~~~~~~~~");
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }
        try {
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }

    /**
     * Init.
     */
    public void init() {
        JPanel top = new JPanel();
        pane.setSize(600, 400);
        JButton testRuntime = new JButton("free garbage");
        testRuntime.addActionListener(x -> start(0));
        JButton runtimeMemory = new JButton("runtimeMemory usage");
        runtimeMemory.addActionListener(x -> start(1));
        JButton physicalMemory = new JButton("physical memory");
        physicalMemory.addActionListener(x -> start(2));
        JButton threadsInfo = new JButton("MXBean threads");
        threadsInfo.addActionListener(x -> start(3));
        JButton runtimeThreads = new JButton("Runtime threads");
        runtimeThreads.addActionListener(x -> start(4));
        JPanel buttons = new JPanel();
        GroupLayout group = new GroupLayout(top);
        top.setLayout(group);
        group.setAutoCreateGaps(true);
        group.setAutoCreateContainerGaps(true);
        group.setVerticalGroup(
                group.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(group.createSequentialGroup()
                                .addComponent(testRuntime)
                                .addComponent(runtimeMemory)
                                .addComponent(physicalMemory)
                                .addComponent(threadsInfo)
                                .addComponent(runtimeThreads)
                                .addContainerGap())
                        .addGroup(group.createSequentialGroup()
                                .addComponent(pane)
                        )
        );

        group.setHorizontalGroup(
                group.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(group.createSequentialGroup()
                                .addGroup(group.createParallelGroup()
                                        .addComponent(testRuntime)
                                        .addComponent(runtimeMemory)
                                        .addComponent(physicalMemory)
                                        .addComponent(threadsInfo)
                                        .addComponent(runtimeThreads))
                                .addGroup(group.createParallelGroup()
                                        .addComponent(pane))));
        top.add(buttons);
        top.add(pane);
        this.add(top);
    }

    /**
     * Start.
     *
     * @param i the
     */
    public void start(int i) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Thread t = null;
        switch (i) {
            case 0 -> t = new Thread(() -> {
                text.setText("");
                freeRuntimeGarbage();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
            case 1 -> t = new Thread(() -> {
                text.setText("");
                testRuntimeMemory();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
            case 2 -> t = new Thread(() -> {
                text.setText("");
                testPhysicalMemory();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
            case 3 -> t = new Thread(() -> {
                text.setText("");
                testMXBeanThreads();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
            case 4 -> t = new Thread(() -> {
                text.setText("");
                testRuntimeThreads();
                this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
        }
        assert t != null;
        t.start();
    }
}
