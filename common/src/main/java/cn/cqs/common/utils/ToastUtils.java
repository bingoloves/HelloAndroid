package cn.cqs.common.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.cqs.base.DensityUtils;
import cn.cqs.common.R;
import cn.cqs.common.log.LogUtils;

/**
 * Created by bingo on 2021/1/28.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 自定义Toast工具
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/28
 */

public class ToastUtils {
//    private static Toast mToast;
//    /**
//     * 自定义Toast布局
//     */
//    private static View view;
//    /**
//     * 默认的padding值
//     */
//    private static int paddingLeft,paddingTop,paddingRight,paddingBottom;
    /**
     * 自定义Toast
     * @param message
     */
    public static synchronized void show(String message){
        show(AppUtils.getInstance().getApplication().getApplicationContext(),0,message);
    }
    /**
     * 自定义Toast
     * @param iconRes
     * @param message
     */
    public static synchronized void show(@DrawableRes int iconRes,String message){
        show(AppUtils.getInstance().getApplication().getApplicationContext(),iconRes,message);
    }
    /**
     * 自定义Toast
     * @param context
     * @param message
     */
    public static synchronized void show(Context context,String message){
        show(context,0,message);
    }
    /**
     * 自定义Toast
     * @param context
     * @param message
     */
    public static synchronized void show(Context context,@DrawableRes int iconRes,String message){
        Toast mToast = new Toast(context);
        mToast.setGravity(Gravity.BOTTOM,0, DensityUtils.dp2px(context,24));
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(getDefaultView(context,iconRes,message));
        mToast.show();
    }

    private static View getDefaultView(Context context, @DrawableRes int iconRes, String message){
        View view = LayoutInflater.from(context).inflate(R.layout.common_toast,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.toast_icon);
        TextView text = (TextView) view.findViewById(R.id.toast_message);
        text.setText(message);
        if (iconRes > 0){
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(iconRes);
            view.setPadding(DensityUtils.dp2px(context,30),DensityUtils.dp2px(context,10),DensityUtils.dp2px(context,30),DensityUtils.dp2px(context,10));
        }
        return view;
    }
}
