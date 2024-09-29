package api.test;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.*;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.users;
import api.payload.user;
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

@Test(priority = 4)
public void getallUsers()
{
	Response rs=users.getallusers();
	rs.then().statusCode(200).log().all();
}
@Test(priority = 1)
public void newusercreation(ITestContext context)
{
	Response rs=users.createuser(context, userobj);
	rs.then().statusCode(201).log().all();
}
@Test(priority = 2)
public void updateuser(ITestContext context)
{
	userobj.setName(fak.name().lastName());
	userobj.setJob(fak.job().seniority());
	Response rs=users.putuser(context, userobj);
	rs.then().statusCode(200).log().all();
}
@Test(priority = 3)
public void deleteuser(ITestContext context)
{
	Response rs=users.deleteuser(context);
	rs.then().statusCode(204).log().all();
}
}
