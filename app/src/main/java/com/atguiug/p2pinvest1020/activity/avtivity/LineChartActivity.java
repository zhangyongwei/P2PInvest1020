package com.atguiug.p2pinvest1020.activity.avtivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguiug.p2pinvest1020.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import butterknife.InjectView;

public class LineChartActivity extends BaseActivity {


    @InjectView(R.id.base_title)
    TextView baseTitle;
    @InjectView(R.id.base_back)
    ImageView baseBack;
    @InjectView(R.id.base_setting)
    ImageView baseSetting;
    @InjectView(R.id.chart)
    LineChart chart;
    @InjectView(R.id.activity_line_chart)
    RelativeLayout activityLineChart;


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

        //字体
        Typeface mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        //设置表格的表题
        chart.setDescription("我的资产");
        //背景颜色是否显示
        chart.setDrawGridBackground(false);

        //x轴
        XAxis xAxis = chart.getXAxis();
        //X轴的位置
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setTypeface(mTf);
        //设置背景表格显示
        xAxis.setDrawGridLines(true);
        //文字下面的线是否显示
        xAxis.setDrawAxisLine(true);

        //xAxis.setLabelsToSkip(1);
        //左边的轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        //第一个参数表示区间的数据，表示区间的数值是否变化
        leftAxis.setLabelCount(5, false);

        //右边的轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(10, false);
        rightAxis.setDrawGridLines(false);

        // set data 设置数据
        chart.setData((LineData) generateDataLine(100));

        // do not forget to refresh the chart
        //chart.invalidate();
        chart.animateX(750);
    }

    @Override
    public void initTitle() {

        baseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        baseSetting.setVisibility(View.GONE);
        baseTitle.setText("折线图");
    }

    @Override
    public int getLayoutid() {
        return R.layout.activity_line_chart;
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < 12; i++) {
            //entry 第一个参数是数据值 第二个参数是第几个点
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }
        //每一条线的说明
        LineDataSet d1 = new LineDataSet(e1, "New DataSet " + cnt + ", (1)");
        //连线的宽度
        d1.setLineWidth(2.5f);
        //每个坐标点的大小
        d1.setCircleSize(4.5f);
        //点击坐标时显示的坐标线的颜色
        d1.setHighLightColor(Color.rgb(240, 0, 0));
        //每个点的值是否显示
        d1.setDrawValues(false);

        /*ArrayList<Entry> e2 = new ArrayList<Entry>();
        for (int i = 0; i < 12; i++) {
            e2.add(new Entry(e1.get(i).getVal() - 30, i));
        }
        LineDataSet d2 = new LineDataSet(e2, "New DataSet " + cnt + ", (2)");
        d2.setLineWidth(2.5f);
        d2.setCircleSize(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        d2.setDrawValues(false);
*/
        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);
        //sets.add(d2);

        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
}