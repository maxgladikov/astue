package astue.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@SuppressWarnings("unused")
@Service
public class JwtService {
	
	private final static String SECURITY_KEY="6D5A7134743777217A25432A462D4A614E635266556A586E3272357538782F41";
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims=extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails ) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*24 ))
				.signWith(getSignIngKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	public String generateToken(UserDetails userDetails ) {
		return generateToken(new HashMap<>(),userDetails);
	}
	
	public  String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignIngKey()).build().parseClaimsJws(token).getBody();
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String userName=extractUserName(token);
		return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	private Key getSignIngKey() {
		byte[] keyBytes=Decoders.BASE64.decode(SECURITY_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
