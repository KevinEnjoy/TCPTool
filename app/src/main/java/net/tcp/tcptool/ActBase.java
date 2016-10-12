package net.tcp.tcptool;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;


public class ActBase extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initToolbar(){

        initToolbar(null);
    }
    public void initToolbar(String title){

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        setTitleName(title);
    }

    public void showToolbarMenuItem(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            Menu menu = toolbar.getMenu();
            if(menu.size()>0){
                menu.findItem(R.id.action_settings).setVisible(true);
            }
        }
    }
    public void dismissToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }
    public void setTitleName(String title){
        if(title!=null){
            TextView toolbarTitle = (TextView) findViewById(R.id.toolbarTitle);
            if(toolbarTitle!=null)toolbarTitle.setText(title);
        }
    }



    public void setToolbarBtn(View.OnClickListener listener){
//        Button toolbar_btn = (Button)findViewById(R.id.toolbar_btn);
//        if(toolbar_btn!=null){
//            toolbar_btn.setVisibility(View.VISIBLE);
//            toolbar_btn.setOnClickListener(listener);
//        }
    }

    public void setSystemBarDefault(){
        setSystemBar(R.color.colorPrimary);
    }

    public void setSystemBarDefaultTransparent(){
        setSystemBar(R.color.colorTransparent);
    }
    /**
     *
     * @param resColor 通知栏所需颜色
     */
    public void setSystemBar(int resColor){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintResource(resColor);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    public void startAct(Class name){
        Intent intent = new Intent(this,name);
        startActivity(intent);
    }
    public void startFragment(String name){
        Intent intent = new Intent(this,ActContainer.class);
        intent.putExtra("fragment_name",name);
        startActivity(intent);
    }

    public void startFragment(String name, Bundle bundle){
        Intent intent = new Intent(this,ActContainer.class);
        bundle.putString("fragment_name",name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void showToast(CharSequence message){

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }
    public void showToast(int message){

        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        /**
         * 点击非EditText区域，隐藏键盘
         */
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event)
    {
        if (v != null && (v instanceof EditText))
        {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom)
            {
                // 点击EditText的事件，忽略它。
                return false;
            } else
            {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
