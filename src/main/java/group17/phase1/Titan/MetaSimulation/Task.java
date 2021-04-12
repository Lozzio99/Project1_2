package group17.phase1.Titan.MetaSimulation;

import group17.phase1.Titan.interfaces.Vector3dInterface;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Task implements RunnableFuture<Vector3dInterface>
{


    @Override
    public void run() {

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public Vector3dInterface get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public Vector3dInterface get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
    {
        return null;
    }
}
