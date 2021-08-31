package cdw.cdwproject.jwt;

import cdw.cdwproject.model.User.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenProvider {
    private final String SECRET_KEY = "OldSilver";

    //Thời gian có hiệu lực của chuỗi jwt
    private final long JWT_EXPIRATION = 604800000L;

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaim(token);
        return claimResolver.apply(claims);

    }

    private Claims extractAllClaim(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());

    }

    public String generateToken(MyUserDetails myUserDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, myUserDetails.getUser().getEmail());

    }

    public String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 10))).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();


    }

    public boolean validateToken(String token, MyUserDetails myUserDetails) {
        final String emailToken = extractUserEmail(token);
        return (emailToken.equals((myUserDetails.getUser().getEmail())) && !isTokenExpired(token));
    }

}
