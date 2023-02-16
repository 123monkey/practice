package com.practice.future.test;

import java.util.concurrent.ExecutionException;


/**
 * @author liuyazhou
 */
public class Thread10_ThenCombine {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
     /*   DeptService deptService = new DeptService();
        UserService userService = new UserService();

        //第1个任务：获取id=1的部门
        CompletableFuture<Dept> deptFuture = CompletableFuture
                .supplyAsync(() -> {
                            return deptService.getById(1);
                        }
                );

        //第2个任务：获取id=1的人员
        CompletableFuture<User> userFuture = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        //int a = 1 / 0;//出了异常就报错
                        return userService.getById(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                });

        //将上面2个任务的返回结果dept和user合并，返回新的user
        CompletableFuture<User> resultFuture = deptFuture
                .thenCombine(userFuture,
                        new BiFunction<Dept, User, User>() {
                            @Override
                            public User apply(Dept dept, User user) {
                                user.setDeptId(dept.getId());
                                user.setDeptName(dept.getName());
                                return userService.save(user);
                            }
                        }
                );

        System.out.println("线程：" + Thread.currentThread().getName() + " 结果：" + resultFuture.get());*/
    }
}