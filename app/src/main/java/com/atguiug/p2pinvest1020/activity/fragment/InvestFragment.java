package com.atguiug.p2pinvest1020.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 张永卫on 2017/3/10.
 */

public class InvestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        TextView textView = new TextView(getActivity());

        textView.setText("InvestFragment");

        textView.setGravity(Gravity.CENTER);

        return textView;
    }
}
