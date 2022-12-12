package com.profolio.service;

import com.profolio.constant.ConatctStatus;
import com.profolio.dto.ContactDto;
import com.profolio.entity.Contact;
import com.profolio.repository.CompanyPopupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyPopupService {

    private final CompanyPopupRepository companyPopupRepository;

    public void save(ContactDto contactDto) {
        System.out.println(contactDto);
        Contact contact = contactDto.createContact();
        contact.setConatctStatus(ConatctStatus.NOT_READ);
        companyPopupRepository.save(contact);
    }

    public void delete(List<Long> id) {

        for (Long number:
                id) {
            companyPopupRepository.deleteById(number);
        }

    }

    public Page<Contact> getConatctCompanyList(String username, Pageable pageable) {
        Page<Contact> list= companyPopupRepository.findBySendName(username,pageable);
        return list;
    }
    public Page<Contact> getConatctUserList(String username, Pageable pageable) {
        Page<Contact> list= companyPopupRepository.findByRecvName(username,pageable);
        return list;
    }
    public Contact getContact(Long id) {

        Optional<Contact> contact = companyPopupRepository.findById(id);
        return contact.get();
    }

    public void changeRead(Long id) {
        Optional<Contact> contact = companyPopupRepository.findById(id);
        Contact con = contact.get();
        con.setConatctStatus(ConatctStatus.READ);
        companyPopupRepository.save(con);

    }
}
