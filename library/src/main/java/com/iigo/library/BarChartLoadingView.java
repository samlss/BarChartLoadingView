package com.iigo.library;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author SamLeung
 * @e-mail 729717222@qq.com
 * @github https://github.com/samlss
 * @csdn https://blog.csdn.net/Samlss
 * @description A bar chart loading view that have four different bar.
 */
public class BarChartLoadingView extends View {
    private final static int DEFAULT_SIZE = 200; //200 px

    private final static int DEFAULT_BAR_NUMBER = 4;
    private final static int[] DEFAULT_SCHEME_COLORS = new int[]{
            Color.parseColor("#F47E60"),
            Color.parseColor("#E15B64"),
            Color.parseColor("#ABBD81"),
            Color.parseColor("#F8B26A"),
    };
    private List<Paint> mBarPaintList;
    private List<Path> mBarPathList;
    private List<RectF> mBarRectList;

    private int mBarNumber = DEFAULT_BAR_NUMBER;
    private int[] mSchemeColors = DEFAULT_SCHEME_COLORS;

    private float mCenterX;
    private float mCenterY;

    private Path mCalculatePath;
    private Matrix mCalculateMatrix;
    private AnimatorSet mAnimatorSet;
    private List<Animator> mValueAnimators;
    private float[] mAnimatorValues;

    public BarChartLoadingView(Context context) {
        this(context, null);
    }

    public BarChartLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarChartLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        parseAttrs(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BarChartLoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        parseAttrs(attrs);
        init();
    }

    private void parseAttrs(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BarChartLoadingView);
        mBarNumber = typedArray.getInteger(R.styleable.BarChartLoadingView_barNumber, DEFAULT_BAR_NUMBER);
        typedArray.recycle();
    }

    private void init() {
        mBarPaintList = new ArrayList<>();
        mBarPathList = new ArrayList<>();
        mBarRectList = new ArrayList<>();

        mCalculatePath = new Path();
        mCalculateMatrix = new Matrix();

        mAnimatorSet = new AnimatorSet();
        mValueAnimators = new ArrayList<>();

        initAnimators();

        initBarList();
    }

    private void initAnimators() {
        mAnimatorValues = new float[mBarNumber];

        int[] randomDelayArr = getRandomInts(0, 250, mBarNumber);
        for (int i = 0; i < mBarNumber; i++) {
            mAnimatorValues[i] = 0.05f;

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.05f, 1);
            final int finalI = i;
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mAnimatorValues[finalI] = (float) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.setStartDelay(randomDelayArr[i]);
            valueAnimator.setDuration(500);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
            valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
            mValueAnimators.add(valueAnimator);
        }

        mAnimatorSet.playTogether(mValueAnimators);
    }

    public static int[] getRandomInts(int min, int max, int n) {
        int len = max - min + 1;

        if (max < min || n > len) {
            return null;
        }

        int[] source = new int[len];
        for (int i = min; i < min + len; i++) {
            source[i - min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index;
        for (int i = 0; i < result.length; i++) {
            index = Math.abs(rd.nextInt() % len--);
            result[i] = source[index];
            source[index] = source[len];
        }
        return result;
    }

    private void initBarList(){
        for (int i = 0; i < mBarNumber; i++){
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.FILL);
            mBarPaintList.add(paint);

            mBarPathList.add( new Path());
            mBarRectList.add(new RectF());
        }

        initSchemeColors();
    }

    /**
     * Bind the scheme colors to the paint.
     * */
    private void initSchemeColors(){
        if (mSchemeColors == null){
            mSchemeColors = DEFAULT_SCHEME_COLORS;
        }

        for (int i = 0; i < mBarNumber; i++){
            Paint paint = mBarPaintList.get(i);
            paint.setColor(mSchemeColors[i % mSchemeColors.length]);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int w = widthSpecSize;
        int h = heightSpecSize;

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            w = DEFAULT_SIZE;
            h = DEFAULT_SIZE;
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            w = DEFAULT_SIZE;
            h = heightSpecSize;
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            w = widthSpecSize;
            h = DEFAULT_SIZE;
        }

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;
        initBars(w, h);

        mAnimatorSet.start();
    }

    private void initBars(int width, int height){
        float barHeight = height * 4 / 5f;
        float barWidth  = width * 2f / (3 * mBarNumber + 1);
        float gapWidth  = barWidth / 2;

        int centerY = height / 2;

        for (int i = 0; i < mBarNumber; i++){
            mBarPathList.get(i).reset();

            float left = gapWidth * (i  + 1) + i * barWidth;

            RectF rectF = mBarRectList.get(i);
            rectF.set(left, centerY - barHeight / 2,
                    left + barWidth, centerY + barHeight / 2);
            mBarPathList.get(i).addRect(rectF, Path.Direction.CW);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBarPathList.isEmpty()
                || mBarPaintList.isEmpty()){
            return;
        }

        for (int i = 0; i < mBarNumber; i++) {
            mCalculateMatrix.reset();
            mCalculatePath.reset();
            RectF rectF = mBarRectList.get(i);

            float animatedValue = mAnimatorValues[i];
            mCalculateMatrix.postScale(1, animatedValue, rectF.left + rectF.width() / 2, mCenterY + rectF.height() / 2);
            mBarPathList.get(i).transform(mCalculateMatrix, mCalculatePath);

            canvas.drawPath(mCalculatePath, mBarPaintList.get(i));
        }
    }

    /**
     * Specify how many bar you wants to set.
     * */
    public void setBarNumber(int barNumber) {
        if (barNumber <= 0){
            barNumber = DEFAULT_BAR_NUMBER;
        }

        //if old bar number equals new bar number, return.
        if (barNumber == mBarNumber){
            return;
        }

        this.mBarNumber = barNumber;

        mBarPaintList.clear();
        mBarPathList.clear();
        mValueAnimators.clear();
        mBarRectList.clear();

        initBarList();
        initBars(getWidth(), getHeight());

        stop();
        initAnimators();
        start();
    }

    /**
     * Get the bar number.
     * */
    public int getBarNumber() {
        return mBarNumber;
    }

    /**
     * To set the scheme colors of every bar.
     *
     * @param colors The colors array.
     * */
    public void setColorSchemeColors(int... colors) {
        mSchemeColors = colors;
        initSchemeColors();
        postInvalidate();
    }

    /**
     * Start animation.
     * */
    public void start(){
        if (mAnimatorSet != null){
            mAnimatorSet.start();
        }
    }

    /**
     * Stop animation.
     * */
    public void stop(){
        if (mAnimatorSet != null){
            mAnimatorSet.cancel();
        }
    }

    /**
     * Get the scheme colors.
     * */
    public int[] getColorSchemeColors() {
        return mSchemeColors;
    }

    /**
     * When you do not need to use this, you should better to call this method to release.
     * */
    public void release(){
        stop();
        for (Animator animator : mValueAnimators){
            animator.cancel();
            ((ValueAnimator)animator).removeAllUpdateListeners();
        }

        mValueAnimators.clear();

        mBarPaintList.clear();
        mBarPathList.clear();
        mBarRectList.clear();
    }
}
