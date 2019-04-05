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
//어제까지는 Controller 인터페이스를 구현해서 컨트롤러를 정의했지만,
//오늘은 POJO 기반으로 컨트롤러를 정의해본다!!!
//어노테이션 기반 매핑에서는 어노테이션을 지정할 수 있는 대상이 
//클래스, 메서드 

import com.itbank.exception.RegistFailException;
import com.itbank.model.domain.Board;
import com.itbank.model.domain.FileBean;
import com.itbank.model.service.BoardService;

//scan하여 생성의 대상이 된다!!!
@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;

	@RequestMapping("/test")
	public String test() {
		System.out.println("스프링 MVC 세팅 성공!!!");
		return null;
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String regist(FileBean fileBean, HttpServletRequest request) {
		System.out.println("스프링 MVC 세팅 성공!!!");
		String realPath=request.getServletContext().getRealPath("/data");
		boardService.save(fileBean.getMyFile(), realPath);
		return null;
	}
	
	@RequestMapping(value="/board/list", method=RequestMethod.GET)
	public ModelAndView selectAll() {
		System.out.println("목록보기 요청");
		List boardList=boardService.selectAll();
		ModelAndView mav = new ModelAndView("board/list");
		mav.addObject("boardList", boardList);//저장
		return mav;
	}
	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String insert(Board board) {
		System.out.println("글쓰기 요청");
		boardService.insert(board);
		return "redirect:/board/list";
	}
	@RequestMapping(value="/board/detail", method=RequestMethod.GET)
	public ModelAndView select(int board_id) {
		System.out.println("넘어온 board_id"+board_id);
		Board board=boardService.select(board_id);
		ModelAndView mav=new ModelAndView("board/detail");
		mav.addObject("board", board);
		return mav;
	}
	
	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
	public String delete(int board_id) {
		System.out.println("삭제 요청");
		boardService.delete(board_id);
		return "redirect:/board/list";
	}
	@RequestMapping(value="/board/edit", method=RequestMethod.POST)
	public String update(Board board) {
		System.out.println("수정 요청");
		boardService.update(board);
		return "redirect:/board/detail?board_id="+board.getBoard_id();
	}
	
	//예외가 발생하면, 예외를 처리하는 메서드 정의
	@ExceptionHandler(RegistFailException.class)
	public ModelAndView handlerException(RegistFailException e){
		ModelAndView mav = new ModelAndView("board/error");
		mav.addObject("err", e);
		return mav;
	}
}














