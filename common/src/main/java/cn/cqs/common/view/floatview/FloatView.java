package cn.cqs.common.view.floatview;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by bingo on 2021/1/6.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: 类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/1/6
 */
public class FloatView extends DragViewLayout {
    private ImageView floatImageView;
    private OnFloatViewIconClickListener onFloatViewIconClickListener;

    public FloatView(final Context context,@DrawableRes int floatIcon) {
        super(context);
        setClickable(true);
        floatImageView = new ImageView(context);
        floatImageView.setImageResource(floatIcon);
        floatImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFloatViewIconClickListener != null){
                    onFloatViewIconClickListener.onFloatViewClick(context);
                }
            }
        });
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        addView(floatImageView, params);
    }

    /**
     * 提供接口用于修改图标
     * @param resId
     */
    public void setFloatViewIcon(int resId){
        floatImageView.setImageResource(resId);
    }

    /**
     * 图标点击接口
     */
    public interface OnFloatViewIconClickListener{
        void onFloatViewClick(Context context);
    }
    /**
     * 设置点击事件
     * @param clickListener
     */
    public void setOnFloatViewIconClickListener(OnFloatViewIconClickListener clickListener){
        this.onFloatViewIconClickListener = clickListener;
    }
}

