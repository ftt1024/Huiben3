package com.example.huiben3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {

//    @Override
//    protected void onResume() {
//        /**
//         * 设置为横屏
//         */
//        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
//        super.onResume();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.i("ItemDetailActivity onCreate 窗口大小", "宽：" + getWindow().getWindowManager().getDefaultDisplay().getWidth() + "-高：" + getWindow().getWindowManager().getDefaultDisplay().getHeight());
        // I/ItemDetailActivity onCreate 窗口大小: 宽：1080-高：1794
        setContentView(R.layout.activity_item_detail);
//        Toolbar toolbar = findViewById(R.id.detail_toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateUpTo(new Intent(view.getContext(), ItemListActivity.class));
//                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(false);
//        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        View view = findViewById(R.id.item_detail_container);
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            arguments.putInt("width", getWindow().getWindowManager().getDefaultDisplay().getWidth());
            arguments.putInt("height", getWindow().getWindowManager().getDefaultDisplay().getHeight());
            ItemDetailFragment fragment = new ItemDetailFragment(this);
            fragment.setArguments(arguments);
            view.setOnTouchListener(fragment); // 设置fragment的触摸事件
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
        // view.setFitsSystemWindows(true);
        Log.i("数据目录", this.getLayoutInflater().getContext().getApplicationInfo().dataDir);

        Log.i("大小", "宽：" + view.getWidth() + "，高：" + view.getHeight());
        Log.i("大小", "宽：" + view.getMeasuredWidth() + "，高：" + view.getMeasuredHeight());
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        View view = findViewById(R.id.item_detail_container);
//        int[] location1 = new int[2] ;
//        view.getLocationInWindow(location1); //获取在当前窗口内的绝对坐标
//        int[] location2 = new int[2] ;
//        view.getLocationOnScreen(location2);//获取在整个屏幕内的绝对坐标
//        //do something
//        Log.i("onWindowFocusChanged", location1[0] + " " + location1[1] + " " + location2[0]+ " " + location2[1]);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
