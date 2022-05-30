package com.github.zzxt0019.modbus.core;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionCause {
    ErrorMessage("返回消息错误"),
    NotConnected("客户端未连接"),
    ResponseTimeout("等待响应超时"),
    RequestTimeout("等待请求超时"),
    ;
    private final String name;
}