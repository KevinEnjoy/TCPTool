package net.tcp.tcptool;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.draghelp.MyItemDragListener;
import com.draghelp.MyItemTouchHelperViewHolder;
import com.draghelp.MySimpleItemTouchHelperCallback;
import com.draghelp.OnMyStartDragListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.util.KeyContent;
import com.util.SharePreferencesUserHelper;
import com.view.EmptyRecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FragmentManagerCMD extends Fragment implements OnMyStartDragListener {

    private final String TAG = getClass().getName();
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.tv_hello)
    TextView tvHello;
    private Unbinder unbinder;
    private ActContainer context;

    @BindView(R.id.recyclerview)
    EmptyRecyclerView recyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private List<String[]> mItems = new ArrayList<>();

    MyRecyclerDragDemoAdapter adapter;

    Gson gson = new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fm_manager_cmd, null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        context = (ActContainer) getActivity();
        context.setTitleName("编辑命令");

        mItems = APP.getCmdData();

        adapter = new MyRecyclerDragDemoAdapter(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setEmptyView(tvHello);

        ItemTouchHelper.Callback callback = new MySimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }


    public void appendCmdData(String[] data){

        mItems.add(data);
        saveCmdData();
    }
    public void removeCmdData(int position){

        mItems.remove(position);
        saveCmdData();
    }

    public void moveCmdData(int fromPosition, int toPosition){

        Collections.swap(mItems, fromPosition, toPosition);
        saveCmdData();
    }
    public void saveCmdData(){

        String value = gson.toJson(mItems);
        SharePreferencesUserHelper.setStringValue(context,KeyContent.CMD_DATA,value);
        Log.i(TAG, "saveCmdData: "+value);
    }


    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onClick() {

        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title("创建命令")
                .customView(R.layout.layout_dialog_create, true)
                .positiveText("OK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EditText editText = (EditText) dialog.getCustomView().findViewById(R.id.editText);
                        EditText editData = (EditText) dialog.getCustomView().findViewById(R.id.editData);

                        String text = editText.getText().toString();
                        String data = editData.getText().toString();
                        Log.i(TAG, "onClick: " + text);
                        Log.i(TAG, "onClick: " + data);
                        if(text.length()==0 || data.length() == 0){
                            Toast.makeText(context,"内容不能为空",Toast.LENGTH_LONG).show();
                        }else{
                            appendCmdData(new String[]{text,data});
                            adapter.notifyDataSetChanged();
                        }
                    }
                }).build();
        dialog.show();
    }

    class MyRecyclerDragDemoAdapter extends RecyclerView.Adapter<MyItemViewHolder>
            implements MyItemDragListener {

        private final OnMyStartDragListener mDragStartListener;

        public MyRecyclerDragDemoAdapter(Context context, OnMyStartDragListener dragStartListener) {
            mDragStartListener = dragStartListener;
        }

        @Override
        public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_recycler_drag, parent, false);
            MyItemViewHolder itemViewHolder = new MyItemViewHolder(view);
            return itemViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyItemViewHolder holder, int position) {
            holder.textView.setText(mItems.get(position)[0]);
            holder.dataView.setText(mItems.get(position)[1]);
            // Start a drag whenever the handle view it touched
            holder.handleView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }

        @Override
        public void onItemDismiss(int position) {
            removeCmdData(position);
            notifyItemRemoved(position);
        }

        @Override
        public boolean onItemMove(int fromPosition, int toPosition) {
            moveCmdData(fromPosition,toPosition);
            notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        /**
         * Simple example of a view holder that implements {@link MyItemTouchHelperViewHolder} and has a
         * "handle" view that initiates a drag event when touched.
         */

    }

    class MyItemViewHolder extends RecyclerView.ViewHolder implements
            MyItemTouchHelperViewHolder {

        public final TextView textView;
        public final TextView dataView;
        public final ImageView handleView;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            dataView = (TextView) itemView.findViewById(R.id.data);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.parseColor("#bceaeaea"));
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
