package com.example.adam.demo;

import com.example.adam.demo.models.Completed;
import com.example.adam.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletedRepository completedRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> getByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User create(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }


    public Page<Completed> findAllCompleted(int pageNo, User user){

        Pageable paging = PageRequest.of(pageNo, 10);
        Page<Completed> pageResult = completedRepository.findAllByCompletion(user.getEmail(),paging);

        pageResult.filter(completed -> completed.getUser().getEmail().equals(user.getEmail()));
        if(pageResult.hasContent()){
            return pageResult;
        }else{
            return new PageImpl<Completed>(new ArrayList<>());
        }
    }

    public void setCompleted(User user, int id){
        Completed comp = new Completed();
        comp.setCompletedAt(LocalDateTime.now());
        comp.setUser(userRepository.findByEmail(user.getEmail()).get());
        comp.setId(id);
        completedRepository.save(comp);
    }
}
