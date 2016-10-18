//package com.quxin.freshfun.test;
//
//import org.junit.Test;
//import rx.Observable;
//import rx.Subscriber;
//import rx.Subscription;
//import rx.functions.Action1;
//import rx.schedulers.Schedulers;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by tianmingzhao on 16/10/10.
// */
//public class RxJavaTest {
//
//    @Test
//    public void test111() {
//
//        String fileName1 = "aaa";
//        String fileName2 = "bbb";
//        String fileName3 = "ccc";
//
//        long beforeTime = System.currentTimeMillis();
//
//        String[] a = new String[]{fileName1};
//        String[] b = new String[]{fileName2};
//        String[] c = new String[]{fileName3};
//
//        Observable obj1 = Observable.from(a).subscribeOn(Schedulers.newThread());
//        Observable obj2 = Observable.from(b).subscribeOn(Schedulers.newThread());
//        Observable obj3 = Observable.from(c).subscribeOn(Schedulers.newThread());
//
//        obj1.subscribe(RxJavaTest.getAction());
//        obj2.subscribe(RxJavaTest.getAction());
//        obj3.subscribe(RxJavaTest.getAction());
//
//        long now = System.currentTimeMillis();
//
//        System.out.println((now - beforeTime)/1000 + "s");
//    }
//
//    /**
//     * 插入数据库
//     * @param s
//     */
//    private static void add(String s) {
////        try {
////            Thread.sleep(20000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
////
//        // TODO
//        System.out.println("插入数据库");
//
//        System.out.println(s + "图片完成");
//    }
//
//    /**
//     * 获取回调主线程函数
//     * @return
//     */
//    private static Action1<String> getAction () {
//        return  new Action1<String>() {
//            @Override
//            public void call(String s) {
//                new RxJavaTest().add(s);
//            }
//        };
//    }
//
//}
