package com.tangr.jdpulltorefresh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private JDPullToRefreshRecyclerview mRecyclerview;
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatas();
        mRecyclerview = (JDPullToRefreshRecyclerview) findViewById(R.id.custom_pull_recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        final CommonAdapter adapter = new CommonAdapter<String>(this, R.layout.list_item, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.item_tv, s);
                holder.setImageResource(R.id.item_iv, R.mipmap.ic_launcher);
            }
        };
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setOnRefreshListener(new JDPullToRefreshRecyclerview.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mDatas.clear();
                        refreshDatas();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                mRecyclerview.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }
        });
    }

    private void initDatas()
    {
        for (int i = 0; i < 10; i++)
        {
            mDatas.add("买买买!");
        }
    }

    private void refreshDatas()
    {
        for (int i = 0; i < 10; i++)
        {
            mDatas.add("剁剁剁!");
        }
    }
}
