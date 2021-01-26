package cn.cqs.base;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bingo on 2021/1/26.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/26
 */

public class ToastUtils {
    public static void show(Context context,String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
