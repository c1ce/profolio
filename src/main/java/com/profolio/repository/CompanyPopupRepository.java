package com.profolio.repository;

import com.profolio.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyPopupRepository extends JpaRepository<Contact,Long> {

    Page<Contact> findBySendName(String username, Pageable pageable);

    Page<Contact> findByRecvName(String username, Pageable pageable);
}
