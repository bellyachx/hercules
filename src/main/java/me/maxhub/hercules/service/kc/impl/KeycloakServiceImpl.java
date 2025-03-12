package me.maxhub.hercules.service.kc.impl;

import me.maxhub.hercules.config.KeycloakProperties;
import me.maxhub.hercules.service.kc.KeycloakService;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.OAuth2Constants;
import org.keycloak.TokenVerifier;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.common.VerificationException;
import org.keycloak.common.crypto.CryptoIntegration;
import org.keycloak.common.util.PemUtils;
import org.keycloak.crypto.KeyType;
import org.keycloak.crypto.def.DefaultCryptoProvider;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final KeycloakProperties properties;
    public KeycloakServiceImpl(KeycloakProperties properties) {
        this.properties = properties;
        CryptoIntegration.init(DefaultCryptoProvider.class.getClassLoader());
    }

    @Override
    public AccessToken verifyToken(String token) throws VerificationException {
        var tokenVerifier = TokenVerifier.create(token, AccessToken.class);
        var keyId = tokenVerifier.getHeader().getKeyId();

        return tokenVerifier.publicKey(getPublicKey(keyId)).verify().getToken();
    }

    @Override
    public UserRepresentation getUser(String id) {
        try (var kc = kc()) {
            return kc.realm(properties.getRealm()).users().get(id).toRepresentation();
        }
    }

    private PublicKey getPublicKey(String keyId) {
        try (var kc = kc()) {
            var keys = kc.realm(properties.getRealm()).keys().getKeyMetadata().getKeys();

            return keys.stream()
                    .filter(key -> StringUtils.equals(keyId, key.getKid()) &&
                            StringUtils.equals(KeyType.RSA, key.getType()))
                    .findFirst()
                    .map(key -> PemUtils.decodePublicKey(key.getPublicKey()))
                    .orElseThrow(() -> new RuntimeException("Key not found by given keyId"));
        }
    }

    private Keycloak kc() {
        return KeycloakBuilder.builder()
                .serverUrl(properties.getUrl())
                .realm(properties.getRealm())
                .clientId(properties.getClientId())
                .clientSecret(properties.getClientSecret())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }
}
