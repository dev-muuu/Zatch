package com.example.zatch;

public enum PNDialogMessage {

    GatchAccept("가치 채팅방으로 이동합니다.", "네"),
    GatchRefuse("가치 요청을 거절하시겠습니까?", "네"),
    Exit("채팅방을 나가시겠습니까?\n" +
            "채팅방을 나가면 채팅 내역은 복구되지 않습니다.", "네, 확인했습니다."),
    MakeMeeting("약속을 등록하시겠습니까?", "확인"),
    Block("님을 차단하시겠습니까?\n더 이상의 대화가 불가합니다.","네, 차단합니다."),
    Register("등록을 완료하시겠습니까?","등록 완료"),
    ImageDelete("해당 이미지를 삭제하시겠습니까?","삭제");


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