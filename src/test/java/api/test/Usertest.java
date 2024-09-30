package api.test;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.*;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.users;
import api.payload.user;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Usertest 
{
Faker fak;
user userobj;
@BeforeClass
public void setupdata()
{
	fak=new Faker();
	userobj=new user();
	userobj.setName(fak.name().fullName());
	userobj.setJob(fak.job().position());
}

@Test(priority = 4, enabled = true)
public void getallUsers()
{
	Response rs=users.getallusers();
	rs.then().statusCode(200).log().all();
	String responsebody=rs.asString();
	Assert.assertEquals(responsebody.contains("Janet"),true,"Keyword is not found in response body");
	/*JsonPath jsonpathview=rs.jsonPath();
	String firstname=jsonpathview.get("data[1].first_name");
	Assert.assertEquals(firstname, "Janet");*/
}
@Test(priority = 1, enabled = false)
public void newusercreation(ITestContext context)
{
	Response rs=users.createuser(context, userobj);
	rs.then().statusCode(201).log().all();
	Headers headerview=rs.getHeaders();
	for(Header headview:headerview)
	{
		System.out.println("Header name :" +headview.getName() +", Value :"+headview.getValue());
	}
}
@Test(priority = 2, enabled = false)
public void updateuser(ITestContext context)
{
	userobj.setName(fak.name().lastName());
	userobj.setJob(fak.job().seniority());
	Response rs=users.putuser(context, userobj);
	rs.then().statusCode(200).log().all();
}
@Test(priority = 3, enabled = false)
public void deleteuser(ITestContext context)
{
	Response rs=users.deleteuser(context);
	rs.then().statusCode(204).log().all();
}
}
