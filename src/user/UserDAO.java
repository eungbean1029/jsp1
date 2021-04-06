package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn;//데이터베이스에 접근하게 해주는 하나의 객체
	private PreparedStatement pstmt;
	private ResultSet rs;//정보를 담을 수 있는 객체 생성
	
	public UserDAO() {
		try {//예외처리
			String dbURL = "jdbc:mysql://localhost:3306/BBS";//localhost는 본인 컴퓨터의 주소
			String dbID = "root";
			String dbPassword = "20180313";//
			Class.forName("com.mysql.jdbc.Driver");//Driver는 mysql에 접속할 수 있도록 해주는 하나의 매개체 역할
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		}catch (Exception e) {
			e.printStackTrace();//오류 추적
		}
	}
	
	public int login(String userID, String userPassword) {//로그인을 시도하는 함수 생성
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";//실제로 데이터베이스에 입력할 명령어를 SQL로 만들어줌
		try {
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);//?에 해당하는 값을 userID로 해줌
			rs = pstmt.executeQuery();//실행한 결과를 넣어줌
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return 1;//로그인 성공
				}
				else
					return 0;//비밀번호 불일치
			}
			return -1;//아이디 없음
		}catch (Exception e) {
			e.printStackTrace();
		}
		return -2;//데이터베이스 오류
	}

}
