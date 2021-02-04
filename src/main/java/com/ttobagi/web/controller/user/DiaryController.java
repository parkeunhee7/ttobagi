package com.ttobagi.web.controller.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ttobagi.web.entity.Couple;
import com.ttobagi.web.entity.DiaryView;
import com.ttobagi.web.service.CoupleService;
import com.ttobagi.web.service.DiaryService;
import com.ttobagi.web.service.MemberService;

@Controller
@RequestMapping("/user/diary")
public class DiaryController {
	
	@Autowired
	private DiaryService service;
	
	
	@Autowired
	private CoupleService coupleService;
	
	@RequestMapping("list")
	public String list(Model model, HttpSession session,
			@RequestParam(name = "p", defaultValue = "1") int page ) {
		
		int id = (int)session.getAttribute("id");
		
		//-----상대방 아이디 구하기
		int opponentId=0;
		List<Couple> coupleList = coupleService.getList(id);
		if(id==coupleList.get(0).getSenderId()) {//내 아디디가 커플요청 받는자 아이디면
			opponentId =coupleList.get(0).getReceiverId();//상대방은 요청자 아이디
		}
		else if (id==coupleList.get(0).getReceiverId()) {
			opponentId =coupleList.get(0).getSenderId();//상대방은 요청자 아이디
		}
		//----- 페이지에따른 데이터 
		int size=4;
		int startIndex=size*(page-1);//0 4 8 12 16    1page면 0 2page면 4     size*(page-1)
		
		
		List<DiaryView> list = service.getViewList(id,opponentId,startIndex,size);
		model.addAttribute("list",list);
		System.out.println(list);
		return "user.diary.list";
	}
	
	@RequestMapping("reg")
	public String reg() {
		return "user.diary.reg";
	}

}
