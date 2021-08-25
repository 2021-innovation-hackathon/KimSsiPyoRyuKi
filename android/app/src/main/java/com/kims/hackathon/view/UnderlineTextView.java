package com.kims.hackathon.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kims.hackathon.R;

@SuppressLint("AppCompatCustomView")
public class UnderlineTextView extends TextView {

    private Context context;
    private Paint underlinePaint;

    private float width;
    private float height;

    private int underlineColor;
    private int underlineWidth;

    public UnderlineTextView(Context context) {
        super(context);
        this.context = context;
        this.underlineColor = R.color.underline_text_orange;
        this.underlineWidth = 25;
        initView();
    }

    public UnderlineTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.underlineColor = R.color.underline_text_orange;
        this.underlineWidth = 25;
        initView();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, height, width, height, underlinePaint);
    }

    private void initView(){
        underlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //underlinePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        underlinePaint.setUnderlineText(true);
        underlinePaint.setStyle(Paint.Style.STROKE);
        underlinePaint.setColor(getResources().getColor(underlineColor, context.getTheme()));
        underlinePaint.setStrokeWidth(underlineWidth);
    }
}
