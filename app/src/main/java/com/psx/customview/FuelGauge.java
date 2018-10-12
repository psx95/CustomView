package com.psx.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

public class FuelGauge extends View {

    private int TRANSPORT_COLOR = Color.BLUE;
    private int WORK_COLOR = Color.GREEN;
    private int TEXT_COLOR = Color.DKGRAY;
    private int SMALL_TEXT_COLOR = Color.LTGRAY;
    private int TEXT_SIZE = 40;
    private int SMALL_TEXT_SIZE = 10;
    private int VERTICAL_SAPCE_BETWEEN_TEXTS = 11;
    private float TRANSPORT_FUEL = 0;
    private float WORK_FUEL = 0;
    private boolean USE_CENTER = true;
    private float width;
    private float height;
    private double arcRadius;
    private Paint transportPaint;
    private Paint workPaint;
    private Paint textPaint, smallTextPaint;
    private RectF oval;
    private static final String TAG = FuelGauge.class.getSimpleName();

    public FuelGauge(Context context) {
        super(context);
        init();
    }

    public FuelGauge(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public FuelGauge(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init() {
        setupTransportPaint();
        setupWorkPaint();
        setupTextPaint();
        setupSmallTextPaint();
    }

    private void setupSmallTextPaint() {
        smallTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        smallTextPaint.setColor(SMALL_TEXT_COLOR);
        smallTextPaint.setTextSize(SMALL_TEXT_SIZE);
        smallTextPaint.setTextAlign(Paint.Align.CENTER);
        smallTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    private void setupTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(TEXT_COLOR);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
    }

    private void setupTransportPaint() {
        transportPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        transportPaint.setColor(TRANSPORT_COLOR);
        transportPaint.setShader(null);
        transportPaint.setStrokeWidth(70);
        transportPaint.setStyle(Paint.Style.STROKE);
        transportPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void setupWorkPaint() {
        workPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        workPaint.setColor(WORK_COLOR);
        workPaint.setShader(null);
        workPaint.setStrokeWidth(70);
        workPaint.setStyle(Paint.Style.STROKE);
        workPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void init(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.FuelGauge, 0, 0);
            TRANSPORT_COLOR = typedArray.getColor(R.styleable.FuelGauge_transport_color, Color.BLUE);
            WORK_COLOR = typedArray.getColor(R.styleable.FuelGauge_work_color, Color.GREEN);
            TRANSPORT_FUEL = typedArray.getFloat(R.styleable.FuelGauge_transport_fuel, 0.0f);
            WORK_FUEL = typedArray.getFloat(R.styleable.FuelGauge_work_fuel, 0.0f);
            USE_CENTER = typedArray.getBoolean(R.styleable.FuelGauge_useCenter, true);
            TEXT_SIZE = typedArray.getInteger(R.styleable.FuelGauge_textSize, 40);
            TEXT_SIZE = convertDpToPx(TEXT_SIZE);
            SMALL_TEXT_SIZE = typedArray.getInteger(R.styleable.FuelGauge_smallTextSize, 10);
            SMALL_TEXT_SIZE = convertDpToPx(SMALL_TEXT_SIZE);
            TEXT_COLOR = typedArray.getColor(R.styleable.FuelGauge_textColor, Color.DKGRAY);
            SMALL_TEXT_COLOR = typedArray.getColor(R.styleable.FuelGauge_smallTextColor, Color.LTGRAY);
            VERTICAL_SAPCE_BETWEEN_TEXTS = typedArray.getInteger(R.styleable.FuelGauge_verticalSpaceBetweenTexts, 11);
            VERTICAL_SAPCE_BETWEEN_TEXTS = convertDpToPx(VERTICAL_SAPCE_BETWEEN_TEXTS);
            typedArray.recycle();
        }
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        float center_x = width / 2;
        float center_y = height / 2;
        oval = new RectF();
        arcRadius = ((Math.min(width, height) / 2) * 0.8);
        float a = (float) arcRadius;
        oval.set(center_x - a, center_y - a, center_x + a, center_y + a);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "OnDraw Called");
        canvas.drawText("11", width / 2, height / 2, textPaint);
        canvas.drawText("LITRES", width / 2, height / 2 + convertDpToPx(VERTICAL_SAPCE_BETWEEN_TEXTS), smallTextPaint);
        canvas.drawArc(oval, 160, 220, USE_CENTER, transportPaint);
        canvas.drawArc(oval, 160, 110, USE_CENTER, workPaint);
    }

    private int convertDpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, getResources().getDisplayMetrics());
    }
}

