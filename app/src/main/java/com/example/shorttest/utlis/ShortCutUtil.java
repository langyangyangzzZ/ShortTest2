package com.example.shorttest.utlis;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.widget.Toast;


import com.example.shorttest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 2021/4/6
 * 作者:Tiaw.
 */
public class ShortCutUtil {


    public static final String SHORTCUT_TAB_INDEX = "shortcut_tab_index";
    private ShortCutUtil() {
    }

    private volatile static  ShortCutUtil mShortCutUtil;

    public static  ShortCutUtil getInstance() {
        if (mShortCutUtil == null) {
            synchronized ( ShortCutUtil.class) {
                if (mShortCutUtil == null) {
                    mShortCutUtil = new  ShortCutUtil();
                }
            }
        }
        return mShortCutUtil;
    }


    private static int[] icons = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground,};

    private static String[] titles = {"首页", "我的", "详情", "设置"};

    private ShortcutManager sm;

    public  ShortCutUtil init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            sm = context.getSystemService(ShortcutManager.class);
        }
        return mShortCutUtil;
    }

    /**
     * 设置默认快捷方式
     *
     * @param context 上下文
     * @param ast     跳转页面
     */
    public void setShortCuts(Context context, Class ast) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ArrayList<ShortcutInfo> list = new ArrayList<>();

            for (int i = 0; i < titles.length; i++) {
                Intent intent = new Intent(context, ast);
                intent.setAction(Intent.ACTION_VIEW);
                intent.putExtra("msg", titles[i]);
                intent.putExtra(SHORTCUT_TAB_INDEX, i);
                intent.addCategory("android.intent.category.LAUNCHER");

                ShortcutInfo build = new ShortcutInfo.Builder(context, "id" + i)
                        .setShortLabel(titles[i])
                        .setLongLabel(titles[i])
                        .setIcon(Icon.createWithResource(context, icons[i]))
                        .setIntent(intent)
                        .build();
                list.add(build);

            }
            sm.setDynamicShortcuts(list);
        } else {
            Toast.makeText(context, "该设备不支持快捷方式", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * @param context       当前content
     * @param targetClass   快捷图标打开的界面
     * @param backClass     打开后按返回键返回的界面
     * @param shortCutId    shortCut 唯一id
     * @param shortCutIcon  桌面上显示的图标
     */
    public void AddShortCut(Context context, Class targetClass, Class backClass, int shortCutId, int shortCutIcon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
            if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
                Intent shortcutInfoIntent = new Intent(context, targetClass);
                shortcutInfoIntent.setAction(Intent.ACTION_VIEW);

                ShortcutInfo info = new ShortcutInfo.Builder(context, "id" + shortCutId)
                        .setIcon(Icon.createWithResource(context, shortCutIcon)).
                                setShortLabel(titles[shortCutId]).setIntent(shortcutInfoIntent).build();

                PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, new Intent(context, backClass), PendingIntent.FLAG_UPDATE_CURRENT);
                shortcutManager.requestPinShortcut(info, shortcutCallbackIntent.getIntentSender());
            }
        } else {
            Toast.makeText(context, "设备不支持在桌面创建快捷图标！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param context       上下文
     * @param cls           要跳转的页面
     * @param shortCutId    shortCut 唯一id
     * @param shortCutIcon  桌面上显示的图标
     * @param shortCutLabel 桌面图标下方显示的文字
     */
    public void updItem(Context context, Class<?> cls, int shortCutId, int shortCutIcon, String shortCutLabel) {
        Intent intent = new Intent(context, cls);
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra("msg", titles[shortCutId]);

        ShortcutInfo info = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            info = new ShortcutInfo.Builder(context, "id" + shortCutId)
                    .setIcon(Icon.createWithResource(context, shortCutIcon))
                    .setShortLabel(shortCutLabel)
                    .setIntent(intent)
                    .build();
            sm.updateShortcuts(Arrays.asList(info));
        }
    }

    /**
     * 禁止使用快捷方式
     *
     * @param index 禁止使用下标
     */
    public void remove(int index) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            sm.removeAllDynamicShortcuts();
            List<String> list = new ArrayList<String>();
            list.add("id" + index);
            sm.disableShortcuts(list);
        }
    }

    public List<ShortcutInfo> allDate() {
        List<ShortcutInfo> dynamicShortcuts = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            dynamicShortcuts = sm.getDynamicShortcuts();
        }

        return dynamicShortcuts;
    }
}
