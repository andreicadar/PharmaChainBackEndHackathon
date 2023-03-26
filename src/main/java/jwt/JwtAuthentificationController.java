package jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import api.DataAccessService;
import exception.APIRequestException;
import model.JwtRequest;
import model.JwtResponse;
import model.User;

@RestController
@CrossOrigin
public class JwtAuthentificationController {

    private DataAccessService dataAccessService;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public JwtAuthentificationController(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;

    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/api/users/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {
            System.out.println("Username: " + authenticationRequest.getUsername());
            System.out.println("Password: " + authenticationRequest.getPassword());
            authentificate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new APIRequestException(HttpStatus.UNAUTHORIZED, "Credentials Wrong");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        System.out.println(authenticationRequest.getUsername());
        System.out.println(bcryptEncoder.encode(authenticationRequest.getPassword()));
        System.out.println(authenticationRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authentificate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (LockedException e) {
            throw new Exception("LOCKED_ACCOUNT", e);
        } catch (AuthenticationException e) {
            throw new Exception("AUTH_FAIL", e);
        }

    }
}
