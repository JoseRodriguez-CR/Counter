package com.joserodriguez;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	private int getSessionCount(HttpSession session) {
		Object sesssionValue = session.getAttribute("count");
		if(sesssionValue == null) {
			setSessionCount(session, 0);
			sesssionValue = session.getAttribute("count");
		}
		System.out.println(sesssionValue);
		System.out.println((Integer)sesssionValue);
		return (Integer)sesssionValue;
	}
	
	private void setSessionCount(HttpSession session, int val) {
		session.setAttribute("count", val);
	}
	
	// This end point use the getter and setter above to check if there is a session  and add it to the counter
	@RequestMapping("/")
	public String Index(HttpSession session) {
		int currentCount = getSessionCount(session);
		setSessionCount(session, ++currentCount);
		return "index.jsp";  // Once index.jsp is rendered, the user can go to Counter Results thru /counter end point
	}
	
	
	
	// counter end point receive session and model object
	@RequestMapping("/counter")
	public String Counter(HttpSession session, Model model) {
		model.addAttribute("count", getSessionCount(session));
		return "count.jsp";
	}
	
	
	@RequestMapping("/reset")
	public String Reset(HttpSession session) {
		session.invalidate();
		//session.removeAttribute(); since I'm not specifying an attribute to be removed I used invalidate 
		return "redirect:/counter";
	}

}
