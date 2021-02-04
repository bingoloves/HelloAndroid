package cn.cqs.dialog.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by bingo on 2020/12/14.
 *
 * @Author: bingo
 * @Email: 657952166@qq.com
 * @Description: popupwindow 子项抽象实体类
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/14
 */
public class PopupMenu {
    /**
     * 默认名称，备用
     */
    private String defaultName;
    /**
     * 菜单项数据集
     */
    private String[] menus;
    /**
     * 当前菜单选择位置
     */
    private int index = -1;

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String[] getMenus() {
        return menus;
    }
    public List<String> getMenusList() {
        if (menus == null) return null;
        ArrayList<String> arrayList = new ArrayList<String>(menus.length);
        Collections.addAll(arrayList, menus);
        return arrayList;
    }
    public void setMenus(String[] menus) {
        this.menus = menus;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public PopupMenu(String[] menus) {
        this.menus = menus;
    }

    public PopupMenu(String defaultName, String[] menus) {
        this.defaultName = defaultName;
        this.menus = menus;
    }

    public PopupMenu(String defaultName, String[] menus, int index) {
        this.defaultName = defaultName;
        this.menus = menus;
        this.index = index;
    }
}
