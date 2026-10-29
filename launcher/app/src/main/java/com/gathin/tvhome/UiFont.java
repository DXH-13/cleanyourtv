package com.gathin.tvhome;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.util.TypedValue;

/**
 * The typeface behind one of the font_* strings in res/values/fonts.xml, for views built in code. The string
 * points at a bundled font, or, in locales Outfit can't draw (values-vi), names a system family (#833).
 */
final class UiFont {
    private UiFont() {}

    static Typeface get(Context context, int fontString) {
        Resources res = context.getResources();
        TypedValue value = new TypedValue();
        res.getValue(fontString, value, true);
        String name = String.valueOf(value.string);
        // ponytail: Android 6/7 can't load res/font, so Outfit falls back to Roboto at the nearest weight.
        // Ship static Outfit instances through ResourcesCompat if the look matters there.
        if (name.startsWith("res/") && Build.VERSION.SDK_INT < 26) {
            String family = name.contains("extralight") ? "sans-serif-thin"
                    : name.contains("light") ? "sans-serif-light"
                    : name.contains("medium") ? "sans-serif-medium" : "sans-serif";
            return Typeface.create(family, Typeface.NORMAL);
        }
        if (name.startsWith("res/")) return res.getFont(value.resourceId);
        return Typeface.create(name, Typeface.NORMAL);
    }
}
