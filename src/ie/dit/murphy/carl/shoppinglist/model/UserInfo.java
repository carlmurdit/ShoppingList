package ie.dit.murphy.carl.shoppinglist.model;

public class UserInfo {
	private static UserInfo userInfo = null;
	private String name = "";
	private String gender = "";
	private String jobTitle = "";
	private Integer age = 0; //date of birth might be better
	private String email = "";
	private double budget = 0.0f;
	
	
	// Singleton pattern
	private UserInfo() {} 
    public static UserInfo getInstance() {
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		return UserInfo.userInfo;
	}
    
    public void clear() {
    	this.setAge(0);
    	this.setBudget(0.0f);
    	this.setEmail("");
    	this.setGender("");
    	this.setJobTitle("");
    	this.setName("");
    }
	
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
	
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	public boolean ageIsValid() {
		return (getAge() == null || !getAge().equals(0));		
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public boolean emailIsValid() {
		return !getEmail().equals("");
	}
	
	public double getBudget() {
		return budget;
	}
	public void setBudget(double budget) {
		this.budget = budget;
	}
	
	public boolean budgetIsValid() {
		return getBudget() != 0;
	}
	
	
	public void getErrors() throws Exception {
		
		String errMsg="";
		
		if(!nameIsValid()) 
			errMsg += "Please enter your name.\n";
		if(!genderIsValid())  
			errMsg += "Please select your gender.\n";
		if(!jobTitleIsValid()) 
			errMsg += "Please select a job title.\n";
		if(!ageIsValid()) 
			errMsg += "Please enter your age.\n";
		if(!emailIsValid()) 
			errMsg += "Please enter your email.\n";
		if(!budgetIsValid()) 
			errMsg += "Please enter your budget.\n";
		
		if (errMsg!="")
			throw new Exception(errMsg);
		
	}

}
