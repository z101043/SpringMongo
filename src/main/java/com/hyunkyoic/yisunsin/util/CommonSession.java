package com.hyunkyoic.yisunsin.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;



/** 
 * 
 * 1. MethodName : preHandle
 * 2. ClassName	: CommonSession
 * 3. 작성자 : 김양헌
 * 4. 작성일 : 2014. 12. 19.
 * 5. Comment : 세션 관련 - 페이지 권한 설정
 */
public class CommonSession extends HandlerInterceptorAdapter{
	
	private static final Logger log = LoggerFactory.getLogger(CommonSession.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//log.debug("session체크");
		HttpSession session = request.getSession();
		String[] url = request.getRequestURI().split("/");
		String lastUrl = url[url.length - 1];
		//log.debug("lastUrl: "+lastUrl);
		String member = (String) session.getAttribute("user_id");
		String user_auth = (String) session.getAttribute("user_auth");
		
		if(lastUrl.equals("work") || lastUrl.equals("progression") || lastUrl.equals("hospitalManagement") 
				|| lastUrl.equals("areaManagement") || lastUrl.equals("regHospital")){
			
			if (member == null) {
				alertLoginMsg(response, "로그인 후 이용가능합니다.");
				return false;
			} else {
				
				if(!user_auth.equals("AU01") && lastUrl.equals("regHospital")){
					alertMsg(response, "해당 메뉴 이용 권한이 없습니다.");
					return false;
				}
		
				if (user_auth.equals("AU02")) { // 권한 및 메뉴 이용권한 설정
					//alertMsg(response, "해당 메뉴 이용 권한이 없습니다.");
					//return false;
				}
				
				if ((user_auth.equals("AU03") || user_auth.equals("AU04") || user_auth.equals("AU05")) && (
						lastUrl.equals("hospitalManagement") || lastUrl.equals("areaManagement"))) { // 권한 및 메뉴 이용권한 설정
						//alertMsg(response, "해당 메뉴 이용 권한이 없습니다.");
						//return false;
				}
	
				return true;
			}
		}else{
			return true;
		}

	}
	
	public void alertMsg(HttpServletResponse response, String msg) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("		alert('"+msg+"');");
		sb.append("		history.back();");
		sb.append("</script>");
		response.getWriter().print(sb.toString());
		response.getWriter().close();
	}
	
	public void alertLoginMsg(HttpServletResponse response, String msg) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("		alert('"+msg+"');");
		sb.append("		location.href='./';");
		sb.append("</script>");
		response.getWriter().print(sb.toString());
		response.getWriter().close();
	}
	
	public void alertSuccessMsg(HttpServletResponse response, String msg) throws IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("		alert('"+msg+"');");
		sb.append("		location.href='./progression';");
		sb.append("</script>");
		response.getWriter().print(sb.toString());
		response.getWriter().close();
	}

}
