package com.mirai.lyf.bot.persistence.service.master;

import com.mirai.lyf.bot.persistence.domain.master.MemberMessage;
import com.mirai.lyf.bot.persistence.repository.master.MemberMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYF
 */
@Service
public class MemberMessageService {
    private final MemberMessageRepository memberMessageRepository;

    @Autowired
    public MemberMessageService(MemberMessageRepository memberMessageRepository) {
        this.memberMessageRepository = memberMessageRepository;
    }

    @SuppressWarnings("unused")
    public List<MemberMessage> saveAll(List<MemberMessage> messages) {
        return memberMessageRepository.saveAll(messages);
    }

//    public MemberMessage find(String qqCode, String groupCode) {
//        return memberMessageRepository.findAll(qqCode, groupCode);
//    }

    @SuppressWarnings("unused")
    public MemberMessage save(MemberMessage memberMessage) {
        return memberMessageRepository.save(memberMessage);
    }

    public void delete(MemberMessage memberMessage) {
        memberMessageRepository.delete(memberMessage);
    }
}
