package com.ljs.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.bjcathay.android.async.Arguments;
import com.bjcathay.android.async.ICallback;
import com.ljs.adapter.ProjectAdapter;
import com.ljs.adapter.IndustryAdapter;
import com.ljs.blackdiamonds.DetailsActivity;
import com.ljs.blackdiamonds.R;
import com.ljs.constant.ApiUrl;
import com.ljs.model.CategoriesArrayModel;
import com.ljs.model.CategoriesModel;
import com.ljs.model.ProjectModel;
import com.ljs.model.ProjectsArrayModel;
import com.ljs.view.MyListView;
import com.ljs.view.MySpinner;
import com.xiaosu.pulllayout.FooterView;
import com.xiaosu.pulllayout.PullLayout;
import com.xiaosu.pulllayout.WaterDropView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouhe on 2016/7/5.
 */
public class ProjectFragment extends Fragment implements PullLayout.OnPullCallBackListener {
    private View view;
    private MyListView listView;
    private ProjectAdapter myadapter;
    private MySpinner industrySpinner;
    private MySpinner analyzeSpinner;
    private PullLayout pullLayout;
    private CategoriesArrayModel categoriesArrayModel;
    private ProjectsArrayModel projectsArrayModel;
    private List<ProjectModel> projectModels;
    private static final int CATEGORIES = 0x0000;
    private static final int REQUIRES = 0x0001;
    private static final int PROJECT = 0x0002;
    private int page = 0;
    private int require = -1;
    private int category = -1;
    private List<CategoriesModel> categoriesModels;
    private List<CategoriesModel> categoriesModels2;
    private Context context;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case CATEGORIES:
                    categoriesModels = (List<CategoriesModel>) msg.obj;
                    List<String> list = new ArrayList<String>();
                    for(CategoriesModel i:categoriesModels){
                        list.add(i.getDisplayValue());
                    }
                    industrySpinner.initData(new IndustryAdapter(getActivity(),list),list);
                    break;
                case REQUIRES:
                    categoriesModels2 = (List<CategoriesModel>) msg.obj;
                    List<String> list2 = new ArrayList<String>();
                    for(CategoriesModel i:categoriesModels2){
                        list2.add(i.getDisplayValue());
                    }
                    analyzeSpinner.initData(new IndustryAdapter(getActivity(),list2),list2);
                    break;
                case PROJECT:
                    projectModels.addAll((List<ProjectModel>) msg.obj);
                    myadapter.notifyDataSetChanged();
                    pullLayout.finishPull();
                    break;
            }
        }
    };

    public ProjectFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.project_fragment,null,false);
        initView();
        setListeners();
        initData();
        return view;
    }


    private void setListeners() {
        pullLayout.setOnPullListener(this);

        industrySpinner.setSpinnerListener(new MySpinner.SpinnerListener() {
            @Override
            public void listener(int postion) {
               category = Integer.parseInt(categoriesModels.get(postion).getValue());
                projectModels.clear();
                if(require!=-1){
                    httpRequest(require+"",category+"",0);
                }else{
                    httpRequest("",category+"",0);
                }
                myadapter.notifyDataSetChanged();
            }
        });

        analyzeSpinner.setSpinnerListener(new MySpinner.SpinnerListener() {
            @Override
            public void listener(int postion) {
                require = Integer.parseInt(categoriesModels2.get(postion).getValue());
                projectModels.clear();
                if(category!=-1){
                    httpRequest(require+"",category+"",0);
                }else{
                    httpRequest(require+"","",0);
                }
                myadapter.notifyDataSetChanged();
            }
        });

        industrySpinner.setSpinnerOnClickListener(new MySpinner.SpinnerOnClickListener() {
            @Override
            public void listener(View v) {
                analyzeSpinner.setVisbility();
            }
        });

        analyzeSpinner.setSpinnerOnClickListener(new MySpinner.SpinnerOnClickListener() {
            @Override
            public void listener(View v) {
                industrySpinner.setVisbility();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        listView = (MyListView) view.findViewById(R.id.list);
        industrySpinner = (MySpinner) view.findViewById(R.id.project_industry);
        industrySpinner.setText("行业分类");
        analyzeSpinner = (MySpinner) view.findViewById(R.id.priject_analyze);
        analyzeSpinner.setText("需求分析");
        pullLayout = (PullLayout) view.findViewById(R.id.pulllayout);

        WaterDropView headView = (WaterDropView) LayoutInflater.from(getActivity()).inflate(com.xiaosu.pulllayout.R.layout.lay_water_drop_view, null, false);
        headView.setWaterDropColor(R.color.title_color);
        headView.setRefreshArrowColorColor(R.color.title_color);
        headView.setLoadStartColor(R.color.title_color);
        headView.setLoadEndColor(R.color.title_color);
        pullLayout.attachHeadView(headView);

        FooterView footer = new FooterView(getActivity());
        footer.setTextColor(R.color.title_color);
        footer.setIndicatorArrowColorColor(R.color.title_color);
        footer.setLoadStartColor(R.color.title_color);
        footer.setLoadEndColor(R.color.title_color);
        pullLayout.attachFooterView(footer);
    }

    private void initData(){
        projectModels = new ArrayList<ProjectModel>();
        myadapter = new ProjectAdapter(getActivity(),projectModels);
        listView.setAdapter(myadapter);

        CategoriesArrayModel.getCategories(ApiUrl.CATEGORIES).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                categoriesArrayModel = arguments.get(0);
                Message msg = handler.obtainMessage();
                msg.obj = categoriesArrayModel.getData();
                msg.what = CATEGORIES;
                handler.sendMessage(msg);
            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {

            }
        });

    CategoriesArrayModel.getCategories(ApiUrl.REQUIRES).done(new ICallback() {
        @Override
        public void call(Arguments arguments) {
            categoriesArrayModel = arguments.get(0);
            Message msg = handler.obtainMessage();
            msg.obj = categoriesArrayModel.getData();
            msg.what = REQUIRES;
            handler.sendMessage(msg);
        }
    }).fail(new ICallback() {
        @Override
        public void call(Arguments arguments) {

        }
    });
        httpRequest();
    }

    @Override
    public void onRefresh() {
        projectModels.clear();
        page =0;
        if(require!=-1){
            if(category==-1){
                httpRequest(require+"","",page);
            }else {
                httpRequest(require+"",category+"",page);
            }
        }else if(category!=-1){
            if(require==-1){
                httpRequest("",category+"",page);
            }else {
                httpRequest(require+"",category+"",page);
            }
        }else{
            httpRequest();
        }
    }

    @Override
    public void onLoad() {
        if(page<projectsArrayModel.getPage()){
            page++;
        }
        if(require!=-1){
            if(category==-1){
                httpRequest(require+"","",page);
            }else {
                httpRequest(require+"",category+"",page);
            }
        }else if(category!=-1){
            if(require==-1){
                httpRequest("",category+"",page);
            }else {
                httpRequest(require+"",category+"",page);
            }
        }else{
            httpRequest();
        }
        pullLayout.finishPull();
    }

    public void httpRequest(String require,String category,int page ){
        ProjectsArrayModel.getProjects(require,category,page).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                projectsArrayModel = arguments.get(0);
                if(projectsArrayModel==null||projectsArrayModel.getData().size()<=0||projectsArrayModel.getData()==null){
                    pullLayout.finishPull();
                }
                if(projectsArrayModel.getPage()<=1){
                    pullLayout.setPullUpEnable(false);
                }else{
                    pullLayout.setPullUpEnable(true);
                }
                Message msg = handler.obtainMessage();
                msg.what = PROJECT;
                msg.obj = projectsArrayModel.getData();
                handler.sendMessage(msg);
            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {

            }
        });
    }

    public void httpRequest(){
        ProjectsArrayModel.getProjectsAll(page).done(new ICallback() {
            @Override
            public void call(Arguments arguments) {
                projectsArrayModel = arguments.get(0);
                if(projectsArrayModel.getData().size()<=0||projectsArrayModel.getData()==null){
                    pullLayout.finishPull();
                }
                if(projectsArrayModel.getPage()<=1){
                    pullLayout.setPullUpEnable(false);
                }else{
                    pullLayout.setPullUpEnable(true);
                }
                Message msg = handler.obtainMessage();
                msg.what = PROJECT;
                msg.obj = projectsArrayModel.getData();
                handler.sendMessage(msg);
            }
        }).fail(new ICallback() {
            @Override
            public void call(Arguments arguments) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        pullLayout.autoRefresh();
    }
}
