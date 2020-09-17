package org.kpax.oauth2.config;

import jdk.nashorn.internal.lookup.MethodHandleFactory;
import org.kpax.oauth2.model.UserPrincipal;
import org.kpax.oauth2.security.api.JwtAuthenticationFilter;
import org.kpax.oauth2.security.api.JwtTokenProvider;
import org.kpax.oauth2.service.user.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Configuration
@EnableWebSocketMessageBroker

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub"); // /app
        registry.enableSimpleBroker("/sub");   // /topic


        //   Use this for enabling a Full featured broker like RabbitMQ

        /*
        registry.enableStompBrokerRelay("/topic")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest");
        */
    }

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

            registration.interceptors(new ChannelInterceptor() {
                @Override
                public Message<?> preSend(Message<?> message, MessageChannel channel) {
                    StompHeaderAccessor accessor =
                            MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                        System.out.println("***CONNECTION ATTEMPTED");
                        try {
                            String jwt = getJwtFromSocketRequest(accessor);
                            System.out.println("***filteringgg2222");

                            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                                System.out.println("****ID FROM JWT2222 = " + userId);

                                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                                accessor.setUser(auth);

                            }
                        } catch (Exception ex) {
                            logger.error("Could not set user authentication in security context", ex);

                        }
                    }

                    return message;
                }
            });

    }



    private String getJwtFromSocketRequest(StompHeaderAccessor accessor) {
        return accessor.getFirstNativeHeader("Authorization");
    }
}
