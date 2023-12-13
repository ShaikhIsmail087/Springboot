package com.skb.course.apis.authorsws.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AuthoritiesRepository extends CrudRepository<AuthoritiesEntity, Integer> {

    Set<AuthoritiesEntity> findByRole(String role);
}
