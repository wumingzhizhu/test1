package com.java7book.chapter2.dynamicproxy;

public class MethodInvokeTypes {
    public void invoke() {
        SampleInterface sample = new Sample();
        sample.sampleMethodInInterface();
        Sample newSample = new Sample();
        newSample.normalMethod();
        Sample.staticSampleMethod();
    }
}
