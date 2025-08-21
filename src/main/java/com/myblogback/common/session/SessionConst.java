// 세션에 저장할 때 사용하는 key(문자열) 한 곳에 모아둠
// 상수로 빼두면 자동완성 지원, 오타 위험 감소, 유지보수성 증가((ex)SessionConst.USER_ID = "" 찾아서 고치면 끝)
package com.myblogback.common.session;

public class SessionConst {
    private SessionConst() {}
    public static final String USER_ID = "USER_ID"; //로그인 성공시 사용자 ID저장해줌
}
