package com.LingduoKong.app;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lingduokong on 2/23/16.
 */
public class RequestController {

    private ExecutorService executor;
    List<GetRequestTask> tasks;

    public RequestController() {
        executor = Executors.newFixedThreadPool(5);
        tasks = new ArrayList<>();
    }

    public boolean isTerminated() {
        return executor.isTerminated();
    }

    public void addTask(String url) {
        tasks.add(new GetRequestTask(url, this.executor));
    }

    public List<String> getData() {
        List<String> results = new ArrayList<>();
        while (!tasks.isEmpty()) {
            for(Iterator<GetRequestTask> it = tasks.iterator(); it.hasNext();) {
                GetRequestTask task = it.next();
                if(task.isDone()) {
                    results.add(task.getResponse());
                    it.remove();
                }
            }
            //avoid tight loop in "main" thread
            if(!tasks.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        executor.shutdown();
        return results;
    }
}
