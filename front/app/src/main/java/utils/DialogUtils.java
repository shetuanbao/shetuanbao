package utils;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.ContextThemeWrapper;

public class DialogUtils {

    public static AlertDialog.Builder getAlertDialog(Context context, boolean isLight) {
        return new AlertDialog.Builder(
                new ContextThemeWrapper(context, getDialogTheme(isLight)));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static int getDialogTheme(boolean isLight) {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                (isLight ? android.R.style.Theme_Holo_Light_Dialog
                        : android.R.style.Theme_Holo_Dialog)
                : android.R.style.Theme_Dialog;
    }

}
