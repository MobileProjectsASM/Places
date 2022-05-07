package com.applications.asm.places.view.utils;

import android.text.TextWatcher;

public interface AfterTextChanged extends TextWatcher {
    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) { };

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) { };
}
