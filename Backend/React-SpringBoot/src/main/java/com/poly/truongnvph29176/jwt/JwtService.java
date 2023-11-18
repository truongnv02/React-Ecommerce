package com.poly.truongnvph29176.jwt;

import com.poly.truongnvph29176.model.UserCustomDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "e82c73692e6fa99b1770cfd6605bfc5b9ec3a12b362d9de5459a2612191497c4";

    // Trích xuất tên người dùng từ một chuỗi JWT.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Giải quyết thông tin được trích xuất
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Tạo một JWT dựa trên thông tin người dùng
    public String generateToken(UserCustomDetail userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // Tạo một chuỗi JWT dựa trên các thông tin người dùng
    public String generateToken(Map<String, Object> extraClaims,
                                UserCustomDetail userCustomDetails) {
        return buildToken(extraClaims, userCustomDetails);
    }

    // Xây dựng chuỗi JWT để bổ xung cho các method khác
    private String buildToken(Map<String, Object> extraClaims,
                              UserCustomDetail userCustomDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userCustomDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Kiểm tra xem một JWT có hợp lệ không
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Kiểm tra token hết hạn hay chưa
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Trích xuất thời điểm hết hạn của JWT.
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Trích xuất tất cả các thông tin từ JWT
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //  Trả về một khóa dùng để ký JWT dựa trên một chuỗi secret key.
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
