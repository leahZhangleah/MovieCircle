package com.example.android.moviecircle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandableTextView extends LinearLayout implements View.OnClickListener {
    private static final String TAG = "ExpandableTextView";

    public static final int DEFAULT_TEXT_COLOR = 0XFF000000;
    public static final int DEFAULT_LINE_NUM=3;
    public static final int DEFAULT_TEXT_SIZE=12;
    public static final int DEFAULT_MARGIN_TOP=10;
    private TextView mTextView;
    private ImageView mImageView;

    /**TextView字体的颜色*/
    private int textColor;
    /**TextView字体的大小*/
    private float textSize;
    /**TextView默认显示行数*/
    private int maxLine;
    /**TextView的文本内容*/
    private String text;
    /**ImageView使用的图片*/
    private Drawable mIcon;
    /**TextView所有的内容暂用的行数*/
    private int contentLine=0;
    /**是否展开*/
    private boolean isExpanded=false;

    public ExpandableTextView(Context context) {
        super(context);
        init(null,0);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs,0);
    }

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs,defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs,defStyleAttr);
    }

    private void init(AttributeSet attributeSet,int defStyleAttr){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        TypedArray array = this.getContext().obtainStyledAttributes(attributeSet,R.styleable.ExpandableTextView,defStyleAttr,0);
        textColor = array.getColor(R.styleable.ExpandableTextView_textColor,DEFAULT_TEXT_COLOR);
        textSize = array.getDimensionPixelOffset(R.styleable.ExpandableTextView_textSize,dp2px(DEFAULT_TEXT_SIZE));
        maxLine = array.getInt(R.styleable.ExpandableTextView_lines,DEFAULT_LINE_NUM);
        mIcon = array.getDrawable(R.styleable.ExpandableTextView_icon);
        text = array.getString(R.styleable.ExpandableTextView_text);
        if(mIcon == null){
            mIcon = this.getContext().getResources().getDrawable(R.drawable.ic_expand_more_black_24dp);
        }
        array.recycle();
        initViewAttribute();

    }

    private void initViewAttribute() {
        mTextView = new TextView(this.getContext());
        mTextView.setText(text);
        mTextView.setTextColor(textColor);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);

        int textHeight = mTextView.getLineHeight() * maxLine;
        LayoutParams params_text = new LayoutParams(LayoutParams.MATCH_PARENT,textHeight);
        addView(mTextView,params_text);

        mImageView = new ImageView(this.getContext());
        mImageView.setImageDrawable(mIcon);
        LayoutParams params_img=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params_img.topMargin = dp2px(DEFAULT_MARGIN_TOP);
        addView(mImageView,params_img);
        mImageView.setOnClickListener(this);
        this.setOnClickListener(this);

        this.post(new Runnable() {
            @Override
            public void run() {
                contentLine = mTextView.getLineCount();
                if(contentLine <=maxLine){
                    mImageView.setVisibility(View.GONE);
                    LayoutParams params = (LayoutParams) mTextView.getLayoutParams();
                    params.height = LayoutParams.WRAP_CONTENT;
                    mTextView.setLayoutParams(params);
                    ExpandableTextView.this.setOnClickListener(null);
                }else{
                    mTextView.setMaxLines(maxLine);
                    mTextView.setEllipsize(TextUtils.TruncateAt.END);
                    mImageView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private int dp2px(int dp){
        return (int) (this.getResources().getDisplayMetrics().density * dp + 0.5);
    }

    @Override
    public void onClick(View view) {
        if(view == mImageView || view == this){
            flexibleHeight();
        }
    }

    private void flexibleHeight() {
        isExpanded = !isExpanded;
        int textHeight = 0;
        float startDegree = 0.0f;
        float endDegree = 180.0f;
        if(isExpanded){
            textHeight = contentLine * mTextView.getLineHeight();
            mTextView.setMaxLines(contentLine);
        }else {
            textHeight = mTextView.getLineHeight()*maxLine;
            endDegree = 0.0f;
            startDegree = 180.0f;
        }

        final LayoutParams params = (LayoutParams) mTextView.getLayoutParams();
        ValueAnimator animator_textView = ValueAnimator.ofInt(mTextView.getHeight(),textHeight);
        animator_textView.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.height = (int) valueAnimator.getAnimatedValue();
                mTextView.setLayoutParams(params);
            }
        });

        ObjectAnimator animator_imageView = ObjectAnimator.ofFloat(mImageView,"rotation",startDegree,endDegree);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(500);
        animatorSet.play(animator_imageView).with(animator_textView);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(!isExpanded){
                    mTextView.setMaxLines(maxLine);
                }
            }
        });
    }

    public void setText(String text) {
        this.text = text;
        mTextView.setText(text);
    }
}






















