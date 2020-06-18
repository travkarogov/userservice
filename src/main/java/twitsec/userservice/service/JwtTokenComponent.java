package twitsec.userservice.service;

import org.springframework.stereotype.Component;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Jwts;
import com.auth0.jwt.JWT;
import twitsec.userservice.model.Role;
import twitsec.userservice.service.exception.TokenInvalidException;

@Component
public class JwtTokenComponent {

    private static final String SECRET_KEY = "sdg415fd#$TR#TGWE%Ytfg$%TGwvgtyieu5uwe5nngytvigtyiue5nlsiuerwse5mbgyv,h.lrdeihyiubes5vgmi%YT$%YTVysg4d5f4g6d";

    public boolean validateJwt(final String jwt) {
        try{
            Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).parseClaimsJws(jwt.replace("Bearer", "").trim()).getBody();
            return true;
        }catch(Exception ex){
            throw new TokenInvalidException("Provided token is invalid");
        }
    }

    public int getUserIdFromToken(final String token){
        return JWT.decode(token).getClaim("userId").asInt();
    }

    public String getEmailFromToken(final String token){
        return JWT.decode(token).getClaim("email").asString();
    }

    public Role getRoleFromToken(final String token){
        return Role.valueOf(JWT.decode(token).getClaim("role").asString());
    }
}
