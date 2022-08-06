//package com.mirai.lyf.bot.robot.listener.base;
//
//import love.forte.simbot.exception.ExceptionHandle;
//import love.forte.simbot.exception.ExceptionHandleContext;
//import love.forte.simbot.listener.ListenResult;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.stereotype.Component;
//
//import java.util.Objects;
//
///**
// * @author LYF
// * @create 2021-06-07 9:41
// * @desc
// **/
//@Component
//public class GlobalException implements ExceptionHandle<IllegalStateException> {
//
//    @NotNull
//    @Override
//    public ListenResult<?> doHandle(@NotNull ExceptionHandleContext<IllegalStateException> context) {
//        System.out.println(Objects.requireNonNull(context.getMainValue()).getCause().toString());
//        return ListenResult.Default;
//    }
//}
