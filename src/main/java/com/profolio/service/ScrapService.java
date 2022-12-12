package com.profolio.service;

import com.profolio.dto.ScrapDto;
import com.profolio.entity.Scrap;
import com.profolio.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;



    public List<ScrapDto> readScrap(String username,Pageable pageable) {
//        return scrapRepository.findByScrapUserId(username,pageable);
        return scrapRepository.findByScrapUserId(username);
    }
//    public ScrapDto readSelectScrap(String username) {
////        return scrapRepository.findBy(itemSearchDto, pageable, createdBy);
//    }

    public boolean createScrap(ScrapDto scrapDto) {
        Scrap scrap = new Scrap();
        scrap.updateItem(scrapDto);
        if(!scrapRepository.existsByScrapUserIdAndItemId(scrapDto.getScrapUserId(),scrapDto.getItemId())) {
            scrapRepository.save(scrap);

            return true;
        }
        return false;
    }

    public void deleteScrap(int code) {
    }
}