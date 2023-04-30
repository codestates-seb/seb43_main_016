package com.codestates.edusync.localmember.service;

import com.codestates.edusync.exception.BusinessLogicException;
import com.codestates.edusync.exception.ExceptionCode;
import com.codestates.edusync.localmember.entity.LocalMember;
import com.codestates.edusync.localmember.repository.LocalMemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Transactional // 모두성공 or 모두실패
@Service
public class LocalMemberService {
    private final LocalMemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public LocalMember createLocalMember(LocalMember member) {
        verifyExistsEmail(member.getEmail());

        String encryptedPassword = passwordEncoder.encode(member.getPassword()); // Password 단방향 암호화
        member.setPassword(encryptedPassword);

        if (member.getProfileImage() == null || member.getProfileImage().isEmpty()) { // 기본 이미지 등록
            member.setProfileImage("https://avatars.githubusercontent.com/u/120456261?v=4");
        }

        LocalMember savedMember = repository.save(member);

        return savedMember;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public LocalMember updateLocalMember(LocalMember member) {
        LocalMember findMember = findVerifiedMember(member.getId());

        Optional.ofNullable(member.getNickName())
                .ifPresent(name -> findMember.setNickName(name));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(password));
        Optional.ofNullable(member.getProfileImage())
                .ifPresent(image -> findMember.setProfileImage(image));
        return repository.save(findMember);
    }

    @Transactional(readOnly = true)
    public LocalMember findLocalMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<LocalMember> findLocalMembers(int page, int size) {
        return repository.findAll(PageRequest.of(page, size,
                Sort.by("id").descending()));
    }

    public void deleteLocalMember(long memberId) {
        LocalMember findMember = findVerifiedMember(memberId);

        repository.delete(findMember);
    }

    private void verifyExistsEmail(String email) {
        Optional<LocalMember> member = repository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS, String.format("%s는 이미 가입한 이메일입니다.", email));
    }

    @Transactional(readOnly = true)
    public LocalMember findVerifiedMember(long memberId) {
        Optional<LocalMember> optionalMember = repository.findById(memberId);
        LocalMember findMember =
                optionalMember.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND, "존재하지 않는 회원입니다."));
        return findMember;
    }
}
