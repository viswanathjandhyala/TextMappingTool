<%@page import="org.apache.tomcat.util.http.fileupload.FileItem"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.disk.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.apache.commons.io.output.*"%>
<%@ page import="org.apache.commons.fileupload.util.Streams"%>
<%@ page import="java.lang.String"%>
<%!String jsonData = "";
String fileName="";%>
<%
	ServletContext context = pageContext.getServletContext();
	boolean isMultipart = ServletFileUpload.isMultipartContent(request);

	String contentType = request.getContentType();
	if ((contentType.indexOf("multipart/form-data") >= 0)) {

		ServletFileUpload upload = new ServletFileUpload();
		FileItemIterator iter = upload.getItemIterator(request);
		try {
			while (iter.hasNext()) {
				FileItemStream item = iter.next();
				String name = item.getFieldName();
				fileName = item.getName();
				InputStream stream = item.openStream();
				if (item.isFormField()) {
				} else {
					jsonData = Streams.asString(stream);
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
	} else {
		out.println("<body>");
		out.println("<p>No file uploaded</p>");
		out.println("</body>");
	}
%>

<jsp:forward page="/UploadServlet">
	<jsp:param name="json" value="<%=jsonData%>" />
	<jsp:param name="fileName" value="<%=fileName %>" />
</jsp:forward>