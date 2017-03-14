package com.wx.auth.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.auth.util.AuthUtil;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class CallBack
 */
public class CallBackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CallBackServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code=request.getParameter("code");
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AuthUtil.APPID+
					"&secret="+AuthUtil.APPSECRET+
					"&code="+code+
					"&grant_type=authorization_code";
		JSONObject jsonObject=AuthUtil.doGetJson(url);
		String openid=jsonObject.getString("openid");
		String token=jsonObject.getString("access_token");
		String inforUrl="https://api.weixin.qq.com/sns/userinfo?"+
					"access_token="+token+
					 "&openid="+openid+
					 "&lang=zh_CN";
		JSONObject userInfo=AuthUtil.doGetJson(inforUrl);
		System.out.println(userInfo);
		//使用微信用户信息直接登录
		request.setAttribute("info", userInfo);
		request.getRequestDispatcher("/index1.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
