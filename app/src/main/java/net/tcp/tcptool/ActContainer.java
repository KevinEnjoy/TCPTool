package net.tcp.tcptool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

public class ActContainer extends ActBase {

    private final String TAG = getClass().getName();

    public interface ActLifeListener{
        void onActNewIntent(Intent intent);
        void onActPause();
        void onActStop();
    }
    private ActLifeListener actLifeListener;

    public ActLifeListener getActLifeListener() {
        return actLifeListener;
    }

    public void setActLifeListener(ActLifeListener actLifeListener) {
        this.actLifeListener = actLifeListener;
    }

    public static Intent newIntent(Intent intent, String fragmentName){

        intent.putExtra("fragment_name",fragmentName);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_container);

        initToolbar();
        setSystemBarDefault();
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("NAME");
        String fragment_name = bundle.getString("fragment_name");
        try {
            Class<?> fragment = Class.forName(fragment_name);
            replaceFragmentSupportV4((Fragment) fragment.newInstance());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(getActLifeListener()!=null){
            getActLifeListener().onActPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(getActLifeListener()!=null){
            getActLifeListener().onActStop();
        }
    }
    /**
     * 把FrameLayout替换为android.support.v4.app.Fragment
     * @param fragment_v4
     */
    public void replaceFragmentSupportV4(android.support.v4.app.Fragment fragment_v4){

        android.support.v4.app.FragmentManager fragmentManager =  getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out,
                        android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.container, fragment_v4)
                .commit();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(getActLifeListener()!=null){
            getActLifeListener().onActNewIntent(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startFragment(FragmentManagerCMD.class.getName());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
