package com.itbank.model.repository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.itbank.common.board.BoardMapper;
import com.itbank.model.domain.Board;

@Repository
public class JdbcBoardDAO implements BoardDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//insert , update , delete 는 모두 update() 사용하면 된다
	public int insert(Board board) {
		int result=0;
		String sql="insert into board(board_id,writer,title,content)";
		sql+=" values(seq_board.nextval, ?,?,?)";
		result=jdbcTemplate.update(sql, board.getWriter(), board.getTitle(), board.getContent());
		return result;
	}
	
	public List selectAll() {
		List list=null;
		String sql="select * from board order by board_id desc";
		list=jdbcTemplate.query(sql, new BoardMapper());
		return list;
	}
	
	public Board select(int board_id) {
		Board board=null;
		
		String sql="select * from board where board_id=?";
		board=jdbcTemplate.queryForObject(sql, new BoardMapper(), board_id);
		return board;
	}
	public int update(Board board) {
		int result=0;
		String sql="update board set writer=?, title=?, content=? ";
		sql+=" where board_id=?";
		result=jdbcTemplate.update(sql, board.getWriter(), board.getTitle(),board.getContent(),board.getBoard_id());
		return result;
	}
	
	public int delete(int board_id) {
		int result=0;
		String sql="delete from board where board_id=?";
		result=jdbcTemplate.update(sql, board_id);
		return result;
	}

}
