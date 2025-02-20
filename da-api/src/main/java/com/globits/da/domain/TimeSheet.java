package com.globits.da.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import com.globits.core.domain.BaseObject;
import com.globits.security.domain.User;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "tbl_timesheet")
@XmlRootElement
public class TimeSheet extends BaseObject {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "date_work")
	private String dateWork;
	 
	@Column(name = "percent_work")
	private String percent_work;
	 
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "project_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Project project;

	public String getDateWork() {
		return dateWork;
	}

	public void setDateWork(String dateWork) {
		this.dateWork = dateWork;
	}

	public String getPercent_work() {
		return percent_work;
	}

	public void setPercent_work(String percent_work) {
		this.percent_work = percent_work;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
}
