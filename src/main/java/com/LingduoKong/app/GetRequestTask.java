package com.LingduoKong.app;

import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

/**
 * Created by lingduokong on 2/23/16.
 */
public class GetRequestTask {

    private GetRequestWork work;
    private FutureTask<String> task;
    public GetRequestTask(String url, Executor executor) {
        this.work = new GetRequestWork(url);
        this.task = new FutureTask<>(work);
        executor.execute(task);
    }

    public boolean isDone() {
        return this.task.isDone();
    }
    public String getResponse() {
        try {
            return this.task.get();
        } catch(Exception e) {
            System.out.println("Error");
            System.out.println(this.work.getURL());
            throw new RuntimeException(e);
        }
    }
}
