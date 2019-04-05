package com.itbank.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.itbank.exception.DeleteFailException;
import com.itbank.exception.EditFailException;
import com.itbank.exception.RegistFailException;
import com.itbank.model.domain.Board;
import com.itbank.model.repository.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService{
	@Autowired
	@Qualifier("mybatisBoardDAO")
	private BoardDAO boardDAO;

	public void save(MultipartFile multipartFile, String path){
		String filename=multipartFile.getOriginalFilename();
		try {
			multipartFile.transferTo(new File(path+"/"+filename));
			System.out.println(path+"/"+filename);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insert(Board board) throws RegistFailException{
		int result=0;
		result=boardDAO.insert(board);

		if(result ==0) {
			throw new RegistFailException("등록실패");
		}
		return result;
	}

	public List selectAll() {
		return boardDAO.selectAll();
	}

	@Override
	public Board select(int board_id) {
		return boardDAO.select(board_id);
	}

	@Override
	public int update(Board board) {
		int result=0;
		result=boardDAO.update(board);
		if(result==0) {
			throw new EditFailException("수정 실패");
		}
		return result;
	}

	@Override
	public int delete(int board_id) {
		int result=0;
		result=boardDAO.delete(board_id);
		if(result==0) {
			throw new DeleteFailException("삭제실패");
		}
		return result;
	}
	
}






