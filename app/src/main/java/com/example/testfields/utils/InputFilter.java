package com.example.testfields.utils;

import android.text.Spanned;

public abstract class InputFilter {
    public abstract CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend);
}
