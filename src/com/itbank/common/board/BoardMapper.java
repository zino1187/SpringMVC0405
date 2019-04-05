package com.itbank.common.board;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.itbank.model.domain.Board;

//jdbcTempate ���� ��ü�� �����ϱ� ���� Ŭ������ �����س��´�
public class BoardMapper implements RowMapper<Board>{
	public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
		Board board = new Board();
		System.out.println(rowNum+"���� ��");
		board.setBoard_id(rs.getInt("board_id"));
		board.setWriter(rs.getString("writer"));
		board.setTitle(rs.getString("title"));
		board.setContent(rs.getString("content"));
		board.setRegdate(rs.getString("regdate"));
		board.setHit(rs.getInt("hit"));
		return board;
	}	
}






