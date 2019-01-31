// IMyAidl.aidl
package com.test.aidl;

// Declare any non-default types here with import statements
import com.test.aidl.bean.Person;

interface IMyAidl {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

    /**
    * 除了基本数据类型，其他类型的参数都需要标上方向类型：in(输入), out(输出), inout(输入输出)
    */
    void addPerson(in Person person);

    List<Person> getPersonList();
}
