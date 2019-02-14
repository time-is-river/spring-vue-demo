package com.casic.demo.utils;

/**
 * @author
 * @date 2019/1/16 9:38
 */
public interface Constants {

    enum ReturnCode {
        SUCCESS(1, "成功"),
        FAILURE(0, "失败");
        private int code;
        private String msg;
        ReturnCode(int code, String mag) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
    }
}
