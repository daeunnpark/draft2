package org.kpax.oauth2.security;

//import com.example.springsocial.exception.BadRequestException;
import org.kpax.oauth2.security.JwtTokenProvider;
import org.kpax.oauth2.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

//import static com.example.springsocial.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider tokenProvider;

    private String REDIRECT_URI_PARAM_COOKIE_NAME="redirect_uri";

    @Autowired
    private CookieUtils cookieUtils;

    private int maxAge = 10000;

    @Value("${app.jwtRedirectUri}")
    private String redirect_uri;

    /*
    @Autowired
    OAuth2AuthenticationSuccessHandler(JwtTokenProvider tokenProvider){s
        this.tokenProvider = tokenProvider;
        // this.appProperties = appProperties;
        // this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
        System.out.println("****oauth2authenticationSuccess");
    }
     */

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
             logger.debug("Response has already been committed. Unable to redirect to " + redirect_uri);
            return;
        }
        clearAuthenticationAttributes(request, response);
        cookieUtils.addCookie(response, "access-token", tokenProvider.generateToken(authentication) ,maxAge);
        getRedirectStrategy().sendRedirect(request, response, redirect_uri);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
    }
/*
    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
     */
}
