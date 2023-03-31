package sid.Fsts.Authentication.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

public class JwtAuthorizationFilter  extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Origin,Accept,X-Requested-with,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,authorization");
        response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentials,authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            //System.out.println("hello");
            //filterChain.doFilter(request, response);
        } else {
            String JwtAuthorisationToken = request.getHeader("Authorization");
            System.out.println(JwtAuthorisationToken);
            if (JwtAuthorisationToken == null || !JwtAuthorisationToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            try {
              //  System.out.println("sss");
                //on supprime du jwt prefixe bearer bach yt3zl rasso
                String JWT = JwtAuthorisationToken.substring(7);
                //On apporte la  signature du token li deje authentifie bih
                Algorithm algorithm = Algorithm.HMAC256("mySecret123");
                // kncryiw objet de type verifier
                JWTVerifier jwtVerifier = com.auth0.jwt.JWT.require(algorithm).build();
                //knverifiw signature b2 o kn3tiwh f whd objer decoded li fih username o les role o kda
                DecodedJWT decodedJWT = jwtVerifier.verify(JWT);
                //avoir la session de l user
                String username = decodedJWT.getSubject();
                // recuperer liste des roles de type tableau de string
                String[] roles = decodedJWT.getClaim("role").asArray(String.class);
                //il faut convertir liste de string en GrantedAuthority
                Collection<GrantedAuthority> authorities = new ArrayList<>();
                for (String r : roles) {
                    authorities.add(new SimpleGrantedAuthority(r));
                }
                //aauthentidier l user en passant l'user avec la liste des roles
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(username, null, authorities);
                //stocker la session d l user dans le context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                //dire au spring passse vers un autre filtre ca peut dispatcherServlet ...
                filterChain.doFilter(request, response);
            } catch (Exception e) {
                response.setHeader("error-message", e.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);

            }

        }

    }
}
