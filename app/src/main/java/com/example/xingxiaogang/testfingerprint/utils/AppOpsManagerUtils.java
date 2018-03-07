package com.example.xingxiaogang.testfingerprint.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * Created by mingli on 2017/5/4.
 */
public class AppOpsManagerUtils {
    /**
     * 使用{@link AppOpsManager}检查应用是否可执行某项操作
     * Note:此处通过反射使用{@link AppOpsManager}的隐藏方法checkOp(int op, int contactId, String packageName)
     * 因为{@link AppOpsManager#checkOp(String, int, String)}第一个参数使用的Operation String Constant
     * 在较低版本的SDK中缺失,例如{@link AppOpsManager#OPSTR_SYSTEM_ALERT_WINDOW}在Android-19中缺失
     */
    public static boolean isOpsAllowed(@NonNull Context context, int opsCode) {
        Method checkOp = ReflectUtils.getClassMethod(AppOpsManager.class, "checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class});
        if (checkOp != null) {
            try {
                String packageName = context.getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    AppOpsManager opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                    int mode = ((Integer) ReflectUtils.invokeClassMethod(opsManager, checkOp, opsCode, Binder.getCallingUid(), context.getPackageName())).intValue();
                    if (mode == AppOpsManager.MODE_ALLOWED) {
                        return true;
                    }
                    if (mode == AppOpsManager.MODE_DEFAULT && Build.VERSION.SDK_INT > 22) {
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**android 4.4能够解决直接开启哪些权限限制*/
    public static void setMode(@NonNull Context context, int opsCode, int mode) {
        Method setModeOp = ReflectUtils.getClassMethod(AppOpsManager.class, "setMode", new Class[]{Integer.TYPE, Integer.TYPE, String.class, Integer.TYPE});
        if (setModeOp != null) {
            try {
                String packageName = context.getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    AppOpsManager opsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
                    ReflectUtils.invokeClassMethod(opsManager, setModeOp, opsCode, Binder.getCallingUid(), context.getPackageName(), mode);
                }
            } catch (Exception e) {

            }
        }
    }
}
