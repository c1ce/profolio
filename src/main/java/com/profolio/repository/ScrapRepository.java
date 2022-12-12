package com.profolio.repository;

import com.profolio.dto.ScrapDto;
import com.profolio.entity.Item;
import com.profolio.entity.Scrap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScrapRepository extends JpaRepository<Scrap, Long>{


//    Page<ScrapDto> findByScrapUserId(String userName, Pageable pageable);

    boolean existsByScrapUserIdAndItemId(String userName,Long itemId);
    List<ScrapDto> findByScrapUserId(String userName);

    void deleteByItemId(Long number);
}