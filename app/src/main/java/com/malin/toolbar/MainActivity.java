package com.malin.toolbar;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "ActionBarSize";

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolBar();
        initListener();
        getStatusBarHeight();
        getAndroidActionBarSize();
        setOverflowButtonColor(this, android.R.color.white);
    }

    private void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Navigation click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolBar() {
        mToolbar.setTitle("标题");
        mToolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setSubtitle("副标题");
        mToolbar.setSubtitleTextColor(getResources().getColor(android.R.color.white));
        mToolbar.setLogo(R.mipmap.ic_launcher);
        mToolbar.setNavigationIcon(R.mipmap.icon_22);
        mToolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        setSupportActionBar(mToolbar);
    }

    /**
     * 获取 status bar height
     * @return
     */
    public int getStatusBarHeight() {
        //84px-->24dp--nexus6p
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG,"statusBarHeight:"+statusBarHeight);
        return statusBarHeight;
    }

    /**
     * 获取ActionBarSize的高度
     * @return
     */
    private int getAndroidActionBarSize() {
        // nexus6p
        // 跟踪主题可以找到这个值 values <dimen name="abc_action_bar_default_height_material">56dp</dimen>
        TypedArray styledAttributes =
                getApplicationContext()
                        .getTheme().
                        obtainStyledAttributes(
                                new int[]{android.R.attr.actionBarSize}
                        );
        int ActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        Log.d(TAG, "ActionBarSize:" + ActionBarSize);
        Toast.makeText(MainActivity.this, "" + ActionBarSize, Toast.LENGTH_SHORT).show();
        return ActionBarSize;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_game: {
                Toast.makeText(MainActivity.this, "game", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_download: {
                Toast.makeText(MainActivity.this, "download", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.action_search: {
                Toast.makeText(MainActivity.this, "search", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Change android toolbar overflow icon color
     * @param activity:activity
     * @param color:your want to color
     * @link
     * http://stackoverflow.com/questions/26997231/android-lollipop-material-design-overflow-menu-icon-color
     * http://blog.csdn.net/u010351494/article/details/50360614
     */
    public static void setOverflowButtonColor(final Activity activity, final int color) {
        if (activity==null)return;
        final String overflowDescription = activity.getString(R.string.abc_action_menu_overflow_description);
        final ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (decorView==null)return;
        final ViewTreeObserver viewTreeObserver = decorView.getViewTreeObserver();
        if (viewTreeObserver==null)return;
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                final ArrayList<View> outViews = new ArrayList<View>();
                decorView.findViewsWithText(
                        outViews,
                        overflowDescription,
                        View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
                if (outViews.isEmpty()) {
                    return;
                }
                try {
                    AppCompatImageView overflow = (AppCompatImageView) outViews.get(0);
                    if (overflow != null) {
                        overflow.setColorFilter(color);
                    }
                    if (Build.VERSION.SDK_INT < 16) {
                        decorView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        decorView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                } catch (Exception e) {
                }
            }
        });
    }
}
