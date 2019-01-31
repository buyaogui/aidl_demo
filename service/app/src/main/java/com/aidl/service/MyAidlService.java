package com.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.test.aidl.IMyAidl;
import com.test.aidl.bean.Person;

import java.util.ArrayList;
import java.util.List;

public class MyAidlService extends Service {

    private final String Tag = this.getClass().getSimpleName();
    private ArrayList<Person> mPersons;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("service_test", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("service_test", "onDestroy");
    }

    /**
     * 创建生产的本地Binder对象，实现AIDL定制的方法
     */
    private IBinder mIBinder = new IMyAidl.Stub() {
        @Override
        public void addPerson(Person person) throws RemoteException {
            synchronized(this) {
                if(mPersons == null){
                    mPersons = new ArrayList<>();
                }
                mPersons.add(person);
            }
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            synchronized(this){
                if(mPersons != null){
                    return mPersons;
                }
                return new ArrayList<>();
            }
        }
    };

    /**
     * 客户端与服务端绑定时的回调，返回 mIBinder 后客户端就可以通过它远程调用服务端的方法，即实现了通讯
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        mPersons = new ArrayList<>();
        Log.v(Tag, "MyAidlService onBind");
        return mIBinder;
    }
}
