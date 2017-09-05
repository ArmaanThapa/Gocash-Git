package com.gocash.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.domain.AbstractPersistable;

@MappedSuperclass
public class AbstractEntity <PK extends Serializable> extends AbstractPersistable<PK> {




	private static final long serialVersionUID = 8453654076725018243L;

	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created = new Date();

	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModified;

	public AbstractEntity() {

		setCreated(new Date());
		setLastModified(new Date());
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCreated() {
		return created;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getLastModified() {
		return lastModified;
	}


}
