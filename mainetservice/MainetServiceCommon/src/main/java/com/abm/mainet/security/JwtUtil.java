package com.abm.mainet.security;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.dto.OrganisationDTO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {

    private static final String JWT_TOKEN = "JWT_TOKEN";

    private String ISSUER_KEY = "ABM";

    private String SECRET_KEY = "#This_is_ABM@Mainet@password_with_more_than_256_bits_of_string_length#";

    // Expire After Creation Of 1 Hr
    private Long KEY_EXPIRY = 3600000L;

    public static void main(String[] args) {
	JwtUtil util = new JwtUtil();
	String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1NTk4MjcxNDcsImVtcElkIjozODMsIm9yZ0lkIjoxLCJpc3MiOiJBQk0iLCJleHAiOjE1NTk4MzA3NDd9.1ObPNaiId6FDuTxj4eoNHlcsaIIqaEwr04Az1MGqQkE";
	EmployeeDTO user = util.parseToken(token);
	String subject = user.getOrganisation().getOrgid() + "##" + user.getEmpId();
	System.out.println(subject);
    }

    /**
     * Tries to parse specified String as a JWT token. If successful, returns
     * User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user
     * properties), simply returns null.
     * 
     * @param token
     *            the JWT token to parse
     * @return the User object extracted from specified token or null if a token
     *         is invalid.
     */
    public EmployeeDTO parseToken(String token) {
	try {

	    String SECRET_KEY_ENCODED = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());

	    // This line will throw an exception if it is not a signed JWS (as
	    // expected)
	    Claims claims = Jwts.parser().requireIssuer(ISSUER_KEY)
		    .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY_ENCODED)).parseClaimsJws(token)
		    .getBody();

	    EmployeeDTO user = new EmployeeDTO();
	    OrganisationDTO org = new OrganisationDTO();
	    org.setOrgid(Long.valueOf(String.valueOf(claims.get("orgId"))));
	    user.setOrganisation(org);
	    user.setEmpId(Long.parseLong(String.valueOf(claims.get("empId"))));
	    return user;

	} catch (JwtException | ClassCastException e) {
	    return null;
	}
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role
     * as additional claims. These properties are taken from the specified User
     * object. Tokens validity is infinite.
     * 
     * @param u
     *            the user for which the token will be generated
     * @return the JWT token
     */
    public String createJWT(EmployeeDTO user) {

	Map<String, Object> claims = new ConcurrentHashMap<>();
	claims.put("orgId", user.getOrganisation().getOrgid());
	claims.put("empId", user.getEmpId());

	// The JWT signature algorithm we will be using to sign the token
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	long nowMillis = System.currentTimeMillis();
	Date now = new Date(nowMillis);

	String SECRET_KEY_ENCODED = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());

	// We will sign our JWT with our ApiKey secret
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY_ENCODED);

	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	// Let's set the JWT Claims
	JwtBuilder builder = Jwts.builder().setIssuedAt(now).addClaims(claims).setIssuer(ISSUER_KEY)
		.signWith(signingKey, signatureAlgorithm);

	// if it has been specified, let's add the expiration
	if (KEY_EXPIRY > 0) {
	    long expMillis = nowMillis + KEY_EXPIRY;
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
	}

	// Builds the JWT and serializes it to a compact, URL-safe string
	return builder.compact();
    }

    public static String getJwtToken() {
	return JWT_TOKEN;
    }

}
