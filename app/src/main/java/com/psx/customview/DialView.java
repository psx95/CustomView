package com.psx.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class DialView extends View {

    private static int SELECTION_COUNT = 4;
    private static String TAG = DialView.class.getSimpleName();
    private float width; // custom view width
    private float height; // custom view height
    private Paint textPaint; // for text in view
    private Paint dialPaint; // for dial in circle View
    private float radius; // Radius of circle
    private int activeSelection; // The active selection
    private final StringBuffer tempLabel = new StringBuffer(8);
    private final float[] tempResult = new float[2];

    public DialView(Context context) {
        super(context);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DialView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(40f);
        dialPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dialPaint.setColor(Color.GRAY);
        activeSelection = 0;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activeSelection = (activeSelection + 1) % SELECTION_COUNT;
                if (activeSelection >= 1) {
                    dialPaint.setColor(Color.GREEN);
                } else {
                    dialPaint.setColor(Color.GRAY);
                }
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        radius = (float) (Math.min(width, height) / 2 * 0.8);
    }

    private float[] computeXYForPosition(final int pos, final float radius) {
        float[] result = tempResult;
        Double startAngle = Math.PI * (9 / 8d);
        Double angle = startAngle + (pos * (Math.PI / 4));
        result[0] = (float) (radius * Math.cos(angle)) + (width / 2);
        result[1] = (float) (radius * Math.sin(angle) + (height / 2));
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "OnDraw Called");
        canvas.drawCircle(width / 2, height / 2, radius, dialPaint);
        final float labelRadius = radius + 20;
        StringBuffer label = tempLabel;
        for (int i = 0; i < SELECTION_COUNT; i++) {
            float[] xyData = computeXYForPosition(i, labelRadius);
            float x = xyData[0];
            float y = xyData[1];
            label.setLength(0);
            label.append(i);
            canvas.drawText(label, 0, label.length(), x, y, textPaint);
        }
        // indidcator mark
        final float markerRadius = radius - 35;
        float[] xyData = computeXYForPosition(activeSelection, markerRadius);
        float x = xyData[0];
        float y = xyData[1];
        canvas.drawCircle(x, y, 20, textPaint);
    }
}
