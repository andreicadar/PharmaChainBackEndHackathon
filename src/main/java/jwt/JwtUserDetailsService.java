package jwt;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import api.DataAccessService;
import exception.APIRequestException;
import model.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    api.DataAccessService dataAccessService;

    @Autowired
    public JwtUserDetailsService(DataAccessService dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    @SuppressWarnings("unused")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        try {
            user = dataAccessService.getUserFromUsernameWithPassword(username);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (user == null) {
            throw new APIRequestException(HttpStatus.UNAUTHORIZED, "User not found with username: " + username);
        }

        else {
            System.out.println("suernam: " + user.getUsername() + "pass: " + user.getUsername());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    new ArrayList<>());
        }
    }

}
