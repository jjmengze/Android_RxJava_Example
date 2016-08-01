package com.ameng.rxexample;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by ameng on 8/1/16.
 */
public class MultipleButton extends ScrollView {
    private Context context;
    private LinearLayout container;

    public MultipleButton(Context context) {
        super(context);
        this.context = context;
        container = container();

        this.addView(container);
    }

    public LinearLayout container(){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout v = new LinearLayout(context);
        v.setLayoutParams(params);
        v.setOrientation(LinearLayout.VERTICAL);

        return v;
    }

    public Button addButton(String text){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        Button v = new Button(context);
        v.setLayoutParams(params);
        v.setText(text);
        container.addView(v);
        return v;
    }
}
