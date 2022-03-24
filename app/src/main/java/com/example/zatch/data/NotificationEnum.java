package com.example.zatch.data;

public enum NotificationEnum {

    ZatchMatch("재치","매치된 재치가 있습니다! 확인해 보세요."),
    GatchAllDeposit("가치","의 모든 사용자가 입금했습니다."),
    GatchMemberChange("가치","의 가치원 변동이 있습니다. 입금상태를 재확인 해주세요."),
    MeetingAlarm("거래약속","거래가 있어요. 잊지 않으셨죠?"),
    FinishExchange("거래완료","님과의 거래는 어떠셨나요? 다른 분들을 위해 한 줄 후기를 남겨주세요."),
    RequestCertification("2차인증","2차 인증 사진이 접수되었습니다. 관리자가 꼼꼼히 검토한 후에 %s님을 더욱 신뢰있는 사용자가 될 수 있도록 도울게요."),
    FinishCertification("인증완료","축하합니다! 2차 인증이 완료되었습니다. 믿음지수 +10을 획득하셨습니다.");

    private String serviceTitle;
    private String message;

    NotificationEnum(String serviceTitle, String message){
        this.serviceTitle = serviceTitle;
        this.message = message;
    }

    public String getServiceTitle() {
        return serviceTitle;
    }

    public String getMessage() {
        return message;
    }
}
