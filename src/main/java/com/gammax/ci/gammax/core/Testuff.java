
package com.gammax.ci.gammax.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Optional;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testuff {

	static Logger log = LogManager.getLogger(Testuff.class);
	static boolean testFailureFlag = true;

	public static void execDOSCmdToEndJavaProcesses() {
		try {
			// Execute command
			String command = "cmd /c start taskkill /f /im java.exe";
			Process child = Runtime.getRuntime().exec(command);

			// Get output stream to write from it
			OutputStream out = child.getOutputStream();

			out.write("cd C:/ /r/n".getBytes());
			out.flush();
			out.write("dir /r/n".getBytes());
			out.close();
		} catch (IOException e) {
			log.error("Input & Output operations exception occurred." + e.getMessage());
			Assert.fail("Exception Occurred");
		}
	}

	public static void execCurlCommand(String curl) {
		try {
			// Execute command
			if (System.getProperty("os.name").contains("Windows")) {
				String command = "cmd /c start " + curl;
				Process child = Runtime.getRuntime().exec(command);

				// Get output stream to write from it
				OutputStream out = child.getOutputStream();

				out.write("cd C:/ /r/n".getBytes());
				out.flush();
				out.write("dir /r/n".getBytes());
				out.close();
			} else if (System.getProperty("os.name").contains("Linux")) {

				try {
					String proc = curl;

					log.info("***************************");
					log.info(proc);
					log.info("***************************");
					log.info("***************************");
					log.info(System.getProperty("user.name"));
					log.info("***************************");
					List<String> commands;
					commands = new ArrayList<String>(Arrays.asList(proc.split(" ")));
					ProcessBuilder pb = new ProcessBuilder(commands);

					pb.redirectErrorStream(true);
					Process process = pb.start();
					StringBuilder output = new StringBuilder();
					BufferedReader reader = new BufferedReader(new InputStreamReader((process.getInputStream())));
					String line;
					while ((line = reader.readLine()) != null) {
						output.append(line + "\n");
					}
					int exitVal = process.waitFor();
					if (exitVal == 0) {
						log.info("success");
						log.info(output);
					} else {
						log.info("Error:" + output.toString());
					}
				} catch (IOException e) {
					log.info("IOException:");
					e.printStackTrace();
				} catch (InterruptedException e) {
					log.info("InterruptedException:");
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reportTestInTestStuff(@Optional("chrome") String browserName, String testid, String labid) {

		log.info("Value of testID is" + " " + testid);
		log.info("Value of LabID is" + " " + labid);
		log.info("Value of browserName is" + " " + browserName);

		String userId = TestProperties.get("TestuffUserID");
		String password = TestProperties.get("TestuffPassword");

		if (System.getProperty("os.name").contains("Windows")) {
			if (!testFailureFlag) {
				// Working
				execCurlCommand("curl -u " + userId + ":" + password
						+ " -k -H \"Content-Type:application/json\" -X POST --data \"{\\\"test_id\\\":\\\"" + testid
						+ "\\\",\\\"lab_id\\\":\\\"" + labid
						+ "\\\",\\\"status\\\":\\\"failed\\\",\\\"run_configuration\\\":\\\"" + browserName
						+ "\\\"}\" https://service4.testuff.com/api/v0/run/");
				testFailureFlag = true;
			} else
				execCurlCommand("curl -u " + userId + ":" + password
						+ " -k -H \"Content-Type:application/json\" -X POST --data \"{\\\"test_id\\\":\\\"" + testid
						+ "\\\",\\\"lab_id\\\":\\\"" + labid
						+ "\\\",\\\"status\\\":\\\"passed\\\",\\\"run_configuration\\\":\\\"" + browserName
						+ "\\\"}\" https://service4.testuff.com/api/v0/run/");
		} else {
			if (!testFailureFlag) {
				execCurlCommand("sudo curl -u " + userId + ":" + password
						+ " -k -H Content-Type:application/json -X POST --data {\"test_id\":\"" + testid
						+ "\",\"lab_id\":\"" + labid + "\",\"status\":\"failed\",\"run_configuration\":\"" + browserName
						+ "\"} https://service4.testuff.com/api/v0/run/");
				testFailureFlag = true;
			} else
				execCurlCommand("sudo curl -u " + userId + ":" + password
						+ " -k -H Content-Type:application/json -X POST --data {\"test_id\":\"" + testid
						+ "\",\"lab_id\":\"" + labid + "\",\"status\":\"passed\",\"run_configuration\":\"" + browserName
						+ "\"} https://service4.testuff.com/api/v0/run/");
		}

	}

	public String getLabID(String labname) {

		String labid;
		RestAssured.baseURI = "https://service4.testuff.com/api/v0/";

		Response response = RestAssured.given().auth()
				.basic(TestProperties.get("TestuffUserID"), TestProperties.get("TestuffPassword"))
				.contentType(ContentType.JSON).param("branch", TestProperties.get("TestuffBranchID"))
				.param("name", labname).when().get("lab/");

		System.out.println("The status code is: " + response.getStatusCode());
		System.out.println("The response body is ------>  " + response.getBody().asString() + "  <------");
		JsonPath jsonPath = new JsonPath(response.asString());
		labid = jsonPath.getString("objects[0].id");
		System.out.println("The lab id is: " + labid);
		return labid;
	}
	
	public String getLabCount(String labname) {

		String count;
		RestAssured.baseURI = "https://service4.testuff.com/api/v0/";

		Response response = RestAssured.given().auth()
				.basic(TestProperties.get("TestuffUserID"), TestProperties.get("TestuffPassword"))
				.contentType(ContentType.JSON).param("branch", TestProperties.get("TestuffBranchID"))
				.param("name__contains", labname).when().get("lab/");

		System.out.println("The status code is: " + response.getStatusCode());
		System.out.println("The response body is ------>  " + response.getBody().asString() + "  <------");
		JsonPath jsonPath = new JsonPath(response.asString());
		count = jsonPath.getString("meta.total_count");
		System.out.println("The lab count is: " + count);
		return count;
	}

	public void setTestFailureFlag(boolean testFailureFlag) {
		Testuff.testFailureFlag = testFailureFlag;
	}

	public void reportFailedSuiteTestInTestStuff(String RUNID) {
		RestAssured.baseURI = "https://service4.testuff.com/api/v0/";
		RequestSpecification request = RestAssured.given().auth()
				.basic(TestProperties.get("TestuffUserID"), TestProperties.get("TestuffPassword"))
				.contentType(ContentType.JSON);

		JSONObject requestParams = new JSONObject();
		requestParams.put("status", "passed");

		request.body(requestParams.toString());
		Response response = request.put("run/" + RUNID + "/");

		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);

	}

	public String getTestID(String lab_id, String automation_id) {
		RestAssured.baseURI = "https://service4.testuff.com/api/v0/";

		Response response = RestAssured.given().auth()
				.basic(TestProperties.get("TestuffUserID"), TestProperties.get("TestuffPassword"))
				.contentType(ContentType.JSON).param("lab", lab_id).param("automation_id", automation_id).when()
				.get("test/");

		System.out.println("The status code is: " + response.getStatusCode());
		System.out.println("The response body is ------>  " + response.getBody().asString() + "  <------");
		JsonPath jsonPath = new JsonPath(response.asString());
		System.out.println("The status testid is: " + jsonPath.getString("objects[0].id"));
		return jsonPath.getString("objects[0].id");
	}

	public String getRunID(String lab_id, String automation_id) {
		RestAssured.baseURI = "https://service4.testuff.com/api/v0/";

		Response response = RestAssured.given().auth()
				.basic(TestProperties.get("TestuffUserID"), TestProperties.get("TestuffPassword"))
				.contentType(ContentType.JSON).param("lab", lab_id).param("test", getTestID(lab_id, automation_id))
				.when().get("run/");

		System.out.println("The status code is: " + response.getStatusCode());
		System.out.println("The response body is ------>  " + response.getBody().asString() + "  <------");
		JsonPath jsonPath = new JsonPath(response.asString());
		System.out.println("The status runid is: " + jsonPath.getString("objects[0].id"));
		return jsonPath.getString("objects[0].id");
	}

	public int getTestCount(String testid,String labid){
		System.out.println("Inside get test count method");
		String testCount;
		RestAssured.baseURI = "https://service4.testuff.com/api/v0/";

		System.out.println("The UserID is: " +TestProperties.get("TestuffUserID"));
		System.out.println("The Password is: " +TestProperties.get("TestuffPassword"));
		System.out.println("The TestID is: " +testid);
		System.out.println("The LabID is: " +labid);
		System.out.println("The BranchID is: " +TestProperties.get("TestuffBranchID"));

		Response response = RestAssured.given().auth()
				.basic(TestProperties.get("TestuffUserID"), TestProperties.get("TestuffPassword"))
				.contentType(ContentType.JSON).param("lab", labid).param("automation_id",testid).when().get("test/");
		System.out.println("The status code is: " + response.getStatusCode());
		System.out.println("The response body is ------>  " + response.getBody().asString() + "  <------");
		JsonPath jsonPath = new JsonPath(response.asString());
		testCount = jsonPath.getString("meta.total_count");
		System.out.println("Total test count: " + testCount);
		int count = Integer.parseInt(testCount);
		return count;
	}

	public String getConfigName(String lab_id, String automation_id){
		System.out.println("Inside get Config Name method");
		String configName;
		RestAssured.baseURI = "https://service4.testuff.com/api/v0/";

		System.out.println("The lab ID is: " + lab_id);
		String runID = getRunID(lab_id, automation_id);
		System.out.println("The test ID is: " + runID);
		Response response = RestAssured.given().auth()
				.basic(TestProperties.get("TestuffUserID"), TestProperties.get("TestuffPassword"))
				.contentType(ContentType.JSON).param("lab", lab_id).param("id", getRunID(lab_id, automation_id)).when().get("run/");
		System.out.println("The status code is: " + response.getStatusCode());
		System.out.println("The response body is ------>  " + response.getBody().asString() + "  <------");
		JsonPath jsonPath = new JsonPath(response.asString());
		configName = jsonPath.getString("objects[0].conf_name");
		System.out.println("The name of the browser is " + configName);
		return configName;
	}

}
