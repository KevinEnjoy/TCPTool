package net.tcp.tcptool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import info.hoang8f.android.segmented.SegmentedGroup;


public class FragmentTCP extends android.support.v4.app.Fragment implements ViewPager.OnPageChangeListener {

    private final String TAG = getClass().getName();
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.i(TAG, "onPageSelected: "+position);
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
        }
    }
    class NavAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        public Object instantiateItem(ViewGroup container, int position) {

            View view  = null;
            switch (position){
                case 0:
                    view = context.getLayoutInflater().inflate(R.layout.tcp_send,null);
                    tv = (TextView) view.findViewById(R.id.textView);
//                    view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Log.e(TAG, "onClick: ..." );
//                            tv.setText("Hi ...");
//                        }
//                    });
                    break;
                case 1:
                    view = context.getLayoutInflater().inflate(R.layout.tcp_receive,null);
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
        @Override public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View)object);
        }
    }
}
