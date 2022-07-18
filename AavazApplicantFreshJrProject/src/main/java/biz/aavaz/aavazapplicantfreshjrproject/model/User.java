package biz.aavaz.aavazapplicantfreshjrproject.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//!!! Serialization to handle json snake case and java camel case
//!!! https://www.javadoc.io/doc/com.google.code.gson/gson/2.6.2/com/google/gson/annotations/SerializedName.html
//!!! https://sites.google.com/site/gson/gson-user-guide#TOC-JSON-Field-Naming-Support (JSON Field Naming Support)
@Getter
@Setter
@ToString
@Entity
public class User {
	@SerializedName("first_name")
	private String firstName;
	@SerializedName("last_name")
	private String lastName;
	private int age;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL
			)
	@JoinTable(
			name = "UserSkill",
			joinColumns = @JoinColumn(name = "userId",referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "skillId",referencedColumnName = "id")
	)
	List <Skill> skills;
    
}
