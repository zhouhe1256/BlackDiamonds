package com.ljs.blackdiamonds;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljs.blackdiamonds.slidingmenu.SlidingFragmentActivity;
import com.ljs.fragment.ActionFragment;
import com.ljs.fragment.LeftFragment;
import com.ljs.fragment.ProjectFragment;
import com.ljs.fragment.SocialContactFragment;
import com.ljs.view.SlidingMenu;


public class MainActivity extends SlidingFragmentActivity {
    private ProjectFragment projectFragment;
    private ActionFragment actionFragment;
    private SocialContactFragment socialContactFragment;
    private Fragment mContent;
    private TextView titleView;
    private ImageView titleimageView;
    private ImageView searchimageView;
    private ImageView projectImageView;
    private ImageView actionImageView;
    private ImageView quanzImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSlidingMenu(savedInstanceState);
        initView();
        initTab();
        setListeners();
    }

    private void setListeners() {
        titleimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        searchimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        titleView = (TextView) findViewById(R.id.title);
        titleimageView = (ImageView) findViewById(R.id.title_image);
        searchimageView = (ImageView) findViewById(R.id.search);
        projectImageView = (ImageView) findViewById(R.id.project_image);
        actionImageView = (ImageView) findViewById(R.id.action_image);
        quanzImageView = (ImageView) findViewById(R.id.quanz_image);
    }

    private void initTab() {
        if (projectFragment == null) {
            projectFragment = new ProjectFragment(this);
        }
        if (actionFragment == null) {
            actionFragment = new ActionFragment();
        }
        if (socialContactFragment == null) {
            socialContactFragment = new SocialContactFragment();
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_layout, projectFragment, "1")
                .add(R.id.content_layout, actionFragment, "2")
                .add(R.id.content_layout, socialContactFragment, "3")
                .commit();


    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        getSupportFragmentManager().beginTransaction()
                .show(projectFragment)
                .hide(actionFragment)
                .hide(socialContactFragment)
                .commit();
        projectImageView.setImageResource(R.drawable.project_t);
        actionImageView.setImageResource(R.drawable.action);
        quanzImageView.setImageResource(R.drawable.quanz);
        titleView.setText("项目");
        searchimageView.setVisibility(View.VISIBLE);
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        getSupportFragmentManager().beginTransaction()
                .show(actionFragment)
                .hide(projectFragment)
                .hide(socialContactFragment)
                .commit();
        projectImageView.setImageResource(R.drawable.project);
        actionImageView.setImageResource(R.drawable.action_t);
        quanzImageView.setImageResource(R.drawable.quanz);
        titleView.setText("活动");
        searchimageView.setVisibility(View.GONE);
    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        getSupportFragmentManager().beginTransaction()
                .show(socialContactFragment)
                .hide(actionFragment)
                .hide(projectFragment)
                .commit();
        projectImageView.setImageResource(R.drawable.project);
        actionImageView.setImageResource(R.drawable.action);
        quanzImageView.setImageResource(R.drawable.quanz_t);
        titleView.setText("圈子");
        searchimageView.setVisibility(View.GONE);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.project_image:
                clickTab1Layout();
                break;
            case R.id.action_image:
                clickTab2Layout();
                break;
            case R.id.quanz_image:
                clickTab3Layout();
                break;
        }
    }

    /**
     * 初始化侧边栏
     */
    private void initSlidingMenu(Bundle savedInstanceState) {
        // 设置左侧滑动菜单
        setBehindContentView(R.layout.menu_frame_left);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, new LeftFragment()).commit();
        SlidingMenu sm = getSlidingMenu();
        sm.setMode(SlidingMenu.LEFT);
        sm.setShadowWidthRes(R.dimen.shadow_width);
        sm.setShadowDrawable(null);
        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setBehindScrollScale(0.0f);

    }


}
