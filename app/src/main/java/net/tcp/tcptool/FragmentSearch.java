package net.tcp.tcptool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.modal.SearchResult;
import com.util.HandlerHelper;
import com.util.MDUtil;
import com.view.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FragmentSearch extends Fragment {

    private final String TAG = getClass().getName();
    @BindView(R.id.editTextSearch)
    EditText editTextSearch;
    @BindView(R.id.buttonSearch)
    Button buttonSearch;
    @BindView(R.id.recyclerView)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.tv_hello)
    TextView tvHello;
    private Unbinder unbinder;
    private ActContainer context;
    MaterialDialog md;

    private List<SearchResult> listData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fm_search, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        context = (ActContainer) getActivity();

        context.setTitleName("扫描设备");
        context.setToolbarBtn(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startFragment(FragmentManagerCMD.class.getName());
            }
        });
        listData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SearchResult data = new SearchResult();
            data.setIp("192.168.1." + i);
            data.setPort("998");
            data.setMac("FFFFFFFFFFFFFF" + i);
            listData.add(data);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setEmptyView(tvHello);
        recyclerView.setAdapter(new MyAdapter());
        HandlerHelper.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                context.showToolbarMenuItem();
            }
        },200);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.buttonSearch)
    public void onClick() {

        showMD();
        HandlerHelper.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissMD();
            }
        },2000);
    }


    public void showMD() {
        if (md == null) {
            md = MDUtil.getWaitingDialog(context, null).show();
        } else if (!md.isShowing()) {
            md.show();
        }
    }

    public void dismissMD() {
        if (md != null && md.isShowing()) {
            md.dismiss();
        }
    }

    public void onRecyclerItemClick(int position) {
        Log.e(TAG, "onRecyclerItemClick: " + position);

        Bundle bundle = new Bundle();
        bundle.putString("ip", listData.get(position).getIp());
        bundle.putString("port", listData.get(position).getPort());
        bundle.putString("mac", listData.get(position).getMac());
        context.startFragment(FragmentTCP.class.getName(), bundle);
    }

    class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {

            holder.tvName.setText(listData.get(position).getIp() + ":" + listData.get(position).getPort());
            holder.tvDes.setText(listData.get(position).getMac());
        }

        @Override
        public int getItemCount() {

            if (listData == null) {
                return 0;
            }
            return listData.size();
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvDes;

        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    onRecyclerItemClick(position);
                }
            });
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvDes = (TextView) itemView.findViewById(R.id.des);
        }
    }
}
