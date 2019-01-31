package com.aidl.custom;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.aidl.custom.adapter.PersonAdapter;
import com.test.aidl.IMyAidl;
import com.test.aidl.bean.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    private IMyAidl mAidl;
    private ListView listView;
    private List<Person> dataList = new ArrayList<>();
    private PersonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        adapter = new PersonAdapter(this, dataList);
        listView.setAdapter(adapter);

    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            Log.e(getLocalClassName(), "service connected");
            mAidl = IMyAidl.Stub.asInterface(iBinder);
            mBound = true;
            if(mAidl != null){
                try{
                    dataList = mAidl.getPersonList();
                    if(dataList == null) {
                        dataList = new ArrayList<>();
                    }
                    adapter.setNewData(dataList);
                }catch (RemoteException e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(getLocalClassName(), "service disconnected");
            mBound = false;
            mAidl =null;
        }
    };

    public void addPerson(View view) {
        if(!mBound){
            attemptToBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mAidl == null) return;

        Random random = new Random();
        Person person = new Person("name"+random.nextInt(10));
        try{
            mAidl.addPerson(person);
            dataList = mAidl.getPersonList();
            adapter.setNewData(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        intent.setAction("com.aidl.service.MyAidlService");
        intent.setPackage("com.aidl.service");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

}
