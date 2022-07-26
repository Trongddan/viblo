package com.example.viablo.entity;

import lombok.Data;

@Data
public class ObjResponse {
    private String status;
    private String message;
    private Object data;

    public ObjResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
