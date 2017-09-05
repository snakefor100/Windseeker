package com.junlong.windseeker.constants;

/**
 * Created by niujunlong on 17/9/6.
 */
public class ErrorConstants {

    public static enum Errors {
        SUCCESS(0, "SUCCESS");

        private int code;
        private String message;

        private Errors(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
