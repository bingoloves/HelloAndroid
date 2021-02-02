package cn.cqs.helloandroid;

import android.app.Activity;
import android.content.Intent;

import cn.cqs.aop.annotation.Animation;
import cn.cqs.aop.annotation.Extra;
import cn.cqs.aop.annotation.Navigate;

/**
 * Created by bingo on 2021/2/1.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 页面跳转服务
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/2/1
 */

public interface NavigationServer {
    /**
     * 跳转示例
     * <p>
     *     1、无返回值：跳转模式
     *     2、返回值是Intent(只能是Intent),则只是构建一个跳转的Intent需要自己手动startActivity(intent)
     *     注意：第一个参数必须是Context,要实现动画跳转必须是Activity，否则不生效。fragment中可以使用Context,一般不需要@Animation
     * @param activity
     * @param name
     */
    @Navigate(TwoActivity.class)
    @Animation(enterAnim = { R.anim.slide_in_from_right,R.anim.slide_out_from_left },exitAnim = {R.anim.slide_in_from_left,R.anim.slide_out_from_right})
    void moveTwo(Activity activity, @Extra("name") String name);

    @Navigate(TwoActivity.class)
    Intent moveTwo(Activity activity);
}
