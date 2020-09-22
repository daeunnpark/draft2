package org.kpax.oauth2.service;

import org.kpax.oauth2.model.Oauth2Client;
import org.kpax.oauth2.repository.Oauth2ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomOauth2ClientDetailsService implements ClientDetailsService{

    @Autowired
    private Oauth2ClientRepository oauth2ClientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Oauth2Client client = oauth2ClientRepository.findByClientId(clientId);
        return new BaseClientDetails(client);
    }
}
