package com.bigtail.platform.framework.orm.dao;

import org.springframework.data.repository.CrudRepository;

import com.bigtail.platform.framework.orm.dao.persistence.jpa.DualEntity;


public interface TestCURDDao extends CrudRepository<DualEntity, String> {

}
