package com.example.zatch;

public enum PNDialogMessage {

    GatchAccept("가치 채팅방으로 이동합니다.","네"),
    GatchRefuse("가치 요청을 거절하시겠습니까?","네"),
    Exit("채팅방을 나가시겠습니까?\n" +
            "채팅방을 나가면 채팅 내역은 복구되지 않습니다.","네, 확인했습니다.");

    private final String message;
    private final String positive;

    PNDialogMessage(String message, String positive) {
        this.message = message;
        this.positive = positive;
    }

    public String getMessage(){
        return this.message;
    }

    public String getPositive(){
        return this.positive;
    }
}