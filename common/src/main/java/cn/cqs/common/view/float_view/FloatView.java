package cn.cqs.common.view.float_view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import cn.cqs.common.R;

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

    public FloatView(Context context) {
        this(context, R.drawable.ic_debug);
    }

    public FloatView(final Context context, final int floatImgId) {
        super(context);
        setClickable(true);
        floatImageView = new ImageView(context);
        floatImageView.setImageResource(floatImgId);
        floatImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFloatViewIconClickListener != null){
                    onFloatViewIconClickListener.onFloatViewClick();
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
        void onFloatViewClick();
    }
    /**
     * 设置点击事件
     * @param clickListener
     */
    public void setOnFloatViewIconClickListener(OnFloatViewIconClickListener clickListener){
        this.onFloatViewIconClickListener = clickListener;
    }
}

