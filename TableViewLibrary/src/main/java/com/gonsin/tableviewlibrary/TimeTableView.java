package com.gonsin.tableviewlibrary;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeTableView extends FrameLayout {
    private static final int TIME_TABLE_HEIGHT = 60;
    private static final int TIME_RED_LINE_HEIGHT = 2;
    private NestedScrollView nestedScrollView;
    private TextView nullLatticeText;//左上角日期栏
    private LinearLayout weekTitle;//头部日期栏
    private LinearLayout timeTitle;//左边时间
    private FrameLayout frameLayout;
    private FrameLayout day1;
    private FrameLayout day2;
    private FrameLayout day3;
    private FrameLayout day4;
    private FrameLayout day5;
    private FrameLayout day6;
    private FrameLayout day7;
    private FrameLayout frameLayout2;
    private LinearLayout weekClassLinear;//日程消息
    private List<TimeTableModel> timeTableModels;
    private int classWith;
    private View redView;
    private Timer timer;
    private int weekOfMonth;
    private HorizontalSlidingListen horizontalSlidingListen;

    public TimeTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setHorizontalSlidingListen(HorizontalSlidingListen horizontalSlidingListen) {
        this.horizontalSlidingListen = horizontalSlidingListen;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.table_layout, this, true);
        nullLatticeText = findViewById(R.id.null_text);
        weekTitle = findViewById(R.id.week_title);
        timeTitle = findViewById(R.id.time_title);
        frameLayout = findViewById(R.id.week_class_frameLayout);
        day1 = findViewById(R.id.day_1);
        day2 = findViewById(R.id.day_2);
        day3 = findViewById(R.id.day_3);
        day4 = findViewById(R.id.day_4);
        day5 = findViewById(R.id.day_5);
        day6 = findViewById(R.id.day_6);
        day7 = findViewById(R.id.day_7);
        nestedScrollView = findViewById(R.id.NestedScrollView);
        frameLayout2 = findViewById(R.id.frameLayout2);
        weekClassLinear = findViewById(R.id.week_class);
        setDefaultView();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        int delay = 60 - calendar.get(Calendar.SECOND);
        frameLayout2.addView(createRedView());
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (redView != null) {
                    frameLayout2.post(() -> frameLayout2.removeView(redView));
                }
                frameLayout2.post(() -> frameLayout2.addView(createRedView()));
                Log.i(TAG, "刷新时间指针");
            }
        }, delay * 1000, 60000);
        nestedScrollView.setOnTouchListener(new OnSwipeTouchListener(getContext(), new OnSwipeTouchListener.OnSwipeTouchListenerCallBack() {
            @Override
            public void onSwipeLeft() {
                Log.e(TAG, "onSwipeLeft: 左滑动");
                if (horizontalSlidingListen != null) {
                    removeItem();
                    weekOfMonth++;
                    weekTitle.removeAllViews();
                    for (int i = 1; i <= 7; i++) {
                        weekTitle.addView(createAWeekTitle(i));
                    }
                    horizontalSlidingListen.onLeftSliding();
                }
            }

            @Override
            public void onSwipeRight() {
                Log.e(TAG, "onSwipeLeft: 右滑动");
                if (horizontalSlidingListen != null) {
                    removeItem();
                    weekOfMonth--;
                    weekTitle.removeAllViews();
                    for (int i = 1; i <= 7; i++) {
                        weekTitle.addView(createAWeekTitle(i));
                    }
                    horizontalSlidingListen.onRightSliding();
                }

            }
        }));
    }

    private void removeItem() {
        day1.removeAllViews();
        day2.removeAllViews();
        day3.removeAllViews();
        day4.removeAllViews();
        day5.removeAllViews();
        day6.removeAllViews();
        day7.removeAllViews();
        day1.addView(createADayNullClassLinear());
        day2.addView(createADayNullClassLinear());
        day3.addView(createADayNullClassLinear());
        day4.addView(createADayNullClassLinear());
        day5.addView(createADayNullClassLinear());
        day6.addView(createADayNullClassLinear());
        day7.addView(createADayNullClassLinear());
    }

    private void setDefaultView() {
        Calendar calendar = Calendar.getInstance();
        weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        nullLatticeText.setText(TimeUtil.time(TimeUtil.MM_DD));
        for (int i = 1; i <= 7; i++) {
            weekTitle.addView(createAWeekTitle(i));
        }
        for (int i = 0; i < 24; i++) {
            timeTitle.addView(createATimeTitle(i));
        }
        day1.addView(createADayNullClassLinear());
        day2.addView(createADayNullClassLinear());
        day3.addView(createADayNullClassLinear());
        day4.addView(createADayNullClassLinear());
        day5.addView(createADayNullClassLinear());
        day6.addView(createADayNullClassLinear());
        day7.addView(createADayNullClassLinear());
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private TextView createATimeTitle(int i) {
        TextView textView = new TextView(getContext());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, height);
        textView.setLayoutParams(params);
        textView.setBackground(getContext().getDrawable(R.drawable.text_view_white_background));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(12, 100, 1, TypedValue.COMPLEX_UNIT_SP);
        }
        textView.setTextColor(getContext().getColor(R.color.black));
        String time;
        if (i < 10) {
            time = "0" + i;
        } else {
            time = String.valueOf(i);
        }
        textView.setText(time + ":" + "00");
        return textView;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private TextView createAWeekTitle(int i) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;
        textView.setLayoutParams(params);
        textView.setBackground(getContext().getDrawable(R.drawable.text_view_background));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(12, 100, 1, TypedValue.COMPLEX_UNIT_SP);
        }
        textView.setTextColor(getContext().getColor(R.color.black));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
        calendar.set(Calendar.DAY_OF_WEEK, i);
        textView.setText(TimeUtil.format(calendar.getTimeInMillis(), TimeUtil.MM_DD_EE));
        return textView;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private LinearLayout createADayNullClassLinear() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.getMeasuredWidth();
        linearLayout.setWeightSum(24);
        linearLayout.setBackgroundColor(getContext().getColor(R.color.white));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 24; i++) {
            linearLayout.addView(createANullClassLattice());
        }
        return linearLayout;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private TextView createANullClassLattice() {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
        params.weight = 1.0f;
        textView.setLayoutParams(params);
        textView.setBackground(getContext().getDrawable(R.drawable.text_view_white_background));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getContext().getColor(R.color.black));

        return textView;
    }

    public void setTimeTableModels(List<TimeTableModel> mList) {
        this.timeTableModels = mList;
        setView();
    }

    private int getAScheduleHeight(long startTime, long endTime) {
        long time = endTime - startTime;
        int min = (int) (time / (1000 * 60));
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, min, getResources().getDisplayMetrics());
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private TextView createAScheduleItem(TimeTableModel timeTableModel) {
        TextView textView = new TextView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, getAScheduleHeight(timeTableModel.getStartTime(), timeTableModel.getEndTime()));
        params.topMargin = getToStartTimeHeight(timeTableModel.getStartTime());
        textView.setLayoutParams(params);
        textView.setBackground(getContext().getDrawable(R.drawable.meeting_item_drawable));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setAutoSizeTextTypeUniformWithConfiguration(12, 100, 1, TypedValue.COMPLEX_UNIT_SP);
        }
        textView.setTextColor(getContext().getColor(R.color.black));
        textView.setText(timeTableModel.getName());
        return textView;
    }

    private int getToStartTimeHeight(long startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long time = startTime - calendar.getTimeInMillis();
        int min = (int) (time / (1000 * 60));
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, min, getResources().getDisplayMetrics());
    }

    @SuppressLint("ResourceAsColor")
    private View createRedView() {
        redView = new View(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        layoutParams.topMargin = getToStartTimeHeight(System.currentTimeMillis());
        redView.setLayoutParams(layoutParams);
        redView.setBackgroundColor(getContext().getResources().getColor(R.color.red));
        return redView;
    }

    private void setView() {
        for (TimeTableModel timeTableModel : timeTableModels) {
            switch (timeTableModel.getWeek()) {
                case 1:
                    day1.addView(createAScheduleItem(timeTableModel));
                    break;
                case 2:
                    day2.addView(createAScheduleItem(timeTableModel));
                    break;
                case 3:
                    day3.addView(createAScheduleItem(timeTableModel));
                    break;
                case 4:
                    day4.addView(createAScheduleItem(timeTableModel));
                    break;
                case 5:
                    day5.addView(createAScheduleItem(timeTableModel));
                    break;
                case 6:
                    day6.addView(createAScheduleItem(timeTableModel));
                    break;
                case 7:
                    day7.addView(createAScheduleItem(timeTableModel));
                    break;
            }
        }
    }

    private void removeAll(){
        removeAllViews();
    }
}
