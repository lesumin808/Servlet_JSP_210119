# Servlet_JSP_210119

### form 데이터 처리
Browser -> data(Request) -> 웹컨테이너(tomcat)servlet

### doGet
form관련 태그(method="get") -> request객체(doGet()) : 보안에 약하다, 사용자 정보가 URL에 노출
### doPost
form관련 태그(method="post") -> request객체(doPost()) : 보안에 강하다, 맵핑 정보만 노출

```form action="맵핑주소" method='post or get'```

하나의 데이터를 받을 때 : getParameter
여러개의 데이터를 받을 때 : [], getParameterValues 배열로 받는다.
속성 값을 알고 싶을 때 : getParameterNames

### JDBC
java가 DB와 통신할 수 있게 해주는 API
데이터베이스 LIB를 eclipse에 복사해야 사용 가능

Class.forNmae(driver) - DBdriver 로딩
con = DriverManager.getConnection(url, id, pw); - Connection
stmt = con.createStatement(); - Statement
String sql = "SELECT * FROM book" - query
res = stmt.executeQuery(sql); - run
