package cn.su.xxd.server.config;

import cn.su.xxd.base.entity.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
//        ResultResponse resultResponse = userApi.findBusinessUser(username);
//        if(resultResponse.isSuccess()) {
//            Gson gson = new Gson();
//            String json = gson.toJson(resultResponse.getResult());
//            Type typeOfT = new TypeToken<UserVo>() {}.getType();
//            UserVo userVo = gson.fromJson(json, typeOfT);
//            String password = userVo.getPassword();
//            resultResponse = userApi.findBusinessByUserId(userVo.getId());
//            Long id = null;
//            if(resultResponse.isSuccess()) {
//                json = gson.toJson(resultResponse.getResult());
//                typeOfT = new TypeToken<BusinessVo>() {}.getType();
//                BusinessVo businessVo = gson.fromJson(json, typeOfT);
//                id = businessVo.getId();
//            }
//            String realname = userVo.getRealName();
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+userVo.getRoleVo().getCode()));
        String encode = new BCryptPasswordEncoder(4).encode("123456");
        return new UserInfo(1L, username, "demo", encode, true, true, true, true, authorities);
//        }else {
//            loginAttemptService.loginFailed(username);
//            throw new UsernameNotFoundException("Username not found: " + username);
//        }

    }


}

