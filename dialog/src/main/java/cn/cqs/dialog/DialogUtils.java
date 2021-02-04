package cn.cqs.dialog;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;

import cn.cqs.adapter.recyclerview.CommonAdapter;
import cn.cqs.adapter.recyclerview.base.ViewHolder;
import cn.cqs.dialog.bean.PopupMenu;
import per.goweii.anylayer.Align;
import per.goweii.anylayer.AnimatorHelper;
import per.goweii.anylayer.AnyLayer;
import per.goweii.anylayer.DialogLayer;
import per.goweii.anylayer.DragLayout;
import per.goweii.anylayer.Layer;
import per.goweii.anylayer.ToastLayer;

/**
 * Created by bingo on 2020/9/9 0009.
 * AnyLayer 通用弹窗的集合
 * @description 此为示例代码，可跟据项目实际需要自定义
 */
public class DialogUtils {

    /**
     * dialog的二次封装
     * @param dragStyle  可拖拽的模式
     * @param layoutId    布局Id
     * @param dataBinder  数据绑定接口
     */
    public static void showDrawDialog(DragLayout.DragStyle dragStyle, int layoutId, Layer.DataBinder dataBinder){
        int gravity = Gravity.CENTER;
        switch (dragStyle){
            case Top:
                gravity = Gravity.TOP;
                break;
            case Bottom:
                gravity = Gravity.BOTTOM;
                break;
            case Left:
                gravity = Gravity.LEFT;
                break;
            case Right:
                gravity = Gravity.RIGHT;
                break;
            case None:
                gravity = -1;
                break;
        }
        DialogLayer dialogLayer = AnyLayer.dialog()
                .contentView(layoutId)
                //是否选择避开状态栏,默认false
                .avoidStatusBar(false)
                .backgroundDimDefault()
                .dragDismiss(dragStyle);
        //数据绑定 通过Layer.getView()获取View进行数据绑定
        if (dataBinder != null)dialogLayer.bindData(dataBinder);
        if (gravity != -1) dialogLayer.gravity(gravity);
        dialogLayer.show();
    }
    /**
     * 显示默认的不带图标的toast
     * @param message
     */
    public static void showToast(@NonNull Context context,@NonNull CharSequence message){
        showToast(context, message, 0, 0.5f, 0, 1500);
    }
    /**
     * 显示toast
     * @param context
     * @param message
     * @param iconRes
     * @param alpha
     * @param backgroundDrawable
     * @param duration
     */
    public static void showToast(@NonNull Context context,@NonNull CharSequence message,int iconRes,float alpha,int backgroundDrawable,long duration){
        ToastLayer toast = new ToastLayer(context);
        toast.duration(duration);
        if (iconRes != 0){
            toast.icon(iconRes);
        }
        if (backgroundDrawable != 0){
            toast.backgroundDrawable(backgroundDrawable);
        }
        toast.message(message)
             .alpha(alpha)
             .gravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL)
             .marginBottom(dp2px(context,80))
             .animator(new Layer.AnimatorCreator() {
                 @Override
                 public Animator createInAnimator(@NonNull View target) {
                    return AnimatorHelper.createZoomAlphaInAnim(target);
                 }

                 @Override
                 public Animator createOutAnimator(@NonNull View target) {
                     return AnimatorHelper.createZoomAlphaOutAnim(target);
                 }
             })
             .show();
        //动态改变部分样式
        LinearLayout linearLayout = toast.getView(R.id.ll_container);
        linearLayout.setPadding(dp2px(context,16f), dp2px(context,6f), dp2px(context,16f), dp2px(context,6f));
        ImageView imageView = toast.getView(R.id.iv_icon);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.width = dp2px(context,22);
        layoutParams.height = dp2px(context,22);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * popup
     * @param view
     * @param layoutId
     * @return
     */
    public static void popup(View view,int layoutId){
        DialogLayer dialogLayer = AnyLayer.popup(view)
                .align(Align.Direction.VERTICAL, Align.Horizontal.ALIGN_RIGHT, Align.Vertical.BELOW, true)
                .offsetYdp(15)
                .outsideTouchedToDismiss(true)
                .outsideInterceptTouchEvent(false)
                .contentView(layoutId)
                .contentAnimator(new DialogLayer.AnimatorCreator() {
                    @Override
                    public Animator createInAnimator(@NonNull View content) {
                        return AnimatorHelper.createDelayedZoomInAnim(content, 1F, 0F);
                    }

                    @Override
                    public Animator createOutAnimator(@NonNull View content) {
                        return AnimatorHelper.createDelayedZoomOutAnim(content, 1F, 0F);
                    }
                });
        if (dialogLayer.isShow()){
            dialogLayer.dismiss();
        } else {
            dialogLayer.show();
        }
    }

    /**
     * popup
     * @param targetView  当前相对位置View
     * @param popupMenu  数据源包装类
     *@param offsetYdp   Y方向的偏移量
     */
    public static DialogLayer popupSingleSelect(final View targetView, final PopupMenu popupMenu, int offsetYdp, int layoutItem, final OnBindViewListener bindViewLisener, final DialogUtils.OnPopupClickListener popupClickListener){
        Context context = targetView.getContext();
        List<String> menus = popupMenu.getMenusList();
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_common_list,null);
        final DialogLayer dialogLayer = AnyLayer.popup(targetView)
                .align(Align.Direction.VERTICAL, Align.Horizontal.ALIGN_RIGHT, Align.Vertical.BELOW, true)
                .offsetYdp(offsetYdp)
                .backgroundDimDefault()
                .outsideTouchedToDismiss(true)
                .outsideInterceptTouchEvent(true)
                .contentView(contentView)
                .contentAnimator(new DialogLayer.AnimatorCreator() {
                    @Override
                    public Animator createInAnimator(@NonNull View content) {
                        return AnimatorHelper.createTopInAnim(content);
                    }

                    @Override
                    public Animator createOutAnimator(@NonNull View content) {
                        return AnimatorHelper.createTopOutAnim(content);
                    }
                });
        RecyclerView popupRv = contentView.findViewById(R.id.commonRv);
        popupRv.setLayoutManager(new LinearLayoutManager(context));
        ((SimpleItemAnimator)popupRv.getItemAnimator()).setSupportsChangeAnimations(false);
        CommonAdapter adapter = new CommonAdapter<String>(context,layoutItem,menus) {
            @Override
            protected void convert(ViewHolder holder, final String value, final int position) {
                if (bindViewLisener != null){
                    bindViewLisener.convert(holder,value,position);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupMenu.getIndex() != position){
                            if (popupMenu.getIndex() != -1)notifyItemChanged(popupMenu.getIndex());
                            notifyItemChanged(position);
                        }
                        popupMenu.setIndex(position);
                        targetView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogLayer.dismiss();
                                if (popupClickListener != null)popupClickListener.onClick(position,value);
                            }
                        }, 300);
                    }
                });
            }
        };
        popupRv.setAdapter(adapter);
        return dialogLayer;
    }
    /**
     * popup
     * @param view       当前相对位置View
     * @param menus      数据集
     * @param offsetYdp  Y方向的偏移量
     * @return
     * @deprecated
     */
    public static DialogLayer popup(View view, final List<String> menus, int offsetYdp, final OnPopupClickListener popupClickListener){
        Context context = view.getContext();
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_popup_list_meun,null);
        final DialogLayer dialogLayer = AnyLayer.popup(view)
                .align(Align.Direction.VERTICAL, Align.Horizontal.ALIGN_RIGHT, Align.Vertical.BELOW, true)
                .offsetYdp(offsetYdp)
                .outsideTouchedToDismiss(true)
                .outsideInterceptTouchEvent(false)
                .contentView(contentView)
                .contentAnimator(new DialogLayer.AnimatorCreator() {
                    @Override
                    public Animator createInAnimator(@NonNull View content) {
                        return AnimatorHelper.createDelayedZoomInAnim(content, 1F, 0F);
                    }

                    @Override
                    public Animator createOutAnimator(@NonNull View content) {
                        return AnimatorHelper.createDelayedZoomOutAnim(content, 1F, 0F);
                    }
                });
        RecyclerView popupRv = contentView.findViewById(R.id.rv_popup_list);
        popupRv.setLayoutManager(new LinearLayoutManager(context));
        popupRv.setAdapter(new CommonAdapter<String>(context, R.layout.layout_popup_meun_item,menus) {
            @Override
            protected void convert(ViewHolder holder, final String o, final int position) {
                holder.setText(R.id.tv_menu,o);
                holder.setVisible(R.id.line,position != menus.size()-1);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogLayer.dismiss();
                        if (popupClickListener != null){
                            popupClickListener.onClick(position,o);
                        }
                    }
                });
            }
        });
        return dialogLayer;
    }
    /**
     * 加載LoadingView
     */
    public static DialogLayer loading(){
        return AnyLayer.dialog()
                .contentView(R.layout.layout_loading_dialog)
                .outsideTouchedToDismiss(true)
                .outsideInterceptTouchEvent(false)
                .backgroundDimDefault();
    }
    /**
     * AlertDialog
     * @param title
     * @param message
     * @param onClickListener
     */
    public static void showAlertDialog(String title,String message,View.OnClickListener onClickListener){
        showAlertDialog(title,message,false,onClickListener);
    }

    /**
     * AlertDialog
     * @param title       标题信息（可为空）
     * @param message     消息信息 （不可为空）
     * @param singleBtn   是否显示单个按钮
     * @param onClickListener  确认事件
     */
    public static void showAlertDialog(final String title, final String message, final boolean singleBtn, final View.OnClickListener onClickListener){
        AnyLayer.dialog()
                .contentView(R.layout.layout_common_dialog)
                .backgroundDimDefault()
                .bindData(new Layer.DataBinder() {
                    @Override
                    public void bindData(@NonNull Layer layer) {
                        LinearLayout contentLl = layer.getView(R.id.ll_content);
                        TextView onlyTitleTv = layer.getView(R.id.tv_only_title);
                        TextView titleTv = layer.getView(R.id.tv_title);
                        TextView contentTv = layer.getView(R.id.tv_content);
                        Button cancelBtn = layer.getView(R.id.btn_cancel);
                        Button okBtn = layer.getView(R.id.btn_ok);
                        cancelBtn.setVisibility(singleBtn?View.GONE:View.VISIBLE);
                        okBtn.setVisibility(View.VISIBLE);
                        cancelBtn.setText("取消");
                        okBtn.setText("确认");
                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)){
                            titleTv.setText(title);
                            contentTv.setText(message);
                            contentLl.setVisibility(View.VISIBLE);
                            onlyTitleTv.setVisibility(View.GONE);
                        }
                        if (TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)){
                            contentLl.setVisibility(View.GONE);
                            onlyTitleTv.setVisibility(View.VISIBLE);
                            onlyTitleTv.setText(message);
                        }
                    }
                })
                .onClickToDismiss(R.id.btn_cancel)
                .onClick(new Layer.OnClickListener() {
                    @Override
                    public void onClick(@NonNull Layer layer, @NonNull View v) {
                        layer.dismiss();
                        if (onClickListener!=null)onClickListener.onClick(v);
                    }
                }, R.id.btn_ok)
                .show();
    }

    /**
     * Popup点击Item的监听
     */
    public interface OnPopupClickListener {
        void onClick(int position, String name);
    }
    /**
     * Popup列表数据绑定接口
     */
    public interface OnBindViewListener{
        void convert(ViewHolder holder, String value, int position);
    }
}
