package com.ameng.rxexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by ameng on 8/1/16.
 */
public class MultipleButton<T> extends ScrollView {
    private Context context;
    private LinearLayout container;

    public MultipleButton(Context context) {
        super(context);
        this.context = context;
        container = container();

        this.addView(container);
    }

    public LinearLayout container() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout v = new LinearLayout(context);
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.VERTICAL);

        return v;
    }

    public Button addButton(String text) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        Button v = new Button(context);
        v.setLayoutParams(params);
        v.setText(text);
        container.addView(v);
        return v;
    }

    public ImageView addImageView(T drawable) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView v = new ImageView(context);
        v.setLayoutParams(params);
        if (drawable instanceof Drawable) {
            v.setBackground((Drawable) drawable);
        }
        else if (drawable instanceof Bitmap) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), (Bitmap) drawable);
            v.setBackground(bitmapDrawable);
        }

        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                container.removeView(view);
            }
        });
        container.addView(v);
        return v;
    }
}
