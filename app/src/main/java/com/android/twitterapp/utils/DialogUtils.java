package com.android.twitterapp.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Mohamed Elgendy.
 */

public class DialogUtils {

    public static Dialog getOkDialog(Context context, String title, String msg, String okTxt,
                                     final Runnable okAction) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);

        if (!TextUtils.isEmptyString(title))
            builder.setTitle(title);

        builder.setMessage(msg);


        builder.setPositiveButton(okTxt, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (okAction != null)
                    okAction.run();
                dialog.dismiss();

            }
        });

        return builder.create();
    }

    public static void showCancelableDialog(@NonNull Context context, @NonNull String msg,
                                            int okTxtResId, int cancelTxtResId,
                                            @NonNull final Runnable okAction, @Nullable final Runnable cancelAction) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(msg)
                .setPositiveButton(okTxtResId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        okAction.run();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(cancelTxtResId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelAction != null) cancelAction.run();
                        dialog.dismiss();
                    }
                });

        builder.show();
    }


    public static ProgressDialog getProgressDialog(
            Context context, String message, boolean canCancelable,
            boolean canceledOnTouchOutside) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(canCancelable);
        progressDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        progressDialog.setMessage(message);
        return progressDialog;
    }


    public static Snackbar getSnackBar(View view, CharSequence textMsg,
                                       View.OnClickListener onClickListener, String textOfOnClick) {

        Snackbar snackbar = Snackbar.make(view, textMsg, Snackbar.LENGTH_LONG);
        if (!TextUtils.isEmptyString(textOfOnClick) && onClickListener != null) {
            snackbar.setAction(textOfOnClick, onClickListener);
            snackbar.setActionTextColor(Color.RED);
        }
        return snackbar;
    }

}
