package jp.dgz.airpressuregraph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Map;

public class GraphView extends View {
    private int color = android.R.color.holo_green_dark;
    private Paint paint = new Paint();

    private long wholeTimeInMill = 2 * 60 * 60 * 1000; // 24 Hours
    private double pressureWidth = 400; // width: 400 hPa
    private double minPressure = 800; // max: 1200 hPa


    private Model model;
    public GraphView(Context context) {
        this(context, null);

        paint = new Paint();
        paint.setColor(0xff000000);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5.0f);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    public GraphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GraphView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setModel(Model model) {
        this.model = model;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        float prevX = -1;
        float prevY = -1;

        long startTime = System.currentTimeMillis() - wholeTimeInMill;
        for (Map.Entry<Long, Double> entry : model.pressures.entrySet()) {
            float x = (entry.getKey() -  startTime) * width / wholeTimeInMill;
            float y = (float) (1 - (entry.getValue() -  minPressure) / pressureWidth) * height;
            if (prevX >= 0) {
                canvas.drawLine(prevX, prevY, x, y, paint);
            }
            prevX = x;
            prevY = y;
        }
    }
}
