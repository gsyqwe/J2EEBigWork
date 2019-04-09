package com.example.demo.domain;

//SUBSCRIBE未支付,PAY已经支付,CONFIRM已经收货,UNSUBSCRIBE退餐
public enum State {
    PAY,UNSUBSCRIBE,CONFIRM,SUBSCRIBE
}
