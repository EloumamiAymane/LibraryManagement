package sid.Fsts.Authentication.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sid.Fsts.Authentication.Repository.UserRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserRepository userRepository;
private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
//lorsque l user veut se s'authentifier alors il appel au method attemptAuthentication
    //dont laquelle il requepere username et pw et le passer a travers methode authenticate qui va faire sql ...
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username=request.getParameter("username");
       String password=request.getParameter("password");
        System.out.println("attemptAuthentication");
        System.out.println(username);
       System.out.println(password);
        sid.Fsts.Authentication.entities.User user=null;
try{
    user=new ObjectMapper().readValue(request.getInputStream(), sid.Fsts.Authentication.entities.User.class);
}catch(Exception e){
   throw  new RuntimeException(e);
}
       UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        //authenticate method c est elle qui va appele le service faire sql.....
        return authenticationManager.authenticate(authenticationToken);
    }

    //puis si il recupere username et ses roles alors il fait appel au suucceful method qui va s'ocupper de builder
    //le token et le refresh tocken
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("successfulAuthentication");
        //avoir user authentifie
       User user= (User) authResult.getPrincipal();

       Algorithm algorithm=Algorithm.HMAC256("mySecret123");
        //construire jwt
        String jwtAccessToken= JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+1*60*1000))
                .withIssuer(request.getRequestURI().toString())
                .withClaim("role",user.getAuthorities().stream().
                        map(ga->ga.getAuthority()).collect(Collectors.toList()))

                .sign(algorithm);

        String jwtRefreshToken= JWT.create().withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()))
                .withIssuer(request.getRequestURI().toString())

                .sign(algorithm);
        Map<String,String> idToken=new HashMap<>();
        //idToken.put("accessToken",jwtAccessToken);
        //idToken.put("refreshToken",jwtRefreshToken);
       // response.setContentType("application/json");
        System.out.println(jwtAccessToken);
      response.setHeader("authorization",jwtAccessToken);
        new ObjectMapper().writeValue(response.getOutputStream(),idToken);

    }
}
