package org.kpax.oauth2.repository;

import org.kpax.oauth2.model.Oauth2Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Oauth2ClientRepository extends JpaRepository<Oauth2Client, String> {
	Oauth2Client findByClientId(String clientId);
}

