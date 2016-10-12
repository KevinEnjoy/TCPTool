package net.tcp.tcptool;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.hoang8f.android.segmented.SegmentedGroup;


public class FragmentTCP extends Fragment implements ViewPager.OnPageChangeListener
        , View.OnClickListener {

    private final String TAG = getClass().getName();
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvIP)
    TextView tvIP;
    private Unbinder unbinder;
    private ActContainer context;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.radioMyfood)
    RadioButton radioMyfood;
    @BindView(R.id.radioClassify)
    RadioButton radioClassify;
    @BindView(R.id.segmentedGroup)
    SegmentedGroup segmentedGroup;

    TextView tv;
    Spinner spinner;
    List<String[]> allCmdData;
    MyAdapter adapter;

    private String ip, port, mac;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fm_tcp, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        context = (ActContainer) getActivity();

        Bundle bundle = context.getIntent().getExtras();
        ip = bundle.getString("ip");
        port = bundle.getString("port");
        mac = bundle.getString("mac");

        tvIP.setText(ip + ":" + port);
        context.setTitleName(mac);
        viewPager.setAdapter(new NavAdapter());
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        onPageSelected(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();

        allCmdData = APP.getCmdData();
        if(adapter!=null)adapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.i(TAG, "onPageSelected: " + position);
        switch (position) {
            case 1:
                radioMyfood.setChecked(true);
                radioClassify.setChecked(false);
                break;
            case 0:
                radioMyfood.setChecked(false);
                radioClassify.setChecked(true);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.radioMyfood, R.id.radioClassify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.radioMyfood:
                viewPager.setCurrentItem(1);
                break;
            case R.id.radioClassify:
                viewPager.setCurrentItem(0);
                break;
            case R.id.buttonCMD:
                context.startFragment(FragmentManagerCMD.class.getName());
                break;
            case R.id.buttonLog:

                break;
            case R.id.buttonSend:

                break;
        }
    }

    class NavAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        public Object instantiateItem(ViewGroup container, int position) {

            View view = null;
            switch (position) {
                case 0:
                    view = context.getLayoutInflater().inflate(R.layout.tcp_send, null);
                    tv = (TextView) view.findViewById(R.id.textView);
                    spinner = (Spinner) view.findViewById(R.id.spinner);

                    adapter = new MyAdapter();
                    spinner.setAdapter(adapter);
                    view.findViewById(R.id.buttonCMD).setOnClickListener(FragmentTCP.this);
                    view.findViewById(R.id.buttonLog).setOnClickListener(FragmentTCP.this);
                    view.findViewById(R.id.buttonSend).setOnClickListener(FragmentTCP.this);
                    break;
                case 1:
                    view = context.getLayoutInflater().inflate(R.layout.tcp_receive, null);
                    break;
            }
            container.addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }
    }



    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return allCmdData.size();
        }

        @Override
        public Object getItem(int position) {
            return allCmdData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView=layoutInflater.inflate(R.layout.layout_spinner_item, null);
            if(convertView!=null) {
                TextView tv = (TextView)convertView;
                tv.setText(allCmdData.get(position)[0]);
            }
            return convertView;
        }
    }
}
