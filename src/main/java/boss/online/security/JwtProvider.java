package boss.online.security;

import java.util.Date;
import java.util.Set;



import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	private static final long expireTime=1000*60*60*24;
	private static final String secretKey= "This is a secret key that no one knows";
			Date expireDate= new Date(System.currentTimeMillis()+expireTime);
	public String generateToken(String username) {
		
		String token = Jwts
				.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512,secretKey)
				.compact();
		return token;
	}
	
	public String getUsernameFromToken(String token) {
		try {
			String username = Jwts.
					parser()
					.setSigningKey(secretKey)
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
			return username;
		} catch (Exception e) {
			return null;
		}
	}
}
