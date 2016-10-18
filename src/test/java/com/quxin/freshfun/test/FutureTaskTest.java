package com.quxin.freshfun.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by tianmingzhao on 16/10/10.
 */
public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        List list = new ArrayList();

        ExecutorService executor = Executors.newCachedThreadPool();
        Task task = new Task();
        task.setList(list);


        Future<Integer> result = executor.submit(task);
        Future<Integer> result1 = executor.submit(task);
        Future<Integer> result2 = executor.submit(task);
        Future<Integer> result3 = executor.submit(task);

        Task2 task2 = new Task2();
        task2.setList(list);
        Future<Integer> result4 = executor.submit(task2);


        List<Future<Integer>> resList = new ArrayList<Future<Integer>>();
        resList.add(result);
        resList.add(result1);
        resList.add(result2);
        resList.add(result3);
        resList.add(result4);

        result.get();
        result1.get();
        result2.get();
        result3.get();


        try {
            result4.get(3000,TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            System.out.println("图片名称" + "失败");
        }


        executor.shutdown();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }

        System.out.println("主线程在执行任务");
        System.out.println(list.toString());

        try {
            System.out.println("task运行结果"+result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");
    }
}


class Task implements Callable<Integer> {

    List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("子线程在进行计算");
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        list.add("a");
        return sum;
    }
}

class Task2 implements Callable<Integer> {

    List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public Integer call() throws Exception {

        System.out.println("子线程在进行计算");
        Thread.sleep(5000);
        int sum = 0;
        for(int i=0;i<100;i++)
            sum += i;
        list.add("b");
        return sum;
    }
}