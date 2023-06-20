import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lsn
 * @date 2023/6/7 1:58 PM
 */
public class batchForDefects4j {
    static Executor executor = new Executor();
    static String defects4jPath = "/Users/lsn/Desktop/regminer/defects4j/framework/bin/defects4j";
    static String cachePath = "/Users/lsn/Desktop/regminer/dd/BugBuilder_cache";
    static String finalPatchPath = "/Users/lsn/Desktop/regminer/dd/BugBuilder_out";
    static String bashPath = "/Users/lsn/Desktop/regminer/dd/BugBuilder/scripts/SpecialVersion.sh";

//    static String defects4jPath = "/root/defects4j/framework/bin/defects4j";
//    static String cachePath = "/root/dd/BugBuilder/cache";
//    static String finalPatchPath = "/root/dd/BugBuilder/out";
//    static String bashPath = "/root/dd/BugBuilder/bugbuilder_jar/scripts/SpecialVersion.sh";

    public static void main(String[] args){
        String pidResult = executor.exec("defects4j pids");
        String[] pids = pidResult.split("\n");
        for(String pid : pids){
//            if(pid == null || pid.equals("") || pid.equals(" ") || pid.equals("Chart") || pid.equals("Cli") || pid.equals("Codec") || pid.equals("JxPath")
//                    || pid.equals("Closure") || pid.equals("Mockito") || pid.equals("Gson") || pid.equals("Math")){
//                continue;
//            }
            if(!Objects.equals(pid, "Compress")){
                continue;
            }
            String bidResult = executor.exec("defects4j bids -p " + pid);
            String[] bids = bidResult.split("\n");
            for (String bid : bids){
//                if(bid == null || bid.equals("") || bid.equals(" ")){
//                    continue;
//                }
                if(!bid.equals("7")){
                    continue;
                }
                String infoResult = executor.exec("defects4j info -p " + pid + " -b " + bid);
                String fixId = extractString(infoResult, "Revision ID \\(fixed version\\):\n", "\n");
                String buggyId = fixId + "~1";
                try {
                    // 创建ProcessBuilder对象，并指定要执行的Shell命令
                    ProcessBuilder processBuilder = new ProcessBuilder("/bin/sh", bashPath,
                            bid,buggyId,fixId,pid,defects4jPath,cachePath);
                    // 启动进程
                    Process process = processBuilder.start();
                    // 等待脚本执行完成
                    int exitCode = process.waitFor();

                    String buggyV = cachePath + File.separator + pid + "_" + bid + "_buggy" + File.separator + "src" + File.separator + "main" + File.separator + "java";
                    String fixV = cachePath + File.separator + pid + "_" + bid + "_fix" + File.separator + "src" + File.separator + "main" + File.separator + "java";
                    String finalPatch = finalPatchPath + File.separator + pid + "_" + bid + ".txt";

//                    ExecutorService exe = Executors.newSingleThreadExecutor();
//                    CompletableFuture<String> future = new CompletableFuture<>();
//                    exe.submit(() -> {
//                        try {
//                            // 在这里执行需要设置超时的方法
//                            // 这里仅作为示例，可以替换为实际的方法逻辑
//
//                            getDiffCommit.main(new String[]{buggyV, fixV, finalPatch});
//                            future.complete("方法执行完成");
//                        } catch (InterruptedException e) {
//                            future.completeExceptionally(e);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    });
//
//                    try {
//                        String result = future.get(7200, TimeUnit.SECONDS);
////
////                        // 提交任务给执行器并设置超时
////                        Future<String> future = exe.submit(task);
////                        String result = future.get(7200, TimeUnit.SECONDS);
//                        System.out.println(result);
//                    } catch (TimeoutException e) {
//                        System.out.println("任务超时");
//                    } catch (InterruptedException | ExecutionException e) {
//                        System.out.println("任务异常：" + e.getMessage());
//                    } finally {
//                        // 关闭执行器
//                        exe.shutdown();
//                    }

//                    Timer timer = new Timer();
//
//                    // 创建一个 TimerTask 对象，定义要执行的任务
//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            // 在此处定义任务的具体操作
//                            getDiffCommit.main(new String[]{buggyV, fixV, finalPatch});
//                            cleanCache();
//
//                            System.out.println("任务执行了！");
//                        }
//                    };
//
//                    // 设定任务在指定延迟后执行，单位为毫秒
//                    long delay = 1000; // 5秒后执行任务
//                    timer.schedule(task, delay);
//
//                    // 设定超时时间
//                    long timeout = 7200000; // 10秒后超时
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            // 在超时后终止任务的执行
//                            task.cancel();
//                            cleanCache();
//                            System.out.println("任务超时！");
//                        }
//                    }, timeout);

                getDiffCommit.main(new String[]{buggyV, fixV, finalPatch});
                    cleanCache();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void cleanCache() throws IOException {
        File projectDir = new File(cachePath);
        File[] child = projectDir.listFiles();
        for (File file : child) {
            if(file.isDirectory()){
                    FileUtils.deleteDirectory(file);
            }
        }

        String url = "./src/diff";
        deleteAllFile.delFileMake(url);
        File file = new File("./src");
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File f:files){
                String path = f.getAbsolutePath();
                if(path.endsWith(".txt")){
                    deleteAllFile.deleteFile(new File(path));
                }
            }
        }
        String fileUrl = "./src/patch.txt";
        deleteAllFile.deleteFile(new File(fileUrl));
    }

    public static String extractString(String input, String start, String end) {
        String patternString = start + "([^\n]+)" + end;
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }
}
