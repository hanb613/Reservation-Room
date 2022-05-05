package com.sw.reservation.users;

import lombok.RequiredArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36";

    String stdUrl = "https://student.donga.ac.kr/Login.aspx";
    String stdInfoUrl = "https://student.donga.ac.kr/Univ/SUD/SSUD0000.aspx?m=1";

    String adminUrl = "https://gw.donga.ac.kr/";
    String adminInfoUrl = "https://student.donga.ac.kr/Univ/SUD/SSUD0000.aspx?m=1"; // 수정

    Boolean status = null;
    public void setStatus(boolean a){
        this.status = a;
    }

    public User createUser(User user){
        return loginRepository.save(user);
    }

    public ResponseEntity<User> postAdmin(UserDto userDto) throws IOException {
        User user = userDto.toEntity();


        return ResponseEntity.status(200).body(user);
    }

    public ResponseEntity<User> postStudent(UserDto userDto) throws IOException {
        User user = userDto.toEntity();

        String stdId = userDto.getId();
        String stdPw = userDto.getPassword();

        Boolean fla = false;
        setStatus(false);

        // hidden (__VIEWSTATE, __VIEWSTATEGENERATOR, __EVENTVALIDATION) 값 파싱
        Connection.Response loginPageResponse = Jsoup.connect(stdUrl)
                                                    .header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                                    .header("Content-Type", "text/html; charset=utf-8")
                                                    .method(Connection.Method.GET)
                                                    .execute();

        Map<String, String> loginTryCookie = loginPageResponse.cookies();

        Document loginPageDocument = loginPageResponse.parse();
        String viewstate = loginPageDocument.select("#__VIEWSTATE").val();
        String viewstategenerator = loginPageDocument.select("#__VIEWSTATEGENERATOR").val();
        String eventvalidation = loginPageDocument.select("#__EVENTVALIDATION").val();

        Map<String, String> data = new HashMap<>();
        data.put("txtStudentCd", stdId);
        data.put("txtPasswd", stdPw);
        data.put("__VIEWSTATE", viewstate);
        data.put("__VIEWSTATEGENERATOR", viewstategenerator);
        data.put("__EVENTVALIDATION", eventvalidation);
        data.put("ibtnLogin.x", "645");
        data.put("ibtnLogin.y", "249");

        Connection.Response response = Jsoup.connect(stdUrl)
                                            .userAgent(userAgent)
                                            .header("Referer", stdUrl)
                                            .data(data)
                                            .cookies(loginTryCookie)
                                            .method(Connection.Method.POST)
                                            .execute();

        Map<String, String> loginCookie = response.cookies();

        Document InfoPageDocument = Jsoup.connect(stdInfoUrl)
                                        .userAgent(userAgent)
                                        .cookies(loginCookie)
                                        .get();
        String stdName;
        stdName = InfoPageDocument.select("#lblKorNm").text();

        if(!stdName.equals("")) {
            fla = true;
            setStatus(true);

            System.out.println("Available user");

            user.setType("1");
            user.setName(stdName);
            createUser(user);
        } else {
            fla = false;
            setStatus(false);

            System.out.println("Denied user");
        }

        return ResponseEntity.status(200).body(user);
    }
}
