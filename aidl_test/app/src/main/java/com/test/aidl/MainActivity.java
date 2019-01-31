package com.test.aidl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.test.aidl.adapter.PersonAdapter;
import com.test.aidl.bean.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

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

        Intent intent = new Intent(getApplicationContext(), MyAidlService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);

    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //连接后拿到 Binder，转换成 AIDL，在不同进程会返回个代理
            mAidl = IMyAidl.Stub.asInterface(iBinder);
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
            mAidl =null;
        }
    };


    public void addPerson(View view) {
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
}
