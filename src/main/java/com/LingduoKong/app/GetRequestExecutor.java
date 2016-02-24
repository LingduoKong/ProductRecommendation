package com.LingduoKong.app;

import java.util.concurrent.Executor;

/**
 * Created by lingduokong on 2/23/16.
 */
public class GetRequestExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
