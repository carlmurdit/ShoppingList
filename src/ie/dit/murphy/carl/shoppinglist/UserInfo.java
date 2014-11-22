package ie.dit.murphy.carl.shoppinglist;

public class UserInfo {
	private static UserInfo userInfo = null;
	private String name = "";
	private String gender = "";
	private String jobTitle = "";
	
	// Singleton pattern
	private UserInfo() {} 
    public static UserInfo getInstance() {
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		return UserInfo.userInfo;
	}
	
	/*public void reset() {
		this.name = "";
		this.gender = "";
		this.jobTitle = "";
	}
*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}
	
	public boolean nameIsValid() {
		return !name.equals("");
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public boolean genderIsValid() {
		return !gender.equals("");
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public boolean jobTitleIsValid() {
		return !jobTitle.equals("");
	}
	
	public void getErrors() throws Exception {
		
		String errMsg="";
		
		if(!nameIsValid()) {
			errMsg += "Please enter your name.\n";
		}
		if(!genderIsValid()) {
			errMsg += "Please select your gender.\n";
		}
		if(!jobTitleIsValid()) {
			errMsg += "Please select a job title.\n";
		}
		
		if (errMsg!="") {
			throw new Exception(errMsg);
		}
		
	}
	

}
