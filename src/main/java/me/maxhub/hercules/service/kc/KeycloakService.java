package me.maxhub.hercules.service.kc;

import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;

public interface KeycloakService {

    AccessToken verifyToken(String token) throws VerificationException;

    UserRepresentation getUser(String id);
}
