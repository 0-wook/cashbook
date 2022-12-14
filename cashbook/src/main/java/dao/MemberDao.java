package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import vo.Member;

public class MemberDao {
	
	// 회원가입 insert
	public int insertMember(Member member) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int row = 0;
		String sql = "INSERT INTO "
				+ "		member(member_id , member_name, member_gender, member_address, member_phone, member_pw, create_date) "
				+ "		VALUES(?,?,?,?,?,PASSWORD(?),now())";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://13.209.216.160/cashbook","root","1q2w3e4r");

			stmt = conn.prepareStatement(sql);
			stmt.setString(1,member.getMemberId());
			stmt.setString(2,member.getMemberName());
			stmt.setString(3,member.getMemberGender());
			stmt.setString(4,member.getMemberAddress());
			stmt.setString(5,member.getMemberPhone());
			stmt.setString(6,member.getMemberPw());
			
			row = stmt.executeUpdate();
			
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return row;
	}
	
	// 회원 수정 update
	public int updateMember(Map<String,Object> map) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int row = 0;
		String sql = "UPDATE member SET member_name=?, member_gender=?, member_address=?, member_phone=?,member_pw=password(?) WHERE member_id = ? AND member_pw=password(?)";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://13.209.216.160/cashbook","root","1q2w3e4r");

			stmt = conn.prepareStatement(sql);
			stmt.setString(1,(String)map.get("memberName"));
			stmt.setString(2,(String)map.get("memberGender"));
			stmt.setString(3,(String)map.get("memberAddress"));
			stmt.setString(4,(String)map.get("memberPhone"));
			stmt.setString(5,(String)map.get("ChangeMemberPw"));
			stmt.setString(6,(String)map.get("memberId"));
			stmt.setString(7,(String)map.get("memberPw"));
			
			row = stmt.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return row;
	}
	// 회원 삭제 delete
	public int deleteMember(Member member) {
		Connection conn = null;
		PreparedStatement hashtagStmt = null;
		PreparedStatement cashbookStmt = null;
		PreparedStatement memberStmt = null;
		int row = 0;
		// member_id가 sessionId인 곳의 해시태그 삭제 --> cashbook의 외래키와 연결돼있어서 조인해서 삭제해줌
		String hashtagSql = "DELETE h"
				+ "			FROM hashtag h"
				+ "			INNER JOIN cashbook c"
				+ "			ON h.cashbook_no = c.cashbook_no"
				+ "			WHERE c.member_id = ?";
		// member_id가 sessionId인 곳의 cashbook 정보 삭제
		String cashbookSql = "DELETE FROM cashbook WHERE member_id=?";
		// member 정보 삭제
		String memberSql = "DELETE FROM member WHERE member_id=? AND member_pw=password(?)";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://13.209.216.160/cashbook","root","1q2w3e4r");

			// member_id가 sessionId인 곳의 해시태그 삭제
			hashtagStmt = conn.prepareStatement(hashtagSql);
			hashtagStmt.setString(1, member.getMemberId());
			
			hashtagStmt.executeUpdate();
			
			// member_id가 sessionId인 곳의 cashbook 정보 삭제
			cashbookStmt = conn.prepareStatement(cashbookSql);
			cashbookStmt.setString(1, member.getMemberId());
			
			cashbookStmt.executeUpdate();
			
			// member 정보 삭제
			memberStmt = conn.prepareStatement(memberSql);
			memberStmt.setString(1, member.getMemberId());
			memberStmt.setString(2, member.getMemberPw());
			
			// 삭제한 행 개수 담기
			row = memberStmt.executeUpdate();
			
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return row;
	}
	
	// 회원 정보 select
	public Member selectMemberOne(String memberId) {
		Member member = new Member();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM member WHERE member_id=?";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://13.209.216.160/cashbook","root","1q2w3e4r");

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				member.setMemberId(rs.getString("member_id"));
				member.setMemberName(rs.getString("member_name"));
				member.setMemberAddress(rs.getString("member_address"));
				member.setMemberGender(rs.getString("member_gender"));
				member.setMemberPhone(rs.getString("member_phone"));		
				member.setMemberPw(rs.getString("member_pw"));		
				member.setCreateDate(rs.getString("create_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return member;
	}
	
	
	//로그인
	public String selectMemberByIdPw(Member member) {
		String memberId = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://13.209.216.160/cashbook","root","1q2w3e4r");

			stmt = conn.prepareStatement(sql);
			stmt.setString(1,member.getMemberId());
			stmt.setString(2,member.getMemberPw());
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				memberId = rs.getString("memberId");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return memberId;
	}
	
}
