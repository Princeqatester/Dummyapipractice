package api.endpoints;
import static io.restassured.RestAssured.*;

import org.testng.ITestContext;

import api.payload.register;
import api.payload.user;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class users 
{
public static Response getallusers()
{
	Response rs=given().when().get(Routes.getusers);
	return rs;
}
public static Response createuser(ITestContext context,user payload)
{
	Response rs=given().contentType(ContentType.JSON).body(payload).when().post(Routes.base_url);
	String userid=rs.jsonPath().get("id");
	System.out.println("Here is the user id"+userid);
	context.setAttribute("iduser", userid);
	return rs;
}
public static Response putuser(ITestContext context,user payload)
{
	String id=(String) context.getAttribute("iduser");
	System.out.println("User id retrieved"+id);
	Response rs=given().contentType(ContentType.JSON).pathParam("userid", id).body(payload).when().put(Routes.updateuser);
	System.out.println(Routes.updateuser);
	return rs;
}
public static Response deleteuser(ITestContext context)
{
	String deleteuserid=(String) context.getAttribute("iduser");
	System.out.println("Retrived id = "+deleteuserid);
	Response rs=given().pathParam("userid", deleteuserid).when().delete(Routes.delete_user);
	return rs;
}
public static Response registersuccessful(register payload)
{
	Response rs=given().contentType(ContentType.JSON).body(payload).when().post(Routes.login_successful);
	return rs;
	
}
}
