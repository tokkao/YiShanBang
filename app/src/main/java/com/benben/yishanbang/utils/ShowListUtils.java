package com.benben.yishanbang.utils;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;

import java.util.List;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/7/23
 * Time: 16:48
 */
public class ShowListUtils {

    //显示底部菜单
    public static void show(Activity context, String title, List<String> showList, OnSelectItem mOnSelectItem) {
        BottomMenu.show((AppCompatActivity) context,title, showList, new OnMenuItemClickListener() {
            @Override
            public void onClick(String text, int index) {
                mOnSelectItem.onCallback(text, index);
            }
        });
    }

    public interface OnSelectItem {
        void onCallback(String item, int position);
    }

}
