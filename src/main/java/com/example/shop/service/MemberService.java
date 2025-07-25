package com.example.shop.service;

import com.example.shop.entity.Member;
import com.example.shop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    /**
     * This method saves Member entity to MemberRepository
     * @return Member entity that is saved to MemberRepository
     */
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    /**
     * Checks whether an account already exists with the email provided by the user during sign-up.
     * Throws an exception if a duplicate account is found.
     */
    private void validateDuplicateMember(Member member){

        if (memberRepository.findByEmail(member.getEmail()) != null){
           throw new IllegalStateException("Account with this email already exists");
        }
    }

    /**
     * This function looks up the Member entity in the database by email and
     * converts it to a UserDetails object to verify credentials and manage authorization.
     * @param email The email (username) provided during login
     * @return UserDetails object containing user's information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    };
}
