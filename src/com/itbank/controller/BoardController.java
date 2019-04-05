package com.itbank.controller;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
//���������� Controller �������̽��� �����ؼ� ��Ʈ�ѷ��� ����������,
//������ POJO ������� ��Ʈ�ѷ��� �����غ���!!!
//������̼� ��� ���ο����� ������̼��� ������ �� �ִ� ����� 
//Ŭ����, �޼��� 

import com.itbank.exception.RegistFailException;
import com.itbank.model.domain.Board;
import com.itbank.model.domain.FileBean;
import com.itbank.model.service.BoardService;

//scan�Ͽ� ������ ����� �ȴ�!!!
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("/test")
	public String test() {
		System.out.println("������ MVC ���� ����!!!");
		return null;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String regist(FileBean fileBean, HttpServletRequest request) {
		System.out.println("������ MVC ���� ����!!!");
		String realPath=request.getServletContext().getRealPath("/data");
		boardService.save(fileBean.getMyFile(), realPath);
		return null;
	}
	
	@RequestMapping(value="/board/list", method=RequestMethod.GET)
	public ModelAndView selectAll() {
		System.out.println("��Ϻ��� ��û");
		List boardList=boardService.selectAll();
		ModelAndView mav = new ModelAndView("board/list");
		mav.addObject("boardList", boardList);//����
		return mav;
	}
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insert(Board board) {
		System.out.println("�۾��� ��û");
		boardService.insert(board);
		return "redirect:/board/list";
	}
	@RequestMapping(value="/board/detail", method=RequestMethod.GET)
	public ModelAndView select(int board_id) {
		System.out.println("�Ѿ�� board_id"+board_id);
		Board board=boardService.select(board_id);
		ModelAndView mav=new ModelAndView("board/detail");
		mav.addObject("board", board);
		return mav;
	}
	
	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
	public String delete(int board_id) {
		System.out.println("���� ��û");
		boardService.delete(board_id);
		return "redirect:/board/list";
	}
	@RequestMapping(value="/board/edit", method=RequestMethod.POST)
	public String update(Board board) {
		System.out.println("���� ��û");
		boardService.update(board);
		return "redirect:/board/detail?board_id="+board.getBoard_id();
	}
	
	//���ܰ� �߻��ϸ�, ���ܸ� ó���ϴ� �޼��� ����
	@ExceptionHandler(RegistFailException.class)
	public ModelAndView handlerException(RegistFailException e){
		ModelAndView mav = new ModelAndView("board/error");
		mav.addObject("err", e);
		return mav;
	}
}














