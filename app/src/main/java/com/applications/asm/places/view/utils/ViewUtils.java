package com.applications.asm.places.view.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.applications.asm.places.R;
import com.applications.asm.places.databinding.AlertDialogLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewUtils {

    public interface RequestPermissionRationaleDialog {
        void onDismissDialog();
    }

    public static void showSnackBar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    public static void showSnackBar(View view, String message) {
        showSnackBar(view, message, Snackbar.LENGTH_LONG);
    }

    public static Dialog loading(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.lock_page);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static void loaded(Dialog dialog) {
        dialog.dismiss();
    }

    public static void showRequestPermissionRationaleDialog(Context context, String message, RequestPermissionRationaleDialog requestPermissionRationaleDialog) {
        AlertDialogLayoutBinding alertDialogLayoutBinding = AlertDialogLayoutBinding.inflate(LayoutInflater.from(context), null, false);
        Picasso.get().load(R.drawable.info).fit().centerCrop().into(alertDialogLayoutBinding.iconImageView);
        alertDialogLayoutBinding.messageTextView.setText(message);
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.permission_rationale_title)
                .setView(alertDialogLayoutBinding.getRoot())
                .setPositiveButton(R.string.accept_dialog_positive_button, (dialogInterface, i) -> dialogInterface.dismiss())
                .setOnDismissListener(dialogInterface -> requestPermissionRationaleDialog.onDismissDialog())
                .show();
    }

    public static void showGeneralWarningDialog(Context context, String warning) {
        AlertDialogLayoutBinding alertDialogLayoutBinding = AlertDialogLayoutBinding.inflate(LayoutInflater.from(context), null, false);
        Picasso.get().load(R.drawable.info).fit().centerCrop().into(alertDialogLayoutBinding.iconImageView);
        alertDialogLayoutBinding.messageTextView.setText(warning);
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.warnings_title_dialog)
                .setView(alertDialogLayoutBinding.getRoot())
                .setPositiveButton(R.string.accept_dialog_positive_button, (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    public static void showGeneralErrorDialog(Context context, String message) {
        AlertDialogLayoutBinding alertDialogLayoutBinding = AlertDialogLayoutBinding.inflate(LayoutInflater.from(context), null, false);
        Picasso.get().load(R.drawable.error).fit().centerCrop().into(alertDialogLayoutBinding.iconImageView);
        alertDialogLayoutBinding.messageTextView.setText(message);
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.error_title_dialog)
                .setView(alertDialogLayoutBinding.getRoot())
                .setPositiveButton(R.string.accept_dialog_positive_button, (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }
}
