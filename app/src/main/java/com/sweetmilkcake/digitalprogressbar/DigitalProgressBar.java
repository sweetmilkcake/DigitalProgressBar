package com.sweetmilkcake.digitalprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DigitalProgressBar extends View {

    private Paint mPaintBackCircle;
    private static final int DEFALUT_PAINT_BACK_CIRCLE_COLOR = Color.GRAY;
    private int mPaintBackCircleColor = DEFALUT_PAINT_BACK_CIRCLE_COLOR;
    private Paint mPaintFrontArc;
    private static final int DEFALUT_PAINT_FRONT_ARC_COLOR = Color.WHITE;
    private int mPaintFrontArcColor = DEFALUT_PAINT_FRONT_ARC_COLOR;
    private Paint mPaintText;
    private static final int DEFALUT_PAINT_TEXT_COLOR = Color.WHITE;
    private int mPaintTextColor = DEFALUT_PAINT_TEXT_COLOR;

    private RectF mRectF;
    private int mProgress = 0;
    private final int mMaxProgress = 100;

    private int mWidth;
    private int mHeight;

    private static final int DEFALUT_RADIUS = 20;
    private float mRadius = DEFALUT_RADIUS;
    private static final int DEFALUT_STROKE_WIDTH = 5;
    private float mStrokeWidth = DEFALUT_STROKE_WIDTH;
    private static final int DEFALUT_TEXT_SIZE = 5;
    private float mTextSize = DEFALUT_TEXT_SIZE;

    public DigitalProgressBar(Context context) {
        this(context, null);
    }

    public DigitalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DigitalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DigitalProgressBar);

            mRadius = array.getDimensionPixelSize(R.styleable.DigitalProgressBar_radius, DEFALUT_RADIUS);
            mStrokeWidth = array.getDimensionPixelSize(R.styleable.DigitalProgressBar_stroke_width, DEFALUT_STROKE_WIDTH);
            mTextSize = array.getDimensionPixelSize(R.styleable.DigitalProgressBar_text_size, DEFALUT_TEXT_SIZE);

            mPaintBackCircleColor = array.getColor(R.styleable.DigitalProgressBar_back_circle_color, DEFALUT_PAINT_BACK_CIRCLE_COLOR);
            mPaintFrontArcColor = array.getColor(R.styleable.DigitalProgressBar_front_arc_color, DEFALUT_PAINT_FRONT_ARC_COLOR);
            mPaintTextColor = array.getColor(R.styleable.DigitalProgressBar_text_color, DEFALUT_PAINT_TEXT_COLOR);
            array.recycle();
        }
        init();
    }

    private void init() {
        mPaintBackCircle = new Paint();
        mPaintBackCircle.setColor(mPaintBackCircleColor);
        mPaintBackCircle.setAntiAlias(true);
        mPaintBackCircle.setStyle(Paint.Style.STROKE);
        mPaintBackCircle.setStrokeWidth(mStrokeWidth);

        mPaintFrontArc = new Paint();
        mPaintFrontArc.setColor(mPaintFrontArcColor);
        mPaintFrontArc.setAntiAlias(true);
        mPaintFrontArc.setStyle(Paint.Style.STROKE);
        mPaintFrontArc.setStrokeWidth(mStrokeWidth);

        mPaintText = new Paint();
        mPaintText.setColor(mPaintTextColor);
        mPaintText.setAntiAlias(true);
        mPaintText.setTextSize(mTextSize);
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }

    private void initRectF() {
        if (mRectF == null) {
            mRectF = new RectF();
            int viewSize = (int) (mRadius * 2);
            int left = (mWidth - viewSize) / 2;
            int top = (mHeight - viewSize) / 2;
            int right = left + viewSize;
            int bottom = top + viewSize;

            mRectF.set(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        initRectF();
        float angle = mProgress / (float) mMaxProgress * 360;

        String text = mProgress + "%";

        canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mPaintBackCircle);
        canvas.drawArc(mRectF, -90, angle, false, mPaintFrontArc);
        canvas.drawText(text, mWidth / 2 + mStrokeWidth / 2, mHeight / 2 + 1.5f * mPaintText.getFontMetrics().bottom, mPaintText);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getRealSize(widthMeasureSpec);
        mHeight = getRealSize(heightMeasureSpec);

        setMeasuredDimension(mWidth, mHeight);
    }

    public int getRealSize(int measureSpec) {
        int result = -1;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        if (mode == MeasureSpec.AT_MOST || mode == MeasureSpec.UNSPECIFIED) {
            result = (int) (mRadius * 2 + mStrokeWidth);
        } else {
            result = size;
        }
        return result;
    }

    public synchronized void setProgress(int progress) {
        if (mProgress >= 0 && mProgress <= mMaxProgress) {
            mProgress = progress;
            invalidate();
        }
    }
}
