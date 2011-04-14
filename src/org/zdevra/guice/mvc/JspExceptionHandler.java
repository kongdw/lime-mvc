/*****************************************************************************
 * Copyright 2011 Zdenko Vrabel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 *****************************************************************************/
package org.zdevra.guice.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zdevra.guice.mvc.exceptions.InvalidJspViewException;


/**
 * The exception handler implementation show the given JSP
 * when the exception is throwed.
 *
 */
public class JspExceptionHandler extends ExceptionHandler {
	
/*---------------------------- m. variables ----------------------------*/
	
	private final String jspPath;
	
/*---------------------------- constructors ----------------------------*/
	
	public JspExceptionHandler(String jspPath) {
		this.jspPath = jspPath;
	}

/*------------------------------- methods ------------------------------*/
	
	@Override
	public void handleException(Throwable t, HttpServletRequest request, HttpServletResponse response) 
	{
		try {
			request = new HttpRequestForForward(request, this.jspPath);
			RequestDispatcher dispatcher = request.getRequestDispatcher(this.jspPath);
			if (dispatcher == null) {
				throw new InvalidJspViewException(jspPath);
			}
			
			dispatcher.forward(request, response);
			request = null;
			
		} catch (ServletException e) {
			throw new IllegalStateException("can't redirect to the JSP:" + jspPath, e);
		} catch (IOException e) {
			throw new IllegalStateException("can't redirect to the JSP:" + jspPath, e);
		}
	}

}
