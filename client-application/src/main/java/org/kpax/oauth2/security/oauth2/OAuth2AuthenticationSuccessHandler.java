package org.kpax.oauth2.security.oauth2;

//import com.example.springsocial.exception.BadRequestException;
import org.kpax.oauth2.security.api.JwtTokenProvider;
import org.kpax.oauth2.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//import static com.example.springsocial.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CookieUtils cookieUtils;

    @Value("${app.jwtRedirectUri}")
    private String redirect_uri;

    private int maxAge = 10000;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (response.isCommitted()) {
             logger.debug("Response has already been committed. Unable to redirect to " + redirect_uri);
            return;
        }
        System.out.println("***handler hereees");
        clearAuthenticationAttributes(request, response);
        cookieUtils.addCookie(response, "access-token", tokenProvider.generateToken(authentication), maxAge);
        getRedirectStrategy().sendRedirect(request, response, redirect_uri);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
    }
}
